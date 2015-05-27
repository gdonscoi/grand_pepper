package net.grandpepper.caiena.grandpepper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import net.grandpepper.caiena.grandpepper.R;

import net.grandpepper.caiena.grandpepper.asynctasks.AsyncTaskRegisterID;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

public class SplashScreenActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 5000;
    public static final String TAG = "Script";
    private Context context;

    private String SENDER_ID = "";
    private String regId;
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = this;

        if(AndroidSystemUtil.checkPlayServices(context)){
            gcm = GoogleCloudMessaging.getInstance(this);
            regId = AndroidSystemUtil.getRegistrationId(this);

            if(regId.trim().length() == 0){
                new AsyncTaskRegisterID().execute(context);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
