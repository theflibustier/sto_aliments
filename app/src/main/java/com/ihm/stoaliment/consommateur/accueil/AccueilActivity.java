package com.ihm.stoaliment.consommateur.accueil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Producteur;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AccueilActivity extends AppCompatActivity implements Observer {

    private ProducteurListAdapter producteurListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);

        AccueilControlleur accueilControlleur = new AccueilControlleur(this);
        accueilControlleur.addObserver(this);


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

        View load = findViewById(R.id.load);
        load.setVisibility(View.GONE);
    }
}
