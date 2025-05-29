package org.azardhel;

import java.util.UUID;

public class Chambre {
    private final UUID id;
    private final TypeDeChambre type;
    private boolean isDisponible;


    public Chambre(UUID id, TypeDeChambre type) {
        this.id = id;
        this.type = type;
    }
    public Chambre(TypeDeChambre type) {
        this.id = UUID.randomUUID();
        this.type = type;
    }

    public boolean estDisponible()
    {
        return isDisponible;
    }

    public UUID getId()
    {
        return id;
    }

    public TypeDeChambre getType()
    {
        return type;
    }

    public void setDisponible(boolean dispo) {
        this.isDisponible = dispo;
    }

    public String getNomDeChambre()
    {
        return type.getLibelle();
    }

    public double getPrix()
    {
        return this.type.getPrixParNuit();
    }

    @Override
    public String toString() {
        return "Chambre " + id + " (" + getNomDeChambre() + " - " + getPrix() + " €)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chambre chambre = (Chambre) o;
        return id.equals(chambre.id); // compare les UUID
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
