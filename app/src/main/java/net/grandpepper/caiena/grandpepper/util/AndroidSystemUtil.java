package net.grandpepper.caiena.grandpepper.util;

/**
 * Created by gdonscoi on 5/27/15.
 */
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import net.grandpepper.caiena.grandpepper.activity.SplashScreenActivity;

public class AndroidSystemUtil {
    public static final String TAG = "Script";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    public static int getAppVersion(Context context){
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return(pi.versionCode);
        }
        catch (NameNotFoundException e) {
            Log.i(TAG, "Package name not found");
        }
        return(0);
    }


    public static int randInt() {
        Random rand = new Random();
        int randomNum = rand.nextInt((50000 - 0) + 1) + 0;
        return randomNum;
    }


    // SHARED PREFERENCES
    public static String getRegistrationId(Context context){
        SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");

        if(registrationId.trim().length() == 0){
            Log.i(TAG, "Registration not found.");
            return("");
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = AndroidSystemUtil.getAppVersion(context);

        if(registeredVersion != currentVersion){
            Log.i(TAG, "App Version has changed");
            return("");
        }

        return(registrationId);
    }

    public static void storeRegistrationId(Context context, String regId){
        SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = AndroidSystemUtil.getAppVersion(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public static SharedPreferences getGCMPreferences(Context context){
        return(context.getSharedPreferences(SplashScreenActivity.class.getSimpleName(), Context.MODE_PRIVATE));
    }

    public static boolean checkPlayServices(Context context){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, ((Activity) context), PLAY_SERVICES_RESOLUTION_REQUEST);
            }
            else{
                Toast.makeText(context, "PlayServices sem suporte", Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
            }
            return(false);
        }
        return(true);
    }
}
