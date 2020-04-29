package com.ihm.stoaliment;

import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;

public class Authentification {

    public static final String PRODUCTEUR_TYPE = "producteur";
    public static final String CONSOMMATEUR_TYPE = "consommateur";


    public static Producteur producteur;
    public static Consommateur consommateur;
    public static String userType;

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
