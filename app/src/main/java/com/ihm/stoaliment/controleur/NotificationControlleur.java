package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.model.Produit;

import java.util.List;

import static com.ihm.stoaliment.controleur.CreateChannel.CHANNEL_ID;

public class NotificationControlleur {

    Activity activity;

    public NotificationControlleur(Activity activity){
        this.activity =activity;
    }

    public void sendNotifToAlertNewProduct(Produit produit, List<String> abonnesId){
        String _message = "Venez chez moi entre " + produit.getHeureDebut() + "h et " + produit.getHeureFin() + "h pour récupérer mes " + produit.getQuantite() + " kg de " + produit.getLabel();
        sendNotificationOnChannel("Oyé oyé", _message, CHANNEL_ID, NotificationCompat.PRIORITY_HIGH);
        Toast.makeText(activity.getBaseContext(), "La notification a été envoyée à tous vos abonnés", Toast.LENGTH_SHORT).show();
    }

    private void sendNotificationOnChannel(String title, String content, String channelId, int priority) {
        //Sert à envoyer vers la classe onClickNotif
        PendingIntent contentIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0,
                new Intent(activity.getApplicationContext(), AccueilConsommateurActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(activity.getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(priority);
        notification.setSmallIcon(R.drawable.abonne);
        notification.setContentIntent(contentIntent);
        int notificationId = 0;
        NotificationManagerCompat.from(activity.getApplicationContext()).notify(notificationId, notification.build() );
    }
}
