package com.ihm.stoaliment.model;

import java.io.Serializable;
import java.util.List;

public class Consommateur implements Serializable {

    private String nom;
    private List<String> producteursSuivis;
    private String id;

    public Consommateur(String nom, List<String> producteursSuivis){
        this.nom = nom;
        this.producteursSuivis = producteursSuivis;
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

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
