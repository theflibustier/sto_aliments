package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ihm.stoaliment.Authentification;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class AuthentificationControleur extends Observable implements View.OnClickListener {


    private Activity activity;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String TAG = "DATABASE";



    public AuthentificationControleur(Activity activity){

        this.activity = activity;
    }

    private void loadAuthentification(String identifiant) {

        db.collection("authentification").whereEqualTo("identifiant", identifiant).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    if(task.getResult().isEmpty()){

                        Log.d(TAG, "Error getting documents: ", task.getException());
                        Toast.makeText(activity.getBaseContext(), "Cet identifiant n'existe pas", Toast.LENGTH_SHORT).show();
                    }

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d(TAG,document.getId() + " => " + document.getData());

                        Authentification authentification = document.toObject(Authentification.class);
                        if(authentification.getType().equals(Authentification.CONSOMMATEUR_TYPE))
                            loadConsommateur(authentification.getRef());

                        else
                            loadProducteur(authentification.getRef());

                    }
                } else {
                    Log.d(TAG, "Erreur d'autentification", task.getException());
                }

            }
        });
    }


    private void loadConsommateur(String id){

        db.collection("consommateur").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {

                        Log.d(TAG,document.getId() + " => " + document.getData());

                        final Consommateur consommateur = document.toObject(Consommateur.class);
                        consommateur.setId(document.getId());
                        System.out.println("ok");
                        Authentification.consommateur = consommateur;
                        Authentification.userType = Authentification.CONSOMMATEUR_TYPE;

                        setChanged();
                        notifyObservers(consommateur);
                    }
                } else {

                    Log.d(TAG, "Consommateur n'existe pas ", task.getException());
                }
            }
        });

    }

    private void loadProducteur(String id) {

        db.collection("producteur").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {

                        Log.d(TAG,document.getId() + " => " + document.getData());

                        final Producteur producteur = document.toObject(Producteur.class);
                        producteur.setId(document.getId());

                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference islandRef = firebaseStorage.getReference().child(producteur.getImageUrl());

                        File localFile = null;
                        try {
                            localFile = File.createTempFile("images", "jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final File finalLocalFile = localFile;
                        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Local temp file has been created
                                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());

                                producteur.setImage(bitmap);

                                Authentification.producteur = producteur;
                                Authentification.userType = Authentification.PRODUCTEUR_TYPE;

                                setChanged();
                                notifyObservers(producteur);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                } else {

                    Log.d(TAG, "Producteur n'existe pas", task.getException());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_valider :
                String identifiant = ((EditText) activity.findViewById(R.id.editTextIdentifiant)).getText().toString();
                loadAuthentification(identifiant);
        }
    }
}
