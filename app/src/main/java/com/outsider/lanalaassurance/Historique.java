package com.outsider.lanalaassurance;

public class Historique {

    private String NumeroQuittance;
    private String Date_du;
    private String Date_au;
    private String Montant_payee;

    public Historique() {
    }

    public Historique(String numeroQuittance, String date_du, String date_au, String montant_payee) {
        NumeroQuittance = numeroQuittance;
        Date_du = date_du;
        Date_au = date_au;
        Montant_payee = montant_payee;
    }

    public String getMontant_payee() {
        return Montant_payee;
    }

    public void setMontant_payee(String montant_payee) {
        Montant_payee = montant_payee;
    }

    public String getNumeroQuittance() {
        return NumeroQuittance;
    }

    public void setNumeroQuittance(String numeroQuittance) {
        NumeroQuittance = numeroQuittance;
    }

    public String getDate_du() {
        return Date_du;
    }

    public void setDate_du(String date_du) {
        Date_du = date_du;
    }

    public String getDate_au() {
        return Date_au;
    }

    public void setDate_au(String date_au) {
        Date_au = date_au;
    }
}
