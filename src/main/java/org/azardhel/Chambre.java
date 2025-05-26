package org.azardhel;

import java.util.UUID;

public class Chambre {
    private final UUID id;
    private final TypeDeChambre type;
    private  double prixParNuit;
    private boolean estDisponible;

    public Chambre(TypeDeChambre type)
    {
        this.id = UUID.randomUUID();
        this.type = type;
    }

    public boolean Disponible()
    {
        return false;
    }

    public String getNomDeChambre()
    {
        return type.getLibelle();
    }

    public double getPrix()
    {
        return this.type.getPrixParNuit();
    }
}
