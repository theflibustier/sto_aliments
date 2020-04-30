package com.ihm.stoaliment.consommateur.produit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.ProducteurListAdapter;
import com.ihm.stoaliment.consommateur.accueil.ProduitListAdapter;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.controleur.ProduitControleur;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DisplayProduit extends AppCompatActivity implements Observer {
    private ProduitListAdapter produitListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_produit);

        String idProducteur = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idProducteur = extras.getString("idProducteur");
        }

        ProduitControleur produitControleur = new ProduitControleur(this);
        produitControleur.addObserver(this);
        produitControleur.loadProduits(String.valueOf(idProducteur));

        List<Produit> produits = new ArrayList<>();
        produitListAdapter = new ProduitListAdapter(this, produits);
        ListView listView = findViewById(R.id.listViewProduit);
        listView.setAdapter(produitListAdapter);

        listView.setOnItemClickListener(produitControleur);
    }

    @Override
    public void update(Observable o, Object arg) {

        produitListAdapter.add(arg);
        produitListAdapter.notifyDataSetChanged();

        View load = findViewById(R.id.load);
        load.setVisibility(View.GONE);
    }
}
