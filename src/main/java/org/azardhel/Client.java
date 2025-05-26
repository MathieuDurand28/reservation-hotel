package org.azardhel;

import java.util.UUID;

public class Client {
    private final UUID id;
    private final String nom;
    private final String prenom;
    private final String email;

    public Client(String nom, String prenom, String email)
    {
     this.id = UUID.randomUUID();
     this.nom = nom;
     this.prenom = prenom;
     this.email = email;
    }

    public String getNom()
    {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
