package com.ihm.stoaliment.model;

import android.media.Image;

import java.util.Date;

public class Produit {

    private String label;
    private Producteur producteur;
    private Date debut;
    private Date fin;
    private int image;

    public Produit(String label, Producteur producteur, int image) {
        this.label = label;
        this.producteur = producteur;
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public Producteur getProducteur() {
        return producteur;
    }

    public Date getDebut() {
        return debut;
    }

    public Date getFin() {
        return fin;
    }

    public int getImage() {
        return image;
    }
}
