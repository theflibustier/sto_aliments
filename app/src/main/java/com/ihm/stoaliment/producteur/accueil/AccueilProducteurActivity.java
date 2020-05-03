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
import com.ihm.stoaliment.AuthentificationActivity;
import com.ihm.stoaliment.R;
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
                System.out.println("Pas encore fait");
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
