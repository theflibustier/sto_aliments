package com.ihm.stoaliment.model;

import java.io.Serializable;

public class Abonne implements Serializable {

    public String name;
    public String codePostal;
    public String ville;
    public int img;

    public Abonne(String name, String codePostal, String ville, int img){
        this.name = name;
        this.codePostal = codePostal;
        this.ville = ville;
        this.img = img;
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

    public int getImg() {
        return img;
    }
}
