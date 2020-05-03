package com.ihm.stoaliment.producteur.abonneList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.controleur.ConsommateurControlleur;
import com.ihm.stoaliment.model.Consommateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AfficheAbonneActivity extends AppCompatActivity implements Observer {

    private List<Consommateur> consommateurs = new ArrayList<>();
    private AbonneListAdapter abonnesListAdapter;
    private ConsommateurControlleur consommateurControlleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_abonne);

        consommateurControlleur = new ConsommateurControlleur(this);
        consommateurControlleur.addObserver(this);
        consommateurControlleur.loadAbonnes(Authentification.producteur.getId());

        abonnesListAdapter = new AbonneListAdapter(this, consommateurs);
        ListView listView = findViewById(R.id.listViewAbonnes);
        listView.setAdapter(abonnesListAdapter);
        listView.setOnItemClickListener(consommateurControlleur);
    }

    @Override
    public void update(Observable o, Object arg) {
        consommateurs = (List<Consommateur>) arg;
        abonnesListAdapter.addAll(consommateurs);
        abonnesListAdapter.notifyDataSetChanged();

    }
}
