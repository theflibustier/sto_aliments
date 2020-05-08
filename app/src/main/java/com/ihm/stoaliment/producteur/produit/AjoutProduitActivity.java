package com.ihm.stoaliment.producteur.produit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.controleur.NotificationControlleur;
import com.ihm.stoaliment.controleur.ProduitControleur;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AjoutProduitActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final String TAG = "testttos";
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
    Switch switchNotif;
    Uri image_uri;
    Producteur producteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);

        final Producteur producteur = Authentification.producteur;
        final NotificationControlleur notificationControlleur = new NotificationControlleur(this);

        imgView = findViewById(R.id.imageView);
        btnCam = findViewById(R.id.btnCapture);
        btnValid = findViewById(R.id.valide);
        editTextQuantity = findViewById(R.id.edit_quantity);
        editTextLabel = findViewById(R.id.productName);
        SpinnerType = findViewById(R.id.spinnerProduct);
        prix = findViewById(R.id.prixProduit);
        heureDebut = findViewById(R.id.heureDebut);
        heureFin = findViewById(R.id.heureFin);
        switchNotif= findViewById(R.id.switchAbonne);

        ImageView sharedButton = (ImageView) findViewById(R.id.shareOnTwitter);

        sharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(requiredInputs()){
                    try {
                        shareOnTwitterWithPost("J'ai le plaisir de vous annoncer que les " + editTextLabel.getText().toString()
                                + " sont enfin disponible de " + heureDebut.getText().toString() + "h à "
                                + heureFin.getText().toString() + " h."
                                + "\n\n" + "Quantité limitée à " + editTextQuantity.getText().toString()
                                + " kg à " + prix.getText().toString() + " € le kilo."
                                + "\n\n" + "N'hésitez surtout pas et venez nombreux !");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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

                if(requiredInputs()){

                    Produit produit = new Produit();
                    int quantite = (String.valueOf(editTextQuantity.getText()).isEmpty())? 0 : Integer.parseInt(String.valueOf(editTextQuantity.getText()));
                    int price = (prix.getText().toString().isEmpty())? 0 : Integer.parseInt(prix.getText().toString());

                    String type = SpinnerType.getSelectedItem().toString();
                    String label = editTextLabel.getText().toString();
                    String hd = heureDebut.getText().toString();

                    String hf = heureFin.getText().toString();
                    produit.setQuantite(quantite);
                    produit.setLabel(label);
                    produit.setTypeProduit(type);
                    produit.setPrix(price);

                    if(!hd.isEmpty())produit.setHeureDebut(Integer.parseInt(hd));
                    if(!hf.isEmpty())produit.setHeureFin(Integer.parseInt(hf));
                    ProduitControleur produitControleur = new ProduitControleur(AjoutProduitActivity.this);
                    produitControleur.addProduit(produit, image_uri);
                    if(!switchNotif.isChecked()) return;

                    notificationControlleur.sendNotifToAlertNewProduct(produit,producteur, producteur.getListeAbonnes());
                }


            }
        });
    }

    //add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinner2 = findViewById(R.id.spinnerProduct);
        //récupérer la liste des produits que le producteur peut vendre
        List<String> list = new ArrayList<>();
        list.add("Salades");
        list.add("Tomates");
        list.add("Oignons");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
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
                    Toast.makeText(this, "Permission refusée, veuillez donner les accès a l'appareil photo", Toast.LENGTH_SHORT).show();
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


    private void shareOnTwitterWithPost(String message) throws IOException {

        Intent intentForTwitter = new Intent(Intent.ACTION_SEND);
        Bundle extra = new Bundle();
        
        intentForTwitter.putExtra(Intent.EXTRA_TEXT, message);

        String sourceFilename= image_uri.getPath();
        intentForTwitter.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(sourceFilename)));
        intentForTwitter.setType("image/jpeg");


        if (image_uri == null){
            Log.d("ERREUR", "iamge nullll");
        }else{
            Log.d("non", "iamge pas du tout null");
        }
//        intentForTwitter.putExtra(Intent.EXTRA_STREAM, image_uri);
//        intentForTwitter.setType("image/jpeg");
        /**/

//            String stringUri = image_uri.toString();
//
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
//
//            ByteArrayOutputStream bs = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
//            intentForTwitter.putExtra("byteArray", bs.toByteArray());

        /**/
        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intentForTwitter, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                intentForTwitter.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                intentForTwitter.setAction("POST");
                resolved = true;
                break;
            }
        }

        if (resolved) {
            startActivity(intentForTwitter);
        } else {
            Intent intent = new Intent();
            //ajout du message
            intent.putExtra(Intent.EXTRA_TEXT, message);
           // intent.putExtra("imageProduit", image_uri);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            //on lance l'activité
            startActivity(intent);
            //application non trouvée
            Toast.makeText(this, "L'application twitter n'a pas été trouvée", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            return "";
        }
    }

    public boolean requiredInputs(){
        boolean res = true;
        if(editTextLabel.length() == 0){
            res = false;
            editTextLabel.setError("Entrer le nom du produit");
        }else if(heureDebut.length() == 0) {
            res = false;
            heureDebut.setError("Entrer l'heure retrait");
        }else if(heureFin.length() == 0) {
            res = false;
            heureFin.setError("Entrer l'heure de fin de retrait");
        }
        else if(prix.length() == 0) {
            res = false;
            prix.setError("Entrer le prix du produit");
        }else if(editTextQuantity.length() == 0){
            res = false;
            editTextQuantity.setError("Entrer la quantité");
        }

        return res;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

}
