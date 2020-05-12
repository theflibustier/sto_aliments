package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.producteur.abonneList.AbonneActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ConsommateurControlleur extends Observable implements AdapterView.OnItemClickListener{

    private Activity activity;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "DATABASE";

    public ConsommateurControlleur(Activity activity){

        this.activity = activity;
        //abonneList.loadAbonnes();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Consommateur consommateur = (Consommateur) parent.getItemAtPosition(position);

        Intent intent = new Intent(activity.getApplicationContext(), AbonneActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ABONNE", consommateur);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void loadConsommateur(String id){

        db.collection("consommateur").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG,document.getId() + " => " + document.getData());
                        final Consommateur consommateur = document.toObject(Consommateur.class);
                        consommateur.setId(document.getId());
                        setChanged();
                        notifyObservers(consommateur);
                    }
                } else {
                    Log.d(TAG, "Consommateur n'existe pas ", task.getException());
                }
            }
        });
    }

    public void loadAbonnes(String producteurId){
        db.collection("consommateur").whereArrayContains("producteursSuivis",producteurId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Consommateur> abonnes = new ArrayList<>();
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       if (document.exists()) {
                           Log.d(TAG, document.getId() + " => " + document.getData());
                           final Consommateur consommateur = document.toObject(Consommateur.class);
                           consommateur.setId(document.getId());
                           abonnes.add(consommateur);
                       }
                   }
                   setChanged();
                   notifyObservers(abonnes);
                } else {
                    Log.d(TAG, "Consommateur n'existe pas ", task.getException());
                }
            }
        });

    }
}