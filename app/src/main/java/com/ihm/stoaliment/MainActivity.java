package com.ihm.stoaliment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.consommateur.autour.AutourActivity;
import com.ihm.stoaliment.consommateur.autour.GeolocalisationActivity;
import com.ihm.stoaliment.controleur.AuthentificationControleur;
import com.ihm.stoaliment.producteur.abonneList.AfficheAbonneActivity;
import com.ihm.stoaliment.producteur.accueil.AccueilProducteurActivity;
import com.ihm.stoaliment.producteur.produit.AjoutProduitActivity;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentification.preferences = getPreferences(MODE_PRIVATE);
        String identifiant = Authentification.preferences.getString(Authentification.GSON_LABEL, "");

        if(identifiant.isEmpty()){

            Intent intent = new Intent(this, AuthentificationActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            AuthentificationControleur authentificationControleur = new AuthentificationControleur(this);
            authentificationControleur.addObserver(this);
            authentificationControleur.loadAuthentification(identifiant);
        }


    }

    @Override
    public void update(Observable o, Object arg) {

        if(arg != null){

            Intent intent;

            if (Authentification.userType.equals(Authentification.CONSOMMATEUR_TYPE))
                intent = new Intent(this, AccueilConsommateurActivity.class);
            else
                intent = new Intent(this, AccueilProducteurActivity.class);

            startActivity(intent);
            finish();
        }
    }
}
