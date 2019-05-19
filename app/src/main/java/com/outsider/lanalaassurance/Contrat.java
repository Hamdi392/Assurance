package com.outsider.lanalaassurance;

public class Contrat {

    private String numero;
    private String nom;
    private String date;
    private String benifVie;
    private String benifEssais;
    private String etat;
    private String produit;

    public Contrat() {
    }

    public Contrat(String numero, String nom, String date, String benifVie, String benifEssais, String etat, String produit) {
        this.numero = numero;
        this.nom = nom;
        this.date = date;
        this.benifVie = benifVie;
        this.benifEssais = benifEssais;
        this.etat = etat;
        this.produit = produit;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBenifVie() {
        return benifVie;
    }

    public void setBenifVie(String benifVie) {
        this.benifVie = benifVie;
    }

    public String getBenifEssais() {
        return benifEssais;
    }

    public void setBenifEssais(String benifEssais) {
        this.benifEssais = benifEssais;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
