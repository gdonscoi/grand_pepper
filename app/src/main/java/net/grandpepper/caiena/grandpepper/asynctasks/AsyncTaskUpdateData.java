package net.grandpepper.caiena.grandpepper.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.grandpepper.caiena.grandpepper.activity.GrandPepperActivity;
import net.grandpepper.caiena.grandpepper.activity.SplashScreenActivity;
import net.grandpepper.caiena.grandpepper.beans.Contact;
import net.grandpepper.caiena.grandpepper.beans.Event;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.beans.Location;
import net.grandpepper.caiena.grandpepper.models.CallForPeppersDAO;
import net.grandpepper.caiena.grandpepper.models.ContactDAO;
import net.grandpepper.caiena.grandpepper.models.EventDAO;
import net.grandpepper.caiena.grandpepper.models.GrandPepperDAO;
import net.grandpepper.caiena.grandpepper.models.LocationDAO;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;
import net.grandpepper.caiena.grandpepper.util.HttpConnectionUtil;

import java.util.List;

public class AsyncTaskUpdateData extends AsyncTask<Object, Boolean, Boolean> {

    private Context context;

    public AsyncTaskUpdateData(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        ((SplashScreenActivity) context).loadMessage.setText("Downloading data …");
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        SQLiteDatabase db = null;
        try {
            List<GrandPepper> grandPeppers = HttpConnectionUtil.parseJsonToInfo(HttpConnectionUtil.getJsonInfo());

            db = GrandPepperDAO.getInstance(context).getConnectionDataBase();
            db.beginTransaction();

            if (!grandPeppers.isEmpty()) {
                CallForPeppersDAO.getInstance(context).destroyAll();
                ContactDAO.getInstance(context).destroyAll();
                EventDAO.getInstance(context).destroyAll();
                LocationDAO.getInstance(context).destroyAll();
                GrandPepperDAO.getInstance(context).destroyAll();

                AndroidSystemUtil.deleteDir();
            }

            for (GrandPepper grandPepper : grandPeppers) {

                if (grandPepper.backgroundImageUrl != null && !grandPepper.backgroundImageUrl.isEmpty()) {
                    String[] imageName = grandPepper.backgroundImageUrl.split("/");
                    grandPepper.backgroundImagePath = AndroidSystemUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(grandPepper.backgroundImageUrl),
                            String.valueOf(grandPepper.version).concat(imageName[imageName.length - 1]));
                    Log.e("AsyncTaskUpdateData", "randPepper.backgroundImageUrl");
                }

                if (grandPepper.callForPeppers != null) {
                    CallForPeppersDAO.getInstance(context).createOrUpdate(grandPepper.callForPeppers);

                    if (grandPepper.callForPeppers.contactsJson != null) {
                        for (Contact contact : grandPepper.callForPeppers.contactsJson)
                            contact.callForPeppers = grandPepper.callForPeppers;
                        ContactDAO.getInstance(context).createOrUpdate(grandPepper.callForPeppers.contactsJson);
                    }
                }

                GrandPepperDAO.getInstance(context).createOrUpdate(grandPepper);

                if (grandPepper.eventsJson != null) {
                    for (Event event : grandPepper.eventsJson) {
                        event.grandPepper = grandPepper;
                        if (event.authorAvatarUrl != null && !event.authorAvatarUrl.isEmpty()) {
                            String[] imageName = event.authorAvatarUrl.split("/");
                            event.authorAvatarPath = AndroidSystemUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(event.authorAvatarUrl),
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
        ((SplashScreenActivity) context).loadMessage.setText("Error in downloading data …");
        Toast.makeText(context, "Erro ao fazer update.", Toast.LENGTH_LONG).show();
    }
}
