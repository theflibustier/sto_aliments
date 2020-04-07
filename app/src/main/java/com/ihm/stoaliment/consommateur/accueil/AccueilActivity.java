package com.ihm.stoaliment.consommateur.accueil;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Produit;

import java.util.List;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        AccueilControlleur accueilControlleur = new AccueilControlleur();
        List<Produit> produits = accueilControlleur.chargeProduit();

        System.out.println(produits);

        ProduitListAdapter produitListAdapter = new ProduitListAdapter(this, produits);
        ListView listView = (ListView) findViewById(R.id.listViewProduit);
        listView.setAdapter(produitListAdapter);
    }
}
