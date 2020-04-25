package com.ihm.stoaliment.consommateur.producteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Producteur;

import java.util.Observable;
import java.util.Observer;

public class DetailProducteurActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_producteur);

        Intent intent = this.getIntent();

        String id = intent.getStringExtra("PRODUCTEUR");

        DetailProducteurControlleur detailProducteurControlleur = new DetailProducteurControlleur(this, id);
        detailProducteurControlleur.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {

        Producteur producteur = (Producteur) arg;

        ((TextView)findViewById(R.id.textViewNomProducteur)).setText(producteur.getNom());
        ((ImageView)findViewById(R.id.imageViewImageProducteur)).setImageBitmap(producteur.getImage());

        View load = findViewById(R.id.load);
        load.setVisibility(View.GONE);
    }
}
