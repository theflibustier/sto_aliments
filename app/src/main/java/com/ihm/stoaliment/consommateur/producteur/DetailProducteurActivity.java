package com.ihm.stoaliment.consommateur.producteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.produit.DisplayProduit;
import com.ihm.stoaliment.controleur.AbonneControleur;
import com.ihm.stoaliment.controleur.ConsommateurControlleur;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.controleur.ProduitControleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;

import java.util.Observable;
import java.util.Observer;

public class DetailProducteurActivity extends AppCompatActivity implements Observer {
    ProducteurControleur producteurControleur;
    AbonneControleur abonneControleur;
    private boolean abonner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_producteur);

        Intent intent = this.getIntent();

        final String idProducteur = intent.getStringExtra("PRODUCTEUR");

        producteurControleur = new ProducteurControleur(this);
        producteurControleur.addObserver(this);

        producteurControleur.loadProducteur(idProducteur);
        abonneControleur = new AbonneControleur(this);
        abonneControleur.addObserver(this);
        abonneControleur.checkAbonne(idProducteur);

        findViewById(R.id.btnListProduit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DisplayProduit.class);
                intent.putExtra("idProducteur", String.valueOf(idProducteur));
                startActivity(intent);
            }
        });

        if(abonner){
            ((Button)findViewById(R.id.btnAbonne)).setText("Se désabonner");
        }else{
            ((Button)findViewById(R.id.btnAbonne)).setText("S'abonner");
        }

        findViewById(R.id.btnAbonne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(abonner) {
                    abonneControleur.deleteAbonne(idProducteur);
                }else {
                    abonneControleur.addAbonne(idProducteur);
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

        if(o instanceof ProducteurControleur){
            Producteur producteur = (Producteur) arg;
            ((TextView)findViewById(R.id.textViewNomProducteur)).setText(producteur.getNom());
            ((ImageView)findViewById(R.id.imageViewImageProducteur)).setImageBitmap(producteur.getImage());
            View load = findViewById(R.id.load);
            load.setVisibility(View.GONE);
        }
        if (o instanceof AbonneControleur){
            abonner = (boolean)arg;
            ((Button)findViewById(R.id.btnAbonne)).setText( (abonner)? "Se désabonner" : "S'abonner");
        }
        if(o instanceof ConsommateurControlleur){
            Authentification.consommateur = (Consommateur) arg;
        }

    }
}