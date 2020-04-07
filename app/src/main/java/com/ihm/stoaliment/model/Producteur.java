package com.ihm.stoaliment.model;

import java.io.Serializable;

public class Producteur implements Serializable {

    public String name;
    public String codePostal;
    public String ville;

    public Producteur(String name, String codePostal, String ville){
        this.name = name;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public String getName() {
        return name;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }
}
