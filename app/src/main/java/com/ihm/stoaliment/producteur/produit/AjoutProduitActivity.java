package com.ihm.stoaliment.producteur.produit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.AccueilActivity;
import com.ihm.stoaliment.controleur.ProduitControleur;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.util.ArrayList;
import java.util.List;

import static com.ihm.stoaliment.producteur.produit.CreateChannel.CHANNEL_ID;

public class AjoutProduitActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button btnCam;
    Button btnValid;
    ImageView imgView;
    Spinner spinner2;
    EditText editTextQuantity;
    EditText editTextLabel;
    Spinner SpinnerType;
    EditText prix;
    EditText heureDebut;
    EditText heureFin;
    private int notificationId = 0;

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);

        imgView = findViewById(R.id.imageView);
        btnCam = findViewById(R.id.btnCapture);
        btnValid = findViewById(R.id.valide);
        editTextQuantity = findViewById(R.id.edit_quantity);
        editTextLabel = findViewById(R.id.productName);
        SpinnerType = findViewById(R.id.spinnerProduct);
        prix = findViewById(R.id.prixProduit);
        heureDebut = findViewById(R.id.heureDebut);
        heureFin = findViewById(R.id.heureFin);

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        //permission already granted
                        openCamera();
                    }
                }else{
                    //system < marshmallow
                    openCamera();
                }
            }
        });
        addItemsOnSpinner();

        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produit p = new Produit();
                p.setQuantite(Integer.parseInt(String.valueOf(editTextQuantity.getText())));
                p.setLabel(editTextLabel.getText().toString());
                p.setTypeProduit(SpinnerType.getSelectedItem().toString());
                p.setPrix(Integer.parseInt(prix.getText().toString()));
                p.setHeureDebut(Integer.parseInt(heureDebut.getText().toString()));
                p.setHeureFin(Integer.parseInt(heureFin.getText().toString()));
                ProduitControleur produitControleur = new ProduitControleur(AjoutProduitActivity.this);
                produitControleur.addProduit(p, image_uri);
                if((findViewById(R.id.switchAbonne).isEnabled())){
                    String _produit = editTextLabel.getText().toString();
                    String _quantite = editTextQuantity.getText().toString();
                    int _heureDebut = Integer.parseInt(heureDebut.getText().toString());
                    int _heureFin = Integer.parseInt(heureFin.getText().toString());
                    String _message = "Benoit de la verge - Il ne reste plus que "+ (_heureDebut - _heureFin) + " jours pour choper mes " + _quantite + " " +_produit + "\n Un message bon";
                    sendNotificationOnChannel( "Oyé oyé", _message, CHANNEL_ID, NotificationCompat.PRIORITY_LOW );
                    Toast.makeText(getBaseContext(), "La notification a été envoyée à tous vos abonnés", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinner2 = (Spinner) findViewById(R.id.spinnerProduct);
        List<String> list = new ArrayList<String>();
        list.add("Product 1");
        list.add("Product 1");
        list.add("Product 1");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Pic_STO");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent,IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else{
                    Toast.makeText(this, "Permission refusé, veuillez donner les acces a l'appareil photo", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imgView.setImageURI(image_uri);
            btnCam.getBackground().setAlpha(64);
        }
    }

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



}
