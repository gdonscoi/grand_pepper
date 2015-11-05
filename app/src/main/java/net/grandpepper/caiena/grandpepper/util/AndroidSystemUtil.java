package net.grandpepper.caiena.grandpepper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import net.grandpepper.caiena.grandpepper.activity.SplashScreenActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class AndroidSystemUtil {
    public static final String TAG = "Script";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String SENDER_ID = "884137778915";


    public static int getAppVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return (pi.versionCode);
        } catch (NameNotFoundException e) {
            Log.i(TAG, "Package name not found");
        }
        return (0);
    }


    public static int randInt() {
        Random rand = new Random();
        return rand.nextInt((50000) + 1);
    }


    // SHARED PREFERENCES
    public static String getRegistrationId(Context context) {
        SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");

        if (registrationId.trim().length() == 0) {
            Log.i(TAG, "Registration not found.");
            return ("");
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = AndroidSystemUtil.getAppVersion(context);

        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App Version has changed");
            return ("");
        }

        return (registrationId);
    }

    public static void storeRegistrationId(Context context, String regId) {
        SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = AndroidSystemUtil.getAppVersion(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.apply();
    }

    public static SharedPreferences getGCMPreferences(Context context) {
        return (context.getSharedPreferences(SplashScreenActivity.class.getSimpleName(), Context.MODE_PRIVATE));
    }

    public static String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static Bitmap getImageExternalStorage(String nameImage) {
        String photoPath = Environment.getExternalStorageDirectory() + nameImage;
        Bitmap photo;
        try {
            photo = BitmapFactory.decodeFile(photoPath);
        } catch (Exception e) {
            Log.e("AndroidSystemUtil" , "Erro ao carregar imagem" + e.getMessage());
            return null;
        }
        return photo;
    }
}
