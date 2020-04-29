package com.ihm.stoaliment.model;

import com.ihm.stoaliment.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AbonneList extends Observable {

    public void loadAbonnes(){

        Consommateur consommateur1 = new Consommateur("Jean",new ArrayList<String>());
        Consommateur consommateur2 = new Consommateur("Charle",new ArrayList<String>());
        Consommateur consommateur3 = new Consommateur("Benoit",new ArrayList<String>());
        Consommateur consommateur4 = new Consommateur("Bernard",new ArrayList<String>());

        List<Consommateur> consommateurs = new ArrayList<>();
        consommateurs.add(consommateur1);
        consommateurs.add(consommateur2);
        consommateurs.add(consommateur3);
        consommateurs.add(consommateur4);

        setChanged();
        notifyObservers(consommateurs);
    }
}
