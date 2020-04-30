package com.ihm.stoaliment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.consommateur.autour.AutourActivity;
import com.ihm.stoaliment.consommateur.autour.GeolocalisationActivity;
import com.ihm.stoaliment.producteur.abonneList.AfficheAbonneActivity;
import com.ihm.stoaliment.producteur.produit.AjoutProduitActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void authentification(View view){

        Intent intent = new Intent(this, AuthentificationActivity.class);
        startActivity(intent);
    }



    public void mapDisplay(View view){
        Intent intent = new Intent( this, AutourActivity.class);
        startActivity(intent);
    }

    public void geolocalisation(View view){

        Intent intent = new Intent( view.getContext(), GeolocalisationActivity.class);
        startActivity(intent);
    }
}
