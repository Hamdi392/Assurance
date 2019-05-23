package com.outsider.lanalaassurance;

public class Impayee {

    private String numContart;
    private String dateEffet;
    private String dateDebut;
    private String dateFin;
    private String monatnt;


    public Impayee() {
    }

    public Impayee(String numContart, String dateEffet, String dateDebut, String dateFin, String monatnt) {
        this.numContart = numContart;
        this.dateEffet = dateEffet;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.monatnt = monatnt;
    }

    public String getNumContart() {
        return numContart;
    }

    public void setNumContart(String numContart) {
        this.numContart = numContart;
    }

    public String getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(String dateEffet) {
        this.dateEffet = dateEffet;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getMonatnt() {
        return monatnt;
    }

    public void setMonatnt(String monatnt) {
        this.monatnt = monatnt;
    }
}
