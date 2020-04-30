package com.ihm.stoaliment.model;

import android.graphics.Bitmap;


import com.google.firebase.firestore.GeoPoint;

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
    private GeoPoint location;
    private List<String> listeAbonnes;

    public Producteur(){}

    public Producteur(String id,String nom, String cp, String ville, List<Produit> produit, String imageUrl, Bitmap image, GeoPoint location){
        this.id = id;
        this.nom = nom;
        this.cp = cp;
        this.ville = ville;
        this.produit = produit;
        this.imageUrl = imageUrl;
        this.image = image;
        this.location =location;
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

    public GeoPoint getLocation() {
        return location;
    }
    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public List<String> getListeAbonnes() {
        return listeAbonnes;
    }
}
