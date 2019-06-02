package com.outsider.lanalaassurance;

public class Impayee {

    private String numero_quittance;
    private String date_effet_quittance;
    private String date_du;
    private String date_au;
    private String montant_quittance;


    public Impayee() {
    }

    public Impayee(String numero_quittance, String date_effet_quittance, String date_du, String date_au, String montant_quittance) {
        this.numero_quittance = numero_quittance;
        this.date_effet_quittance = date_effet_quittance;
        this.date_du = date_du;
        this.date_au = date_au;
        this.montant_quittance = montant_quittance;
    }

    public String getNumero_quittance() {
        return numero_quittance;
    }

    public void setNumero_quittance(String numero_quittance) {
        this.numero_quittance = numero_quittance;
    }

    public String getDate_effet_quittance() {
        return date_effet_quittance;
    }

    public void setDate_effet_quittance(String date_effet_quittance) {
        this.date_effet_quittance = date_effet_quittance;
    }

    public String getDate_du() {
        return date_du;
    }

    public void setDate_du(String date_du) {
        this.date_du = date_du;
    }

    public String getDate_au() {
        return date_au;
    }

    public void setDate_au(String date_au) {
        this.date_au = date_au;
    }

    public String getMontant_quittance() {
        return montant_quittance;
    }

    public void setMontant_quittance(String montant_quittance) {
        this.montant_quittance = montant_quittance;
    }
}
