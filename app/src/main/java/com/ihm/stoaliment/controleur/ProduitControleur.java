package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.consommateur.produit.DetailProduitActivity;
import com.ihm.stoaliment.model.Produit;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class ProduitControleur extends Observable implements AdapterView.OnItemClickListener {

    private Activity activity;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "DATABASE";

    public ProduitControleur(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Produit produit = (Produit) parent.getItemAtPosition(position);

        Intent intent = new Intent(activity.getApplicationContext(), DetailProduitActivity.class);
        intent.putExtra("PRODUIT", produit.getId());
        intent.putExtra("PRODUCTEUR", produit.getId_producteur());
        activity.startActivity(intent);
    }

    public void addProduit(Produit p, Uri imageuri){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String nomImage = "produit/"+ System.currentTimeMillis()+".jpg";
        StorageReference mstorageRef = FirebaseStorage.getInstance().getReference("produit").child(nomImage);

        if(imageuri!=null){
            mstorageRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                    System.out.println("Image Push");
                }
            });
        }

        p.setImageUrl(nomImage);
        p.setId_producteur(Authentification.producteur.getId());

        db.collection("producteur/"+ Authentification.producteur.getId() +"/produit")
                .add(p)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(activity.getBaseContext(),"Produit Ajouté avec succès", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity.getBaseContext(),"Erreur lors de la saisie", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void loadProduits(String idProducteur){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("producteur/"+idProducteur+"/produit/").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if(task.getResult().isEmpty()){

                        Toast.makeText(activity,"Aucun produit trouvé", Toast.LENGTH_SHORT).show();
                    }


                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("DATABASE",document.getId() + " => " + document.getData());

                        final Produit produit = document.toObject(Produit.class);
                        produit.setId(document.getId());
                        System.out.println(produit.getId());

                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference islandRef = firebaseStorage.getReference().child(produit.getImageUrl());

                        System.out.println(produit.getImageUrl());

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
                                produit.setImage(bitmap);
                                setChanged();
                                notifyObservers(produit);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }

                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void loadProduit(final String idProducteur, final String idProduit){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("producteur/"+idProducteur+"/produit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getId().equals(idProduit)){
                            Log.d("DATABASE",document.getId() + " => " + document.getData());

                            final Produit produit = document.toObject(Produit.class);
                            produit.setId(document.getId());

                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                            StorageReference islandRef = firebaseStorage.getReference().child(produit.getImageUrl());

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
                                    produit.setImage(bitmap);
                                    setChanged();
                                    notifyObservers(produit);
                                    return;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    return;
                                }
                            });
                        }
                    }

                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
