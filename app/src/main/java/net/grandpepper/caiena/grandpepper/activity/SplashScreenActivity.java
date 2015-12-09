package net.grandpepper.caiena.grandpepper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import net.grandpepper.caiena.grandpepper.Manifest;
import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.adapters.GifView;
import net.grandpepper.caiena.grandpepper.asynctasks.AsyncTaskRegisterID;
import net.grandpepper.caiena.grandpepper.asynctasks.AsyncTaskUpdateData;
import net.grandpepper.caiena.grandpepper.models.GrandPepperDAO;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

public class SplashScreenActivity extends Activity {

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private Boolean flagRefresh = false;
    private Context context;
    final private int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public TextView loadMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        context = this;

        loadMessage = (TextView) findViewById(R.id.load_message);

        if (!checkPlayServices(context))
            return;

        String regId = AndroidSystemUtil.getRegistrationId(this);
        if (regId.trim().isEmpty()) {
            new AsyncTaskRegisterID().execute(context);
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (getIntent().getExtras() != null)
                    flagRefresh = getIntent().getExtras().getBoolean("refresh", false);

                if (flagRefresh || GrandPepperDAO.getInstance(this).count() <= 0) {
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(SplashScreenActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        return;
                    }

                    new AsyncTaskUpdateData(context).execute();
                    return;
                }
            }

            GifView gifView = (GifView) findViewById(R.id.gif_view);

            int SPLASH_DISPLAY_LENGTH = (int) gifView.getMovieDuration();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        Intent mainIntent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("url", "http://grandpepper.caiena.net");
                        mainIntent.putExtras(mBundle);
                        startActivity(mainIntent);
                        finish();
                        overridePendingTransition(R.anim.enter_slide_up, R.anim.exit_slide_down);
                        return;
                    }


                    Intent mainIntent = new Intent(context, GrandPepperActivity.class);
                    startActivity(mainIntent);
                    finish();
                    overridePendingTransition(R.anim.enter_slide_up, R.anim.exit_slide_down);
                }
            }, SPLASH_DISPLAY_LENGTH);


        } catch (Exception ignored) {
            Log.e("SplashScreenActivity", ignored.getMessage());
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                new AsyncTaskUpdateData(context).execute();
                break;
            }

        }
    }

}
