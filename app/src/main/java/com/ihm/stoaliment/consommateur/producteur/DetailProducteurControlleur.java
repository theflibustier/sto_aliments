package com.ihm.stoaliment.consommateur.producteur;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ihm.stoaliment.model.Producteur;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class DetailProducteurControlleur extends Observable {

    private Activity activity;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "DATABASE";

    public DetailProducteurControlleur(Activity activity, String producteurId){

        this.activity = activity;
        loadProducteur(producteurId);
    }


    private void loadProducteur(String id) {

        db.collection("producteur").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
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
                    Log.d(TAG, "No such document");
                }

            }
        });
    }
}
