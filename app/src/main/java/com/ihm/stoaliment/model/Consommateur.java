package com.ihm.stoaliment.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Consommateur implements Serializable {

    private String nom;
    private String adresse;
    private String ville;
    private String cp;
    private List<String> producteursSuivis;
    private String id;
    private GeoPoint locationConsommateur;

    public Consommateur(String nom, String adresse, String ville, String cp) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.cp = cp;
        producteursSuivis = new ArrayList<>();
    }

    public Consommateur(){}

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<String> getProducteursSuivis() {
        return producteursSuivis;
    }
    public void setProducteursSuivis(List<String> producteursSuivis) {
        this.producteursSuivis = producteursSuivis;
    }

    @Exclude
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCp() {
        return cp;
    }
    public void setCp(String cp) {
        this.cp = cp;
    }

    public GeoPoint getLocationConsommateur() {
        return locationConsommateur;
    }
    public void setLocationConsommateur(GeoPoint locationConsommateur) {
        this.locationConsommateur = locationConsommateur;
    }
}
