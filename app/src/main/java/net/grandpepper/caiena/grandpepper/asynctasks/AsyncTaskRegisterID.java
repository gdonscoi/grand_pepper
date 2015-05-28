package net.grandpepper.caiena.grandpepper.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import net.grandpepper.caiena.grandpepper.activity.WebViewActivity;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;
import net.grandpepper.caiena.grandpepper.util.HttpConnectionUtil;

import java.io.IOException;

/**
 * Created by gdonscoi on 5/27/15.
 */
public class AsyncTaskRegisterID extends AsyncTask<Object, Boolean, Boolean> {

    private Context context;
    private GoogleCloudMessaging gcm;

    @Override
    protected Boolean doInBackground(Object... params) {
        context = (Context) params[0];
        try {
            gcm = GoogleCloudMessaging.getInstance(context);

            String regId = gcm.register(AndroidSystemUtil.SENDER_ID);

            HttpConnectionUtil.sendRegistrationIdToBackend(regId);

            AndroidSystemUtil.storeRegistrationId(context, regId);
        } catch (Exception e) {
            Log.e("AsyncTasckRegister", e.getMessage());
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onPostExecute(Boolean msg){
        if(msg) {
            Intent mainIntent = new Intent(context, WebViewActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("url", "http://www.davidpedoneze.com/gp/");
            mainIntent.putExtras(mBundle);
            context.startActivity(mainIntent);
            ((Activity) context).finish();
            return;
        }
        ((Activity) context).finish();
    }
}
