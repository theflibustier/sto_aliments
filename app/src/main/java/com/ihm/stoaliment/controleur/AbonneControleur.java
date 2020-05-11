package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class AbonneControleur extends Observable {
    private Activity activity;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "DATABASE";

    public AbonneControleur(Activity activity){
        this.activity = activity;
    }

    public void addAbonne(final String idProducteur) {
        db.collection("producteur").document(idProducteur)
                .update("listeAbonnes", FieldValue.arrayUnion(Authentification.consommateur.getId()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("consommateur").document(Authentification.consommateur.getId())
                                .update("producteursSuivis", FieldValue.arrayUnion(idProducteur))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                setChanged();
                                notifyObservers(true);
                                Toast.makeText(activity, "Vous êtes desormais abonné", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
    public void deleteAbonne(final String idProducteur) {
        db.collection("producteur").document(idProducteur)
                .update("listeAbonnes",FieldValue.arrayRemove(Authentification.consommateur.getId()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("consommateur").document(Authentification.consommateur.getId())
                                .update("producteursSuivis", FieldValue.arrayRemove(idProducteur))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                setChanged();
                                notifyObservers(false);
                                Toast.makeText(activity, "Vous êtes désabonné de ce producteur", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    public void checkAbonne(final String idProducteur) {
        db.collection("consommateur").document(Authentification.consommateur.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    final Consommateur consommateur = document.toObject(Consommateur.class);
                    setChanged();
                    assert consommateur != null;
                    if (consommateur.getProducteursSuivis().contains(idProducteur))
                        notifyObservers(true);
                    else
                        notifyObservers(false);
                }
            }
        });
    }
}
