package com.ihm.stoaliment.consommateur.produit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

public class ProduitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        Produit produit = (Produit) bundle.getSerializable("PRODUIT");

        ((TextView)findViewById(R.id.textViewLabelProduit)).setText(produit.getLabel());

        Producteur producteur = produit.getProducteur();
        ((TextView)findViewById(R.id.textViewProducteurProduit)).setText(producteur.getName() + " - " + producteur.getCodePostal() + " - " + producteur.getVille());
        ((ImageView)findViewById(R.id.imageViewImageProduit)).setImageResource(produit.getImage());
    }
}
