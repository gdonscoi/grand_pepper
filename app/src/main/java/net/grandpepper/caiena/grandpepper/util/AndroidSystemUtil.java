package net.grandpepper.caiena.grandpepper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Environment;
import android.util.Log;

import net.grandpepper.caiena.grandpepper.activity.SplashScreenActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private static File getFolder() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/grand_pepper");
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    public static Bitmap getImageExternalStorage(String nameImage) {
        String photoPath = getFolder() + nameImage;
        Bitmap photo;
        try {
            photo = BitmapFactory.decodeFile(photoPath);
        } catch (Exception e) {
            Log.e("AndroidSystemUtil", "Erro ao carregar imagem" + e.getMessage());
            return null;
        }
        return photo;
    }

    public static Bitmap getCircularAvatar(Bitmap bitmapImage) {
        if (bitmapImage == null)
            return null;

        Bitmap circleBitmap = Bitmap.createBitmap(bitmapImage.getWidth(), bitmapImage.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmapImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmapImage.getWidth() / 2, bitmapImage.getHeight() / 2, bitmapImage.getWidth() / 2, paint);

        return circleBitmap;
    }

    public static String saveImageInfo(InputStream image, String nameImage) throws IOException {
        if (image == null)
            return "";
        OutputStream output = null;
        File storagePath = getFolder();
        try {
            output = new FileOutputStream(new File(storagePath, nameImage));
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = image.read(buffer, 0, buffer.length)) >= 0) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Exception ignore) {
            Log.e("HttpConnectionUtil", "Erro ao salvar imagem.");
            return "";
        } finally {
            if (output != null)
                output.close();
            image.close();
        }
        return "/".concat(nameImage);
    }

    public static void deleteDir() {
        File dir = getFolder();
        if (dir.isDirectory()) {
            for (File child : dir.listFiles())
                child.delete();
        }
    }
}
