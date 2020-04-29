package com.ihm.stoaliment.producteur.produit;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.AccueilActivity;
import com.ihm.stoaliment.model.Producteur;

import static com.ihm.stoaliment.producteur.produit.CreateChannel.CHANNEL_ID;


public class NotificationSenderActivity extends AppCompatActivity {
    private int notificationId = 0;

    private void sendNotificationOnChannel(String title, String content, String channelId, int priority) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AccueilActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(priority);
        notification.setSmallIcon(R.drawable.abonne);
        notification.setContentIntent(contentIntent);
        NotificationManagerCompat.from(this).notify(notificationId, notification.build() );
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alert);
        findViewById( R.id.buttonNotify ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String produit = "";
                String quantite = "";
                int heureDebut=0;
                int heureFin=0;
                if(extras!=null){
                    produit = extras.getString("Produit");
                    quantite = extras.getString("Quantité");
                    heureDebut = Integer.parseInt(extras.getString("heureDebut"));
                    heureFin = Integer.parseInt(extras.getString("heureFin"));
                }
                String title = ((EditText) findViewById( R.id.edit_text_title)).getText().toString();
                String message = Producteur.curProducteur.getNom() + " - Il ne reste plus que "+ (heureDebut - heureFin) + " jours pour choper mes " + quantite + " " +produit + "\n"+ ((EditText) findViewById( R.id.edit_text_message)).getText().toString();
                sendNotificationOnChannel( title, message, CHANNEL_ID, NotificationCompat.PRIORITY_LOW );
                Toast.makeText(getBaseContext(), "La notification a été envoyée à tous vos abonnés", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
