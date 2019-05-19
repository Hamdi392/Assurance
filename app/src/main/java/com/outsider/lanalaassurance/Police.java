package com.outsider.lanalaassurance;

public class Police {

    private String NumeroPolice;
    private String NomProduit;
    private String date_du;
    private String date_au;
    private String EtatPolice;
    private Beneficiaire[] BeneficiairesCasVie;
    private Beneficiaire[] BeneficiairesCasDeces;

    public Police() {
    }

    public Police(String numeroPolice, String nomProduit, String date_du, String date_au, String etatPolice) {
        NumeroPolice = numeroPolice;
        NomProduit = nomProduit;
        this.date_du = date_du;
        this.date_au = date_au;
        EtatPolice = etatPolice;
    }

    public Beneficiaire[] getBeneficiairesCasVie() {
        return BeneficiairesCasVie;
    }

    public void setBeneficiairesCasVie(Beneficiaire[] beneficiairesCasVie) {
        BeneficiairesCasVie = beneficiairesCasVie;
    }

    public Beneficiaire[] getBeneficiairesCasDeces() {
        return BeneficiairesCasDeces;
    }

    public void setBeneficiairesCasDeces(Beneficiaire[] beneficiairesCasDeces) {
        BeneficiairesCasDeces = beneficiairesCasDeces;
    }

    public String getNumeroPolice() {
        return NumeroPolice;
    }

    public void setNumeroPolice(String numeroPolice) {
        NumeroPolice = numeroPolice;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public void setNomProduit(String nomProduit) {
        NomProduit = nomProduit;
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

    public String getEtatPolice() {
        return EtatPolice;
    }

    public void setEtatPolice(String etatPolice) {
        EtatPolice = etatPolice;
    }
}
