package com.ihm.stoaliment.producteur.abonneList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.AbonneList;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AfficheAbonneActivity extends AppCompatActivity implements Observer {

    private List<Consommateur> consommateurs;
    private AbonneList abonneList = new AbonneList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_abonne);

        abonneList.addObserver(this);
        AbonneControlleur abonneControlleur = new AbonneControlleur(this, abonneList);

        AbonneListAdapter abonnesListAdapter = new AbonneListAdapter(this, consommateurs);
        ListView listView = findViewById(R.id.listViewAbonnes);
        listView.setAdapter(abonnesListAdapter);
        listView.setOnItemClickListener(abonneControlleur);
    }

    @Override
    public void update(Observable o, Object arg) {

        consommateurs = (List<Consommateur>) arg;
    }
}
