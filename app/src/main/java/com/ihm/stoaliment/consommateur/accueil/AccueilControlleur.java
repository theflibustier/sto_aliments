package com.ihm.stoaliment.consommateur.accueil;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class AccueilControlleur {

    public AccueilControlleur(){

    }

    public List<Produit> chargeProduit(){

        Produit p1 = new Produit("Tomate de saison", new Producteur("Didier le producteur", "31 300", "Toulouse"), R.drawable.tomates);
        Produit p2 = new Produit("Super cerises à bon prix", new Producteur("Tout a 2€", "06 600", "Antibes"), R.drawable.cerises);
        Produit p3 = new Produit("Les bon légume du terroire", new Producteur("Frank et les légume", "75 000", "Paris"), R.drawable.legumes);
        Produit p4 = new Produit("Le meilleur miel de tout le Gers", new Producteur("Gersois et fier de l'être","32 000", "Auch"), R.drawable.miel);

        List<Produit> produits = new ArrayList<>();
        produits.add(p1);
        produits.add(p2);
        produits.add(p3);
        produits.add(p4);


        return produits;
    }
}
