package net.grandpepper.caiena.grandpepper.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import net.grandpepper.caiena.grandpepper.util.NotificationCustomUtil;


public class GcmIntentService extends IntentService {
    public static final String TAG = "Script";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(GcmIntentService.this);
        String title, urlPath, message, messageType = gcm.getMessageType(intent);


        if (extras != null) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.i(TAG, "Error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.i(TAG, "Deleted: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                title = extras.getString("title");
                urlPath = extras.getString("urlPath");
                message = extras.getString("message");

                NotificationCustomUtil.sendNotification(GcmIntentService.this, title, urlPath, message);
            }
        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

}
