package com.ihm.stoaliment.consommateur.produit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.controleur.ProduitControleur;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.util.Observable;
import java.util.Observer;

public class DetailProduitActivity extends AppCompatActivity implements Observer {
    Intent intent;
    String idProducteur;
    Producteur producteur;
    ProducteurControleur producteurControleur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produit);

        intent = this.getIntent();

        idProducteur = intent.getStringExtra("PRODUCTEUR");

        final String idProduit = intent.getStringExtra("PRODUIT");
        producteurControleur = new ProducteurControleur(this);
        ProduitControleur produitControleur = new ProduitControleur(this);
        produitControleur.addObserver(this);
        producteurControleur.addObserver(this);
        produitControleur.loadProduit(idProducteur, idProduit);

        producteurControleur.loadProducteur(idProducteur);
    }

    @Override
    public void update(Observable observable, Object o) {

        Producteur producteur;
        Produit produit;
        if(observable instanceof ProducteurControleur){
            producteur = (Producteur) o;
            ((TextView)findViewById(R.id.ProducteurDetailProduit)).setText("Producteur: " + producteur.getNom());
        }
        if(observable instanceof ProduitControleur){
            produit = (Produit) o;
            ((TextView)findViewById(R.id.LabelDetailProduit)).setText("Produit: " + produit.getLabel());
            ((TextView)findViewById(R.id.prixDetailProduit)).setText("Prix: "+produit.getPrix()+"€");
            ((TextView)findViewById(R.id.quantiteDetailProduit)).setText("Quantité: "+produit.getQuantite()+"Kg");
            ((TextView)findViewById(R.id.retraitDetailProduit)).setText("Retrait de "+produit.getHeureDebut()+ "h à "+produit.getHeureFin()+"h");
            ((ImageView)findViewById(R.id.imageDetailProduit)).setImageBitmap(produit.getImage());
        }
        View load = findViewById(R.id.load);
        load.setVisibility(View.GONE);
    }
}
