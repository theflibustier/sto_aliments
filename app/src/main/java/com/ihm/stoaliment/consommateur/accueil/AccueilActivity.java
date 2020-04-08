package com.ihm.stoaliment.consommateur.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.produit.ProduitActivity;
import com.ihm.stoaliment.model.Produit;
import com.ihm.stoaliment.model.ProduitList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AccueilActivity extends AppCompatActivity implements Observer {

    private AccueilControlleur accueilControlleur;
    private List<Produit> produits;
    private ProduitList produitList = new ProduitList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        produitList.addObserver(this);
        AccueilControlleur accueilControlleur = new AccueilControlleur(this, produitList);

        ProduitListAdapter produitListAdapter = new ProduitListAdapter(this, produits);
        ListView listView = findViewById(R.id.listViewProduit);
        listView.setAdapter(produitListAdapter);
        listView.setOnItemClickListener(accueilControlleur);
    }

    @Override
    public void update(Observable o, Object arg) {

        produits = (List<Produit>) arg;
    }
}
