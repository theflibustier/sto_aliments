package com.ihm.stoaliment.producteur.produit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.ihm.stoaliment.R;

import static com.ihm.stoaliment.producteur.produit.CreateChannel.CHANNEL_ID;


public class NotificationSenderActivity extends AppCompatActivity {
    private int notificationId = 0;

    private void sendNotificationOnChannel(String title, String content, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(priority);
        notification.setSmallIcon(R.drawable.cerises);
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
                String joursRestants = "";
                if(extras!=null){
                    produit = extras.getString("Produit");
                    quantite = extras.getString("Quantité");
                    joursRestants = extras.getString("Jours restants");
                }
                String title = ((EditText) findViewById( R.id.edit_text_title)).getText().toString();
                String message = ((EditText) findViewById( R.id.edit_text_message)).getText().toString() + "Il ne reste plus que "+ joursRestants + " jours pour choper mes " + quantite + " " +produit+"s";
                sendNotificationOnChannel( title, message, CHANNEL_ID, NotificationCompat.PRIORITY_LOW );
            }
        });
    }
}
