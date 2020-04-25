package com.ihm.stoaliment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ihm.stoaliment.consommateur.accueil.AccueilActivity;
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

    public void sendMessage(View view) {
        Intent intent = new Intent(this, AjoutProduitActivity.class);
        startActivity(intent);
    }


    public void accueilConsomateur(View view){

        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
    }

    public void AfficheAbonne(View view){

        Intent intent = new Intent(this, AfficheAbonneActivity.class);
        startActivity(intent);
    }

    public void mapDisplay(View view){

        Intent intent = new Intent( this, AutourActivity.class);
        startActivity(intent);
        Intent intent2 = new Intent( this, GeolocalisationActivity.class);
        startActivity(intent2);
    }

    public void geolocalisation(View view){

        Intent intent = new Intent( this, GeolocalisationActivity.class);
        startActivity(intent);
    }
}
