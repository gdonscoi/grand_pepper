package net.grandpepper.caiena.grandpepper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.asynctasks.AsyncTaskUpdateData;
import net.grandpepper.caiena.grandpepper.models.GrandPepperDAO;

public class SplashScreenActivity extends Activity {

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Context context = this;

        if (!checkPlayServices(context))
            return;

//        String regId = AndroidSystemUtil.getRegistrationId(this);
//        if (regId.trim().isEmpty()) {
//            new AsyncTaskRegisterID().execute(context);
//        }

        try {
            if (GrandPepperDAO.getInstance(this).count() <= 0) {
                new AsyncTaskUpdateData().execute(context);
                return;
            }

            Intent mainIntent = new Intent(context, GrandPepperActivity.class);
            context.startActivity(mainIntent);

        } catch (Exception ignored) {
            Log.e("SplashScreenActivity", ignored.getMessage());
        }

//        int SPLASH_DISPLAY_LENGTH = 3000;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent mainIntent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putString("url", "http://www.davidpedoneze.com/gp/");
//                mainIntent.putExtras(mBundle);
//                SplashScreenActivity.this.startActivity(mainIntent);
//                SplashScreenActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, SplashScreenActivity.this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(context, "PlayServices sem suporte", Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
            }
            return (false);
        }
        return (true);
    }
}
