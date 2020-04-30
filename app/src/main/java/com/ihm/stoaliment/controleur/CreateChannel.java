package com.ihm.stoaliment.controleur;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.Objects;

public class CreateChannel extends Application {
    public static final String CHANNEL_ID = "channel";


    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel1);
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }
}
