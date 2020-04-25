package com.ihm.stoaliment.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

public class Producteur extends Observable implements Serializable {

    private String id;
    private String nom;
    private String cp;
    private String ville;
    private List<Produit> produit;
    private String imageUrl;
    private Bitmap image;
    public static Producteur curProducteur;


    public Producteur(){}

    public Producteur(String id,String nom, String cp, String ville, List<Produit> produit, String imageUrl, Bitmap image){
        this.id = id;
        this.nom = nom;
        this.cp = cp;
        this.ville = ville;
        this.produit = produit;
        this.imageUrl = imageUrl;
        this.image = image;

        curProducteur.cp = cp;
        curProducteur.id = id;
        curProducteur.image = image;
        curProducteur.imageUrl = imageUrl;
        curProducteur.nom = nom;
        curProducteur.ville = ville;
        curProducteur.produit = produit;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCp() {
        return cp;
    }
    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public List<Produit> getProduit() {
        return produit;
    }
    public void setProduit(List<Produit> produit) {
        this.produit = produit;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
