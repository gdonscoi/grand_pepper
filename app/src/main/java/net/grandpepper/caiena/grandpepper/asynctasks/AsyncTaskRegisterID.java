package net.grandpepper.caiena.grandpepper.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;
import net.grandpepper.caiena.grandpepper.util.HttpConnectionUtil;

import java.io.IOException;

/**
 * Created by gdonscoi on 5/27/15.
 */
public class AsyncTaskRegisterID extends AsyncTask<Object, Boolean, Boolean> {

    private Context context;
    private GoogleCloudMessaging gcm;
    private String SENDER_ID = "";

    @Override
    protected Boolean doInBackground(Object... params) {
        String msg = "";
        context = (Context) params[0];

        try {
            gcm = GoogleCloudMessaging.getInstance(context);

            String regId = gcm.register(SENDER_ID);

            msg = "Register Id: " + regId;

            String feedback = HttpConnectionUtil.sendRegistrationIdToBackend(regId);

            AndroidSystemUtil.storeRegistrationId(context, regId);
        } catch (IOException e) {
            Log.e("AsyncTasckRegister", e.getMessage());
        }

        return true;
    }
}
