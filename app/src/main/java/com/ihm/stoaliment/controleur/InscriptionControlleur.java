package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;


import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public class InscriptionControlleur extends Observable implements View.OnClickListener, OnSuccessListener<Location>, CompoundButton.OnCheckedChangeListener{

    private Activity activity;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String TAG = "DATABASE";

    private GeoPoint location;

    public InscriptionControlleur(Activity activity){

        this.activity = activity;
    }

    private void inscription(final Object obj, final String identifiant, final String type){

        db.collection("authentification").whereEqualTo("identifiant", identifiant).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    if(!task.getResult().isEmpty()){

                        Log.d(TAG, "Error getting documents: ", task.getException());

                        setChanged();
                        notifyObservers("Cette identifiant existe déjà...");
                    }
                    else {

                        db.collection(type)
                                .add(obj)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                        Authentification authentification = new Authentification(documentReference.getId(), identifiant, type);

                                        db.collection("authentification")
                                                .add(authentification)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());

                                                        setChanged();
                                                        notifyObservers("");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error adding document", e);
                                                    }
                                                });

                                        setChanged();
                                        notifyObservers("");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }

                } else {
                    Log.d(TAG, "Erreur d'autentification", task.getException());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_coordonne : getCoordonne(); break;
            case R.id.btn_valider_inscription : confirmationInscription(); break;
        }
    }

    private void confirmationInscription(){



        TextInputLayout identifiant = activity.findViewById(R.id.text_layout_identifiant);
        TextInputLayout nom = activity.findViewById(R.id.text_layout_nom);
        TextInputLayout adresse = activity.findViewById(R.id.text_layout_adresse);
        TextInputLayout ville = activity.findViewById(R.id.text_layout_ville);
        TextInputLayout cp = activity.findViewById(R.id.text_layout_cp);



        List<TextInputLayout> inputLayouts = Arrays.asList(identifiant, nom, adresse, ville, cp);

        boolean allDone = true;
        for (TextInputLayout til : inputLayouts) if(!validation(til)) allDone = false;
        if(allDone){

            if( ((CheckBox) activity.findViewById(R.id.check_box_producteur)).isChecked()){

                if(location == null){

                    ((TextView) activity.findViewById(R.id.textView_location)).setTextColor(Color.RED);
                    notifyObservers("Veuillez indiquer votre localisation...");
                    setChanged();
                }
                else{

                    Producteur producteur = new Producteur(nom.getEditText().getText().toString(),
                            cp.getEditText().getText().toString(), ville.getEditText().getText().toString(),
                            adresse.getEditText().getText().toString(), location);

                    inscription(producteur, identifiant.getEditText().getText().toString(), Authentification.PRODUCTEUR_TYPE);
                }


            }

            else{

                Consommateur consommateur = new Consommateur(nom.getEditText().getText().toString(),
                        adresse.getEditText().getText().toString(), ville.getEditText().getText().toString(),
                        cp.getEditText().getText().toString());

                inscription(consommateur, identifiant.getEditText().getText().toString(), Authentification.CONSOMMATEUR_TYPE);
            }
        }


    }


    private boolean validation(TextInputLayout til){

        if( til.getEditText().getText().toString().isEmpty() ){

            til.setError("Champ obligatoire...");
            return false;
        }

        return true;
    }



    private void getCoordonne(){

        LocationServices.getFusedLocationProviderClient(activity).getLastLocation().addOnSuccessListener(this);

    }

    @Override
    public void onSuccess(Location location) {

        if(location != null){

            activity.findViewById(R.id.textView_location).setVisibility(View.INVISIBLE);
            this.location = new GeoPoint(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){

            activity.findViewById(R.id.button_coordonne).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.textView_location).setVisibility(View.VISIBLE);
        }else{

            activity.findViewById(R.id.button_coordonne).setVisibility(View.INVISIBLE);
            activity.findViewById(R.id.textView_location).setVisibility(View.INVISIBLE);
        }
    }
}
