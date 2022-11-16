package com.bikashgurung.stocard.Receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bikashgurung.stocard.MainActivity;
import com.bikashgurung.stocard.R;

import java.util.ArrayList;
import java.util.List;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarReceiver;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;

public class MyRadarReceiver extends RadarReceiver {

    public static final String CHANNEL_ID = "channel";

    @Override
    public void onEventsReceived(Context context, RadarEvent[] events, RadarUser user) {
        // do something with events, user
        for (int i = 0; i < events.length; i++) {
            RadarEvent event = events[i];
            String type = String.valueOf(event.getType());
            String locationName = event.getGeofence().getExternalId();

            System.out.println("event : " + event.toJson());
            System.out.println("event : " + type);

            transitionsFinder(context, type, locationName);
            //createNotificationChannel(context, events, user);
        }
    }

    private void transitionsFinder(Context context, String type, String locationName) {
        if (type == "USER_ENTERED_GEOFENCE") {
            //createNotificationChannel(context, type, locationName);
        } else if (type == "USER_EXITED_GEOFENCE") {
           // createNotificationChannel(context, type, locationName);
        } else {
            System.out.println("You are in " + locationName);
        }
    }


    @Override
    public void onLocationUpdated(Context context, Location location, RadarUser user) {
        // do something with location, user
    }

    @Override
    public void onClientLocationUpdated(Context context, Location location, boolean stopped, Radar.RadarLocationSource source) {
        // do something with location, stopped, source
    }

    @Override
    public void onError(Context context, Radar.RadarStatus status) {
        // do something with status
    }

    @Override
    public void onLog(Context context, String message) {
        // print message for debug logs
    }

    private void createNotificationChannel(Context context, String event_type, String locationName) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Bikash";
            String description = "Channel for location Alert..";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("event_type", event_type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (event_type == "USER_ENTERED_GEOFENCE") {
            System.out.println("You have entered in " + locationName);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_account_icon)
                    .setContentTitle("Location Alert!!!")
                    .setContentText("You have entered in " + locationName)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(123, builder.build());

        } else if (event_type == "USER_EXITED_GEOFENCE") {
            System.out.println("You have exited from " + locationName);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_account_icon)
                    .setContentTitle("Location Alert!!!")
                    .setContentText("You have exited from " + locationName)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(123, builder.build());

        } else {
            System.out.println("You are in " + locationName);
        }
    }


}
