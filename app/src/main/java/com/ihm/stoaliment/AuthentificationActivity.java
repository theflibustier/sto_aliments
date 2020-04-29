package com.ihm.stoaliment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ihm.stoaliment.controleur.AuthentificationControleur;
import com.ihm.stoaliment.model.Consommateur;

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

        if(Authentification.userType.equals(Authentification.CONSOMMATEUR_TYPE)){

            System.out.println(Authentification.consommateur.getNom());
        }
        else
            System.out.println(Authentification.producteur.getNom());

    }
}
