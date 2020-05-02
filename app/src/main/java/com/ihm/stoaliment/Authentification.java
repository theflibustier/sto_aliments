package com.ihm.stoaliment;

import android.content.SharedPreferences;

import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;

import java.io.Serializable;

public class Authentification implements Serializable {

    public static final String PRODUCTEUR_TYPE = "producteur";
    public static final String CONSOMMATEUR_TYPE = "consommateur";
    public static final String GSON_LABEL = "authentification";


    public static Producteur producteur;
    public static Consommateur consommateur;
    public static String userType;
    public static Authentification authentification;
    public static SharedPreferences preferences;

    private String ref;
    private String identifiant;
    private String type;

    public Authentification(){}

    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getIdentifiant() {
        return identifiant;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
