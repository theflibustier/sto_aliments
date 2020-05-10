package com.ihm.stoaliment.consommateur.abonnement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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

public class DisplayAbonnementActivity extends AppCompatActivity implements Observer {

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

        if(listView.getAdapter().getCount() == 0){
            findViewById(R.id.aucunAbonnement).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        producteurs = (List<Producteur>) arg;
        abonnementListAdapter.addAll(producteurs);
        abonnementListAdapter.notifyDataSetChanged();
    }
}
