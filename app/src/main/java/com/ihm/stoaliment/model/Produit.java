package com.ihm.stoaliment.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

public class Produit implements Serializable {

    private String label;
    private String typeProduit;
    private int heureDebut;
    private int heureFin;
    private int quantite;
    private int prix;
    private String imageUrl;

    public Produit(){}

    public Produit(String label, String typeProduit, int heureDebut, int heureFin, String imageUrl, int quantite, int prix) {
        this.label = label;
        this.typeProduit = typeProduit;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
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

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrix() {
        return prix;
    }
    public void setPrix(int prix) {
        this.prix = prix;
    }

}
