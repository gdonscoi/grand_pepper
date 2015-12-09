package net.grandpepper.caiena.grandpepper.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;
import net.grandpepper.caiena.grandpepper.util.HttpConnectionUtil;

public class AsyncTaskRegisterID extends AsyncTask<Object, Boolean, Boolean> {

    private Context context;
    private GoogleCloudMessaging gcm;

    @Override
    protected Boolean doInBackground(Object... params) {
        context = (Context) params[0];
        try {
            gcm = GoogleCloudMessaging.getInstance(context);

            String regId = gcm.register(AndroidSystemUtil.SENDER_ID);
            Log.e("regId", regId);
            HttpConnectionUtil.sendRegistrationIdToBackend(regId);
            AndroidSystemUtil.storeRegistrationId(context, regId);
            Log.e("AsyncTasckRegister", "Registration ID saved for GCM");
        } catch (Exception e) {
            Log.e("AsyncTasckRegister", e.getMessage());
            return false;
        }

        return true;
    }
}
