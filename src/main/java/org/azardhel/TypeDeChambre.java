package org.azardhel;

public enum TypeDeChambre {
    SIMPLE("Chambre simple", 49.99),
    DOUBLE("Chambre double", 129.40),
    SUITE_JUNIOR("Suite junior", 499.99),
    SUITE_SENIOR("Suite sénior", 2599.23);

    private final String libelle;
    private final double prixParNuit;

    TypeDeChambre(String libelle, double prixParNuit) {
        this.libelle = libelle;
        this.prixParNuit = prixParNuit;
    }

    public String getLibelle() {
        return libelle;
    }

    public double getPrixParNuit() {
        return prixParNuit;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
