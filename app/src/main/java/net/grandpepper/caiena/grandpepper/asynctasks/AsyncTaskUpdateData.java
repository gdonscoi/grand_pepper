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
import net.grandpepper.caiena.grandpepper.beans.Info;
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
            List<Info> infos = HttpConnectionUtil.getJsonInfo();

            db = InfoDAO.getInstance(context).getConnectionDataBase();
            db.beginTransaction();

            for (Info info : infos) {

                if (!info.backgroundImageUrl.isEmpty()) {
                    String[] imageName = info.backgroundImageUrl.split("/");
                    info.backgroundImagePath = HttpConnectionUtil.saveImageInfo(HttpConnectionUtil.getImageInfo(info.backgroundImageUrl),
                            String.valueOf(info.version).concat(imageName[imageName.length-1]));
                }

                InfoDAO.getInstance(context).createOrUpdate(info);

                if (info.eventsJson != null) {
                    for (Event event : info.eventsJson)
                        event.info = info;
                    EventDAO.getInstance(context).createOrUpdate(info.eventsJson);

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
