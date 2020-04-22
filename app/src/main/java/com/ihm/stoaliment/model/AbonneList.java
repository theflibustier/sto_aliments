package com.ihm.stoaliment.model;

import com.ihm.stoaliment.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AbonneList extends Observable {

    public void loadAbonnes(){

        Abonne abonne1 = new Abonne("Jean","83000","Toulon", R.drawable.abonne);
        Abonne abonne2 = new Abonne("Charle","06100","Nice", R.drawable.abonne);
        Abonne abonne3 = new Abonne("Benoit","83200","Toulon", R.drawable.abonne);
        Abonne abonne4 = new Abonne("Bernard","06500","Antibes", R.drawable.abonne);

        List<Abonne> abonnes = new ArrayList<>();
        abonnes.add(abonne1);
        abonnes.add(abonne2);
        abonnes.add(abonne3);
        abonnes.add(abonne4);

        setChanged();
        notifyObservers(abonnes);
    }
}
