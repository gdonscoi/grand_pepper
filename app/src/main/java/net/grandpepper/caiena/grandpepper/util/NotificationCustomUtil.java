package net.grandpepper.caiena.grandpepper.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.activity.SplashScreenActivity;
import net.grandpepper.caiena.grandpepper.activity.WebViewActivity;

public class NotificationCustomUtil {
	private static NotificationManager mNotificationManager;
	
	public static void sendNotification(Context context, String title, String urlPath, String message) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent mainIntent = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mainIntent = new Intent(context, WebViewActivity.class);
            Bundle mBundle = new Bundle();
            if(urlPath == null || urlPath.isEmpty())
                urlPath = "http://grandpepper.caiena.net";
            mBundle.putString("url", urlPath);
            mainIntent.putExtras(mBundle);
        }else{
            mainIntent = new Intent(context, SplashScreenActivity.class);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,mainIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
	        .setContentTitle(title)
            .setTicker(title)
			.setContentText(message);
        
        mBuilder.setContentIntent(contentIntent);
        
        Notification notification = mBuilder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(AndroidSystemUtil.randInt(), notification);
    }
}
