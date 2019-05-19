package com.outsider.lanalaassurance;

public class Beneficiaire {

    private String NomPrenom;

    public Beneficiaire(String nomPrenom) {
        NomPrenom = nomPrenom;
    }

    public Beneficiaire() {
    }

    public String getNomPrenom() {
        return NomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        NomPrenom = nomPrenom;
    }
}
