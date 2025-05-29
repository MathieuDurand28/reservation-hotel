package org.azardhel;

import java.time.LocalDate;
import java.util.UUID;

public class ReservationAvecClient {
    private UUID id;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private String nomClient;
    private String prenomClient;
    private String emailClient;

    public ReservationAvecClient(UUID id, LocalDate dateArrivee, LocalDate dateDepart,
                                 String nomClient, String prenomClient, String emailClient) {
        this.id = id;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.emailClient = emailClient;
    }

    @Override
    public String toString() {
        return nomClient + " " + prenomClient + " (" + emailClient + ") a réservé du " +
                dateArrivee + " au " + dateDepart;
    }
}
