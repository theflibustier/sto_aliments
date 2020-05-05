package com.ihm.stoaliment.model;

import com.ihm.stoaliment.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AbonneList extends Observable {

    public void loadAbonnes(){

        Consommateur consommateur1 = new Consommateur("Jean", "adresse", "ville", "cp");
        Consommateur consommateur2 = new Consommateur("Charle", "adresse", "ville", "cp");
        Consommateur consommateur3 = new Consommateur("Benoit", "adresse", "ville", "cp");
        Consommateur consommateur4 = new Consommateur("Bernard", "adresse", "ville", "cp");

        List<Consommateur> consommateurs = new ArrayList<>();
        consommateurs.add(consommateur1);
        consommateurs.add(consommateur2);
        consommateurs.add(consommateur3);
        consommateurs.add(consommateur4);

        setChanged();
        notifyObservers(consommateurs);
    }
}
