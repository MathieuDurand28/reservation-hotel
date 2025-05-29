package org.azardhel;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.UUID;

public class ReservationDetaillee {
    private UUID id;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private String nomClient;
    private String prenomClient;
    private String emailClient;
    private String typeChambre;
    private double prixParNuit;

    public ReservationDetaillee(UUID id, LocalDate dateArrivee, LocalDate dateDepart,
                                String nomClient, String prenomClient, String emailClient,
                                String typeChambre, double prixParNuit) {
        this.id = id;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.emailClient = emailClient;
        this.typeChambre = typeChambre;
        this.prixParNuit = prixParNuit;
    }



    @Override
    public String toString() {
        long nbJours = ChronoUnit.DAYS.between(dateArrivee, dateDepart);
        double total = nbJours * prixParNuit;

        return nomClient + " " + prenomClient + " (" + emailClient + ") a réservé une " +
        typeChambre + " à " + prixParNuit + " €/nuit, du " +
                dateArrivee + " au " + dateDepart + " (" + nbJours + " nuits) — Total : " +
                String.format("%.2f", total) + " €";
    }
}