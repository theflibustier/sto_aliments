package com.ihm.stoaliment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.controleur.AuthentificationControleur;
import com.ihm.stoaliment.producteur.accueil.AccueilProducteurActivity;

import java.util.Observable;
import java.util.Observer;

public class AuthentificationActivity extends AppCompatActivity implements Observer{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        AuthentificationControleur authentificationControleur = new AuthentificationControleur(this);
        authentificationControleur.addObserver(this);
        findViewById(R.id.btn_valider).setOnClickListener(authentificationControleur);
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println(Authentification.userType);
        if (Authentification.userType.equals(Authentification.CONSOMMATEUR_TYPE)) {
            System.out.println(Authentification.consommateur.getNom());
            Intent intent = new Intent(this, AccueilConsommateurActivity.class);
            startActivity(intent);
        } else {
            System.out.println(Authentification.producteur.getNom());
            Intent intent = new Intent(this, AccueilProducteurActivity.class);
            startActivity(intent);
        }
    }
}
