package com.ihm.stoaliment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.controleur.AuthentificationControleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.producteur.accueil.AccueilProducteurActivity;

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
