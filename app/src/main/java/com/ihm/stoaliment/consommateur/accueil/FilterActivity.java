package com.ihm.stoaliment.consommateur.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.GeoPoint;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.autour.AutourActivity;
import com.ihm.stoaliment.controleur.AuthentificationControleur;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class FilterActivity extends AppCompatActivity implements Observer {
    private SeekBar distanceSeekBar;
    private SeekBar priceSeekBar;
    private SeekBar favSeekBar;

    public int distance = 0;
    public int price = 0;
    public int numberStarsFav = 0;

    List<Producteur> producteursFiltered = new ArrayList<>();
    ProducteurControleur producteurControleur;

    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_filtrer);

        producteurControleur = new ProducteurControleur(this);
        producteurControleur.addObserver(this);
        producteurControleur.loadProducteurs();


        //Revenir à la page précédente
        TextView close = (TextView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this, AccueilConsommateurActivity.class);
                startActivity(intent);
            }
        });

        // recupère tous les elements seekbar
        distanceSeekBar = (SeekBar) findViewById(R.id.distanceSeekBar);
        priceSeekBar = (SeekBar) findViewById(R.id.priceSeekBar);
        favSeekBar = (SeekBar) findViewById(R.id.favSeekBar);

        Button valider = (Button) findViewById(R.id.valider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutourActivity autourActivity = new AutourActivity();
                autourActivity.clearOverlay();
                notifyAll();
                autourActivity.setFilteredProductors(producteursFiltered);
            }
        });


        // avoir la value on set un listener dessus
        favSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //au changement on recupère la nouvelle valeur
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberStarsFav = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            //toast : montre la valeur
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(FilterActivity.this, "Vous avez selectionné : " + distance + "étoile(s)",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // avoir la value on set un listener dessus
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //au changement on recupère la nouvelle valeur
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                price = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            //toast : montre la valeur
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(FilterActivity.this, "Prix : " + price + "€",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // avoir la value on set un listener dessus
        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //au changement on recupère la nouvelle valeur
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            //toast : montre la valeur
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(FilterActivity.this, "Distance : " + numberStarsFav + " km",
                        Toast.LENGTH_SHORT).show();
            }
        });
        }

    public int getDistance(){
        return 0;
    }

    public boolean isInRangeDistance(){
        return  false;
    }

    @Override
    public void update(Observable observable, Object o) {

            Producteur producteur = (Producteur) o;


            GeoPoint curCosommateurPosition = Authentification.consommateur.getLocationConsommateur();
            GeoPoint producteurPosition = producteur.getLocation();


            double distanceInMeters = distance(curCosommateurPosition.getLatitude(),
                    producteurPosition.getLatitude(),
                    curCosommateurPosition.getLongitude(),
                    producteurPosition.getLongitude(),
                    0,0);

            Log.d("info", "id consommateur :  " + Authentification.consommateur.getId());

            Log.d("distance", "distance = " + distance);

            if(distanceInMeters / 0.001 <= distance){
                producteursFiltered.add(producteur);
            }

    }

    public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
