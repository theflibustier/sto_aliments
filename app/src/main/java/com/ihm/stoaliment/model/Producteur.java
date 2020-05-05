package com.ihm.stoaliment.model;

import android.graphics.Bitmap;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
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
    private String adresse;

    public Producteur(){}

    public Producteur(String nom, String cp, String ville, String adresse, GeoPoint geoPoint){
        this.nom = nom;
        this.cp = cp;
        this.ville = ville;
        this.adresse = adresse;
        produit = new ArrayList<>();
        imageUrl = "producteur/default.jpg";
        listeAbonnes = new ArrayList<>();
        this.location = geoPoint;
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

    @Exclude
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Exclude
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
    public void setListeAbonnes(List<String> listeAbonnes) {
        this.listeAbonnes = listeAbonnes;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
