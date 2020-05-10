package com.ihm.stoaliment.consommateur.accueil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.location.LocationServices;
import com.ihm.stoaliment.controleur.GeolocalisationControleur;
import com.ihm.stoaliment.controleur.NotificationControlleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.authentification.AuthentificationActivity;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.BaseConsommateurActivity;
import com.ihm.stoaliment.consommateur.autour.AutourActivity;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.model.Producteur;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AccueilConsommateurActivity extends BaseConsommateurActivity implements Observer {

    private ProducteurListAdapter producteurListAdapter;
    private ProducteurControleur producteurControleur;
    private GeolocalisationControleur geolocalisationControleur;
    List<Producteur> producteurs;
    Location currentlocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_accueil_consommateur);

        int distance = this.getIntent().getIntExtra("distance", -1);


        Toast.makeText(AccueilConsommateurActivity.this, "la distance : " + distance,
                Toast.LENGTH_SHORT).show();

        System.out.println("samsung : " + distance);

        Toolbar mytoolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        View BtnAutourDeMoi = findViewById(R.id.autourDeMoi);
        BtnAutourDeMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AutourActivity.class);
                startActivity(intent);
            }
        });


        producteurControleur = new ProducteurControleur(this);
        producteurControleur.addObserver(this);

        producteurControleur.setDistance(distance);

        geolocalisationControleur = new GeolocalisationControleur(this);
        geolocalisationControleur.addObserver(this);
        geolocalisationControleur.loadPosition();

        producteurControleur.onSuccess(currentlocation);


        NotificationControlleur notificationControlleur = new NotificationControlleur(this);
        notificationControlleur.loadLastNotif(Authentification.consommateur.getId());


        TextView filter = (TextView) findViewById(R.id.filtrer);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilConsommateurActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_accueil_consommateur;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.action_home;
    }

    @Override
    public void update(Observable o, Object arg) {

        if(arg instanceof Location){
            currentlocation = (Location) arg;
        }

        if(o instanceof ProducteurControleur) {

            producteurs = (List<Producteur>) arg;
            producteurListAdapter = new ProducteurListAdapter(this, producteurs);
            ListView listView = findViewById(R.id.listViewProducteur);
            listView.setAdapter(producteurListAdapter);
            listView.setOnItemClickListener(producteurControleur);


            findViewById(R.id.layout_load).setVisibility(View.GONE);
            findViewById(R.id.layout_list).setVisibility(View.VISIBLE);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_deconnexion) {

            SharedPreferences mPrefs = Authentification.preferences;
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.remove(Authentification.GSON_LABEL);
            prefsEditor.apply();

            Intent intent = new Intent(this, AuthentificationActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
