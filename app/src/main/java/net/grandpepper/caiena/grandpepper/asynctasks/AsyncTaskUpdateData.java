package net.grandpepper.caiena.grandpepper.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.grandpepper.caiena.grandpepper.activity.GrandPepperActivity;
import net.grandpepper.caiena.grandpepper.beans.CallForPeppers;
import net.grandpepper.caiena.grandpepper.beans.Contact;
import net.grandpepper.caiena.grandpepper.beans.Event;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.beans.Location;
import net.grandpepper.caiena.grandpepper.models.CallForPeppersDAO;
import net.grandpepper.caiena.grandpepper.models.ContactDAO;
import net.grandpepper.caiena.grandpepper.models.EventDAO;
import net.grandpepper.caiena.grandpepper.models.InfoDAO;
import net.grandpepper.caiena.grandpepper.models.LocationDAO;
import net.grandpepper.caiena.grandpepper.util.HttpConnectionUtil;

import java.util.List;

public class AsyncTaskUpdateData extends AsyncTask<Object, Boolean, Boolean> {

    private Context context;

    @Override
    protected Boolean doInBackground(Object... params) {
        context = (Context) params[0];
        SQLiteDatabase db = null;
        try {
            List<GrandPepper> grandPeppers = HttpConnectionUtil.parseJsonToInfo(HttpConnectionUtil.getJsonInfo());

            db = InfoDAO.getInstance(context).getConnectionDataBase();
            db.beginTransaction();

            for (GrandPepper grandPepper : grandPeppers) {

                if (grandPepper.backgroundImageUrl != null && !grandPepper.backgroundImageUrl.isEmpty()) {
                    String[] imageName = grandPepper.backgroundImageUrl.split("/");
                    grandPepper.backgroundImagePath = HttpConnectionUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(grandPepper.backgroundImageUrl),
                            String.valueOf(grandPepper.version).concat(imageName[imageName.length - 1]));
                    Log.e("AsyncTaskUpdateData", "randPepper.backgroundImageUrl");
                }

                if (grandPepper.locationBackgroundImageUrl != null && !grandPepper.locationBackgroundImageUrl.isEmpty()) {
                    String[] imageNameLocation = grandPepper.locationBackgroundImageUrl.split("/");
                    grandPepper.locationBackgroundImagePath = HttpConnectionUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(grandPepper.locationBackgroundImageUrl),
                            String.valueOf(grandPepper.version).concat(imageNameLocation[imageNameLocation.length - 1]));
                    Log.e("AsyncTaskUpdateData", "randPepper.locationBackgroundImageUrl");
                }

                if (grandPepper.talksBackgroundImageUrl != null && !grandPepper.talksBackgroundImageUrl.isEmpty()) {
                    String[] imageNameTalks = grandPepper.talksBackgroundImageUrl.split("/");
                    grandPepper.talksBackgroundImagePath = HttpConnectionUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(grandPepper.talksBackgroundImageUrl),
                            String.valueOf(grandPepper.version).concat(imageNameTalks[imageNameTalks.length - 1]));
                    Log.e("AsyncTaskUpdateData", "randPepper.talksBackgroundImageUrl");
                }
                InfoDAO.getInstance(context).createOrUpdate(grandPepper);

                if (grandPepper.eventsJson != null) {
                    for (Event event : grandPepper.eventsJson) {
                        event.grandPepper = grandPepper;
                        if (event.authorAvatarUrl != null && !event.authorAvatarUrl.isEmpty()) {
                            String[] imageName = event.authorAvatarUrl.split("/");
                            event.authorAvatarPath = HttpConnectionUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(event.authorAvatarUrl),
                                    String.valueOf(grandPepper.version).concat(imageName[imageName.length - 1]));
                            Log.e("AsyncTaskUpdateData", "event.authorAvatarUrl");
                        }
                    }
                    EventDAO.getInstance(context).createOrUpdate(grandPepper.eventsJson);
                }

                if (grandPepper.locationsJson != null) {
                    for (Location location : grandPepper.locationsJson)
                        location.grandPepper = grandPepper;
                    LocationDAO.getInstance(context).createOrUpdate(grandPepper.locationsJson);
                }

                if (grandPepper.callForPeppersesJson != null) {
                    for (CallForPeppers callForPeppers : grandPepper.callForPeppersesJson) {
                        callForPeppers.grandPepper = grandPepper;

                        if (callForPeppers.backgroundImageUrl != null && !callForPeppers.backgroundImageUrl.isEmpty()) {
                            String[] imageName = callForPeppers.backgroundImageUrl.split("/");
                            callForPeppers.backgroundImagePath = HttpConnectionUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(callForPeppers.backgroundImageUrl),
                                    String.valueOf(callForPeppers.title).concat(imageName[imageName.length - 1]));
                            Log.e("AsyncTaskUpdateData", "callForPeppers.backgroundImageUrl ");
                        }

                        CallForPeppersDAO.getInstance(context).createOrUpdate(callForPeppers);

                        if (callForPeppers.contactsJson != null) {
                            for (Contact contact : callForPeppers.contactsJson)
                                contact.callForPeppers = callForPeppers;
                            ContactDAO.getInstance(context).createOrUpdate(callForPeppers.contactsJson);
                        }
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("AsyncTaskUpdateData", e.getMessage());
            return false;
        } finally {
            if (db != null)
                db.endTransaction();
        }

        return true;
    }

    @Override
    public void onPostExecute(Boolean msg) {
        if (msg) {
            Intent mainIntent = new Intent(context, GrandPepperActivity.class);
            context.startActivity(mainIntent);
            ((Activity) context).finish();
            return;
        }
        Toast.makeText(context, "Erro ao fazer update.", Toast.LENGTH_LONG).show();
//        ((Activity) context).finish();
    }
}