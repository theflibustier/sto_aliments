package com.ihm.stoaliment.producteur.accueil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.authentification.AuthentificationActivity;
import com.ihm.stoaliment.R;

public class AccueilProducteurActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ImageView addProduct;
    Button coordonnees;
    Button seeAbonnes;
    Button deconnexion;
    Button acueilEnTravaux;

    private HomeFragment homeFragment;
    private ProduitFragment produitFragment;
    private AbonneFragment abonneFragment;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_producteur);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        homeFragment = null;
        produitFragment = null;
        abonneFragment = null;

        onNavigationItemSelected(bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId()));
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){


            case R.id.navigation_home :

                if(homeFragment == null)
                    homeFragment = new HomeFragment();

                transaction.replace(R.id.fragment, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;


            case R.id.navigation_produit :

                if(produitFragment == null)
                    produitFragment = new ProduitFragment();

                transaction.replace(R.id.fragment, produitFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;

            case R.id.navigation_abonne :

                if(abonneFragment == null)
                    abonneFragment = new AbonneFragment();

                transaction.replace(R.id.fragment, abonneFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
        }

        return false;
    }
}
