package com.ihm.stoaliment.producteur.accueil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.authentification.AuthentificationActivity;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.producteur.CoordonneesActivity;
import com.ihm.stoaliment.producteur.abonneList.AfficheAbonneActivity;
import com.ihm.stoaliment.producteur.produit.AjoutProduitActivity;

public class AccueilProducteurActivity extends AppCompatActivity {
    ImageView addProduct;
    Button coordonnees;
    Button seeAbonnes;
    Button deconnexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_producteur);

        addProduct = findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilProducteurActivity.this, AjoutProduitActivity.class);
                startActivity(intent);
            }
        });

        coordonnees = findViewById(R.id.producteurCoordonnees);
        coordonnees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilProducteurActivity.this, CoordonneesActivity.class);
                startActivity(intent);
            }
        });

        seeAbonnes = findViewById(R.id.seeAbonnes);
        seeAbonnes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilProducteurActivity.this, AfficheAbonneActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
