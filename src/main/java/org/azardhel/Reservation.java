package org.azardhel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        if (dateDepart.isBefore(dateArrivee)) {
            throw new IllegalArgumentException("La date de départ ne peut pas être avant la date d’arrivée.");
        }
    }

    public Reservation(UUID id, Client client, Chambre chambre, LocalDate dateArrivee, LocalDate dateDepart) {
        this.id = id;
        this.client = client;
        this.chambre = chambre;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;

        if (dateDepart.isBefore(dateArrivee)) {
            throw new IllegalArgumentException("La date de départ ne peut pas être avant la date d’arrivée.");
        }
    }

    public double calcTotalPrice()
    {
        long daysBetween = ChronoUnit.DAYS.between(dateArrivee,dateDepart);
        return chambre.getPrix() * daysBetween;
    }

    public UUID getId()
        {
        return id;
        }

    public UUID getClientId()
    {
        return client.getId();
    }

    public Client getClient()
    {
        return client;
    }

    public UUID getChambreId()
    {
        return chambre.getId();
    }

    public Chambre getChambre()
    {
        return chambre;
    }

    public LocalDate getDateDepart()
    {
        return dateDepart;
    }

    public LocalDate getDateArrivee()
    {
        return dateArrivee;
    }

    @Override
    public String toString() {
        return "****************\n" +
                client.getNom() + " " + client.getPrenom() +
                " a réservé la " + chambre.getNomDeChambre() +
                " du " + dateArrivee + " au " + dateDepart + "\n" +
                "pour un prix total de " + String.format("%.2f", calcTotalPrice()) + " € (" +
                String.format("%.2f",chambre.getPrix()) + " € par jour)\n" +
                "****************";
    }
}
