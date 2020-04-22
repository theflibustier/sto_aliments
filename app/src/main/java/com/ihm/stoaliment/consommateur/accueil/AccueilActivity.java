package com.ihm.stoaliment.consommateur.accueil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.ProducteurModel;
import com.ihm.stoaliment.model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AccueilActivity extends AppCompatActivity implements Observer {

    private ProducteurModel producteurModel = new ProducteurModel();
    private ProducteurListAdapter producteurListAdapter;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        dialog = ProgressDialog.show(this, "Chargement", "Un instant...", true);

        producteurModel.addObserver(this);
        AccueilControlleur accueilControlleur = new AccueilControlleur(this, producteurModel);


        List<Producteur> producteurs = new ArrayList<>();
        producteurListAdapter = new ProducteurListAdapter(this, producteurs);
        ListView listView = findViewById(R.id.listViewProducteur);
        listView.setAdapter(producteurListAdapter);
        listView.setOnItemClickListener(accueilControlleur);
    }

    @Override
    public void update(Observable o, Object arg) {

        producteurListAdapter.add((Producteur) arg);
        producteurListAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }
}
