package com.ihm.stoaliment.consommateur.produit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Produit;

import java.util.Observable;
import java.util.Observer;

public class DetailProduitActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        Produit produit = (Produit) bundle.getSerializable("PRODUIT");

        ((TextView)findViewById(R.id.textViewLabelProduit)).setText(produit.getLabel());

        ((ImageView)findViewById(R.id.imageViewImageProduit)).setImageResource(R.drawable.cerises);
    }

    @Override
    public void update(Observable observable, Object o) {
        Produit produit = (Produit) o;

        System.out.println(produit.getLabel());

        ((TextView)findViewById(R.id.textViewLabelProduit)).setText(produit.getLabel());
        ((ImageView)findViewById(R.id.imageViewImageProduit)).setImageBitmap(produit.getImage());

        View load = findViewById(R.id.load);
        load.setVisibility(View.GONE);
    }
}
