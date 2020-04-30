package com.ihm.stoaliment.producteur.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        deconnexion = findViewById(R.id.producteurDeconnexion);
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccueilProducteurActivity.this, AuthentificationActivity.class);
                startActivity(intent);
            }
        });
    }



}
