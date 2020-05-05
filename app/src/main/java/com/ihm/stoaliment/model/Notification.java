package com.ihm.stoaliment.model;

import java.util.List;

public class Notification {
    private String expediteur;
    private List<String> destinataires;
    private String message;
    private String id;

    Notification(){}
    public Notification(String expediteur, List<String> destinataires, String message){
        this.expediteur = expediteur;
        this.destinataires = destinataires;
        this.message = message;
    }

    public List<String> getDestinataires() {
        return destinataires;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }
}
