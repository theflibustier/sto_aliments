package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.consommateur.producteur.DetailProducteurActivity;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Notification;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import static com.ihm.stoaliment.controleur.CreateChannel.CHANNEL_ID;

public class NotificationControlleur extends Observable {

    Activity activity;
    private String TAG = "DATABASE";

    public NotificationControlleur(Activity activity){
        this.activity = activity;
    }

    public void sendNotifToAlertNewProduct(Produit produit, Producteur producteur, List<String> abonnesId){
        String message = "Venez chez moi "+producteur.getNom() + "entre " + produit.getHeureDebut() + "h et " + produit.getHeureFin() + "h pour récupérer mes " + produit.getQuantite() + " kg de " + produit.getLabel();
        addNotifToDB(new Notification(producteur.getId(),abonnesId,message));
        //sendNotificationOnChannel("Oyé oyé", _message, CHANNEL_ID, NotificationCompat.PRIORITY_HIGH);
    }


    private void addNotifToDB(Notification notification){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notification/")
                .add(notification)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(activity.getBaseContext(), "La notification a été envoyée à tous vos abonnés", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity.getBaseContext(),"Erreur", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loadLastNotif(String consommateurId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notification").whereArrayContains("destinataires",consommateurId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Notification> notifications = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                final Notification notification = document.toObject(Notification.class);
                                notification.setId(document.getId());
                                notifications.add(notification);
                            }
                        }
                        setChanged();
                        notifyObservers(notifications);
                        for(Notification notification : notifications){
                            sendNotificationOnChannel(notification,CHANNEL_ID, NotificationCompat.PRIORITY_HIGH);
                            setNotifToSeen(notification);
                        }
                    } else {
                        Log.d(TAG, "Notif n'existe pas ", task.getException());
                    }
                }
            });

    }

    private void sendNotificationOnChannel(Notification notificationToSendOnSmartphone, String channelId, int priority) {
        //Sert à envoyer vers la classe onClickNotif
        Intent intent = new Intent(activity.getApplicationContext(), DetailProducteurActivity.class);
        intent.putExtra("PRODUCTEUR", notificationToSendOnSmartphone.getExpediteur());

        PendingIntent contentIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0,intent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(activity.getApplicationContext(), channelId);
        notification.setContentTitle("Oye oye");
        notification.setContentText(notificationToSendOnSmartphone.getMessage());
        notification.setStyle(new NotificationCompat.BigTextStyle().bigText(notificationToSendOnSmartphone.getMessage()));
        notification.setPriority(priority);
        notification.setSmallIcon(R.drawable.ic_notification_important_24px);
        notification.setContentIntent(contentIntent);
        notification.setAutoCancel(true).setDefaults(android.app.Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat.from(activity.getApplicationContext()).notify(notificationToSendOnSmartphone.getIntId(), notification.build() );
    }

    private void setNotifToSeen(Notification notification){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(notification.getDestinataires()==null || notification.getDestinataires().size()==0 || notification.getDestinataires().size()==1){
            db.collection("notification").document(notification.getId())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("La notif a été supprimée de la bd");
                }
            });
        }
        else  {
            db.collection("notification").document(notification.getId())
                    .update("destinataires", FieldValue.arrayRemove(Authentification.consommateur.getId()))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("La notif a été vue");
                        }
                    });
        }
    }
}
