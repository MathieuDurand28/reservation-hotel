package org.azardhel;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation {
    private final UUID id;
    private Client client;
    private Chambre chambre;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;

    public Reservation(Client client, Chambre chambre, LocalDate dateArrivee, LocalDate dateDepart)
    {
        this.id = UUID.randomUUID();
        this.client = client;
        this.chambre = chambre;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
    }

    public Double calculerPrixTotal()
    {
        return 0.0;
    }

    public void getAllInfos()
    {
        System.out.println(client.getNom()+" "+client.getPrenom()+" a réservé la "+chambre.getNomDeChambre()+" au prix de "+chambre.getPrix()+" par nuit.");
    }
}
