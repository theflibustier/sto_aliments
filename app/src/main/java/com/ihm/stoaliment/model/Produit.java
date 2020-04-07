package com.ihm.stoaliment.model;

import android.media.Image;

import java.io.Serializable;
import java.util.Date;

public class Produit implements Serializable {

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

    public void setLabel(String label) {
        this.label = label;
    }

    public void setProducteur(Producteur producteur) {
        this.producteur = producteur;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public void setImage(int image) {
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
