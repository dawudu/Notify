package com.example.manthole.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import java.util.Calendar;

import android.net.Uri;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by manthole on 03 Oct 2017.
 */

public class GeoLocations {


    android.support.v4.app.NotificationCompat.Builder builder;
    private Context ctx;

    static String geofenceMessage =  "You Have Entered Code Tribe Tembisa";
    int geofenceImage;

    public GeoLocations(Context ctx) {
        this.ctx = ctx;
    }


    public void EnterCodetribeTembisa() {

        geofenceImage = R.drawable.allnotifications;

        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notify Notification")
                        .setContentText(geofenceMessage)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);


        Intent intent = new Intent(ctx.getApplicationContext(), RecycleNotifications.class);
        intent.putExtra("image", geofenceImage);
        intent.putExtra("message", geofenceMessage);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        Intent buttonIntent = new Intent(ctx.getApplicationContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 0, buttonIntent, 0);

        builder.addAction(android.R.drawable.ic_menu_view, "VIEW", pendingIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());


        buildNotification(NOTIFICATION_ID);
    }
//
//    public PendingIntent getLaunchIntent(int notificationId, Context context) {
//
//        Intent intent = new Intent(context, RecycleNotifications.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra("notificationId", notificationId);
//        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//    }
//
//
//    private void clearNotification() {
//        int notificationId = getIntent().getIntExtra("notificationId", 0);
//
//        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.cancel(notificationId);
//    }



    public void EnterEwcCanteen() {


        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notify Notification")
                        .setContentText("You Have Entered Ewc Canteen")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(ctx.getApplicationContext(), RecycleNotifications.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        Intent buttonIntent = new Intent(ctx.getApplicationContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 0, buttonIntent, 0);

        builder.addAction(android.R.drawable.ic_menu_view, "VIEW", pendingIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }


    public void EnterEwcHall() {

        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notify Notification")
                        .setContentText("You Have Entered Maponya Mall")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(ctx.getApplicationContext(), RecycleNotifications.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        Intent buttonIntent = new Intent(ctx.getApplicationContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 0, buttonIntent, 0);

        builder.addAction(android.R.drawable.ic_menu_view, "VIEW", pendingIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());


//        Intent intent = new Intent(ctx, Notification.class);
//
//        //Build Notification
//        NotificationCompat.Builder mBuilder =
//                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Notify Notification")
//                        .setContentText("You Have Entered Ewc Hall");
//
//
//        //Gets an instance of the NotificationManager service
//        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
//
//        //to post your notification to the the notification bar
//        notificationManager.notify(0, mBuilder.build());


    }


    public void EnterBirchAcresMall() {

        Intent intent = new Intent(ctx, Notification.class);

        //Build Notification
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notify Notification")
                        .setContentText("You Have Entered Birch Acres Mall");


        //Gets an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);

        //to post your notification to the the notification bar
        notificationManager.notify(0, mBuilder.build());


    }


    private void buildNotification(int NOTIFICATION_ID) {
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }


}


//        Intent intent = new Intent(ctx, Notification.class);
//
//        //Build Notification
//        NotificationCompat.Builder mBuilder =
//                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Notify Notification")
//                        .setContentText("You Have Entered Code Tribe Tembisa");
//
//
//        //Gets an instance of the NotificationManager service
//        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        mBuilder.setSound(alarmSound);
//        //to post your notification to the the notification bar
//        notificationManager.notify(0, mBuilder.build());
//


