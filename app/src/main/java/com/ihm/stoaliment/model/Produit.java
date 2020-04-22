package com.ihm.stoaliment.model;

import java.io.Serializable;

public class Produit implements Serializable {

    private String label;
    private int quantite;
    private int prix;
    private String imageUrl;

    public Produit(){}

    public Produit(String label, String imageUrl, int quantite, int prix) {
        this.label = label;
        this.imageUrl = imageUrl;
        this.quantite = quantite;
        this.prix = prix;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }


    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrix() {
        return prix;
    }
    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
