package com.ihm.stoaliment.consommateur.produit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.controleur.ProduitControleur;
import com.ihm.stoaliment.model.Produit;

import java.util.Observable;
import java.util.Observer;

public class DetailProduitActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);

        Intent intent = this.getIntent();

        final String idProducteur = intent.getStringExtra("PRODUCTEUR");
        final String idProduit = intent.getStringExtra("PRODUIT");

        ProduitControleur produitControleur = new ProduitControleur(this);
        produitControleur.addObserver(this);
        produitControleur.loadProduit(idProducteur, idProduit);
    }

    @Override
    public void update(Observable observable, Object o) {

        Produit produit = (Produit) o;

        System.out.println(produit.getLabel());

        ((TextView)findViewById(R.id.LabelDetailProduit)).setText(produit.getLabel());
        ((TextView)findViewById(R.id.prixDetailProduit)).setText(String.valueOf(produit.getPrix()));
        ((TextView)findViewById(R.id.quantiteDetailProduit)).setText(String.valueOf(produit.getQuantite()));
        ((TextView)findViewById(R.id.retraitDebutDetailProduit)).setText(String.valueOf(produit.getHeureDebut()));
        ((TextView)findViewById(R.id.retraitFinDetailProduit)).setText(String.valueOf(produit.getHeureFin()));

        ((ImageView)findViewById(R.id.imageDetailProduit)).setImageBitmap(produit.getImage());
    }
}
