package com.ihm.stoaliment.model;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
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
import com.ihm.stoaliment.MainActivity;

import java.io.File;
import java.io.IOException;
import java.sql.Ref;
import java.util.Observable;

public class ProduitModel extends Observable {

//    public void loadProduit(){
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("producteur"+Producteur.curProducteur.getId()+"/produit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//
//
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("DATABASE",document.getId() + " => " + document.getData());
//
//                        final Produit produit = document.toObject(Produit.class);
//
//                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//                        StorageReference islandRef = firebaseStorage.getReference().child(produit.getImageUrl());
//
//                        File localFile = null;
//                        try {
//                            localFile = File.createTempFile("images", "jpg");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        final File finalLocalFile = localFile;
//                        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                // Local temp file has been created
//                                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
//
//                                produit.setImage(bitmap);
//
//                                setChanged();
//                                notifyObservers(produit);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                            }
//                        });
//                    }
//
//                } else {
//                    Log.d("tag", "Error getting documents: ", task.getException());
//                }
//            }
//        });
//    }

    public void addProduit(Produit p, Uri imageuri){

        System.out.println("OUIIII");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String nomImage = System.currentTimeMillis()+".jpg";
        StorageReference mstorageRef = FirebaseStorage.getInstance().getReference("Images").child(nomImage);

        mstorageRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                System.out.println("Image Push");
            }
        });

        p.setImageUrl(nomImage);

        db.collection("producteur/hOzRKGOTExMMLeMQJ6up/produit")
        .add(p)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("Yessss");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error adding document", e);
            }
        });
    }
}
