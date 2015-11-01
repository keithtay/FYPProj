package com.example.keith.fyp.broadcastreceiver;

/**
 * Created by Keith on 30/9/2015.
 */

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.keith.fyp.R;
import com.example.keith.fyp.views.activities.DashboardActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GCMIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1000;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GCMIntentService() {
        super(GCMIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
//            Log.i("HERE", "TRY TRY");
        if (!extras.isEmpty()) {
            // read extras as sent from server
            String message = extras.getString("message");
//                Log.i("MESSAGE", message);
            String serverTime = extras.getString("timestamp");
            sendNotification("Message: " + message + "\n" + "Server Time: "
                    + serverTime);
        }
//            String from=extras.getString("from");
//            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
//            String messageType = gcm.getMessageType(intent);
//
//            if (!extras.isEmpty()) {
//
//                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//                    Log.i("B","Send error: " + extras.toString());
//                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
//                    Log.i("C","Deleted messages on server: " + extras.toString());
//                    // If it's a regular GCM message, do some work.
//                } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
//                    // This loop represents the service doing some work.
//                    for (int i = 0; i < 5; i++) {
//                        Log.i("A", "Working... " + (i + 1) + "/5 @ " + SystemClock.elapsedRealtime());
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                        }
//                    }
//                    Log.i("D", "Completed work @ " + SystemClock.elapsedRealtime());
//                    // Post notification of received message.
//                    // sendNotification("Received: " + extras.toString());
//                    /*********ERROR IN SOME DEVICES*****************/
//
//                    if(from.equals("google.com/iid"))
//                    {
//                        //related to google ... DO NOT PERFORM ANY ACTION
//                    }
//                    else {
//                        //HANDLE THE RECEIVED NOTIFICATION
//                        String msg = intent.getStringExtra("message");
//                        sendNotification(msg);
//                        Log.i("E", "Received: " + extras.toString());
//                    }
//                    /**************************/
//                }
//            }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        SBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
//            Log.i("WAIT", msg);
//            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                    new Intent(this, DashboardActivity.class), 0);
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                    this).setSmallIcon(R.drawable.ic_launcher)
//                    .setContentTitle("Notification from GCM")
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
//                    .setContentText(msg);
//
//            mBuilder.setContentIntent(contentIntent);
//            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
        String strDate = sdf.format(c.getTime());
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        Notification noti = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(largeIcon)
                .setContentTitle("Notification from PEAR")
                .setContentText("There are patients who have new schedule. Notification received at: " + strDate)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent).getNotification();
//        v.vibrate(400);
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

}
