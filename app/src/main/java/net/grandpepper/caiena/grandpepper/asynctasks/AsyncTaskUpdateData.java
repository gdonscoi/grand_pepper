package net.grandpepper.caiena.grandpepper.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.grandpepper.caiena.grandpepper.activity.MainActivity;
import net.grandpepper.caiena.grandpepper.beans.Event;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.models.EventDAO;
import net.grandpepper.caiena.grandpepper.models.InfoDAO;
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

                if (!grandPepper.backgroundImageUrl.isEmpty()) {
                    String[] imageName = grandPepper.backgroundImageUrl.split("/");
                    grandPepper.backgroundImagePath = HttpConnectionUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(grandPepper.backgroundImageUrl),
                            String.valueOf(grandPepper.version).concat(imageName[imageName.length - 1]));
                }

                InfoDAO.getInstance(context).createOrUpdate(grandPepper);

                if (grandPepper.eventsJson != null) {
                    for (Event event : grandPepper.eventsJson)
                        event.grandPepper = grandPepper;
                    EventDAO.getInstance(context).createOrUpdate(grandPepper.eventsJson);

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
            Intent mainIntent = new Intent(context, MainActivity.class);
            context.startActivity(mainIntent);
            ((Activity) context).finish();
            return;
        }
        Toast.makeText(context, "Erro ao fazer update.", Toast.LENGTH_LONG).show();
//        ((Activity) context).finish();
    }
}
