package com.ihm.stoaliment.consommateur.abonnement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ihm.stoaliment.authentification.AuthentificationActivity;
import com.ihm.stoaliment.consommateur.BaseConsommateurActivity;
import com.ihm.stoaliment.consommateur.accueil.AbonnementListAdapter;
import com.ihm.stoaliment.controleur.AbonneControleur;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Producteur;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DisplayAbonnementActivity extends BaseConsommateurActivity implements Observer {

    private List<Producteur> producteurs = new ArrayList<>();
    private AbonnementListAdapter abonnementListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_abonnement);

        ProducteurControleur producteurControleur = new ProducteurControleur(this);
        producteurControleur.addObserver(this);
        producteurControleur.loadAbonnement(Authentification.consommateur.getId());

        abonnementListAdapter = new AbonnementListAdapter(this, producteurs);
        ListView listView = findViewById(R.id.listViewAbonnes);
        listView.setAdapter(abonnementListAdapter);
        listView.setOnItemClickListener(producteurControleur);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_affiche_abonnement;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.action_fav;
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

    @Override
    public void update(Observable o, Object arg) {
        producteurs = (List<Producteur>) arg;
        if (producteurs.size() == 0)
            findViewById(R.id.aucunAbonnement).setVisibility(View.VISIBLE);
        abonnementListAdapter.addAll(producteurs);
        abonnementListAdapter.notifyDataSetChanged();
    }
}
