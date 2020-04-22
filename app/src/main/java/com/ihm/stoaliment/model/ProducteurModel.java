package com.ihm.stoaliment.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ProducteurModel extends Observable {

    public void loadProducteur(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("producteur").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {


                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("DATABASE",document.getId() + " => " + document.getData());

                        final Producteur producteur = document.toObject(Producteur.class);

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
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
