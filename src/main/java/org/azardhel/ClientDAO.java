package org.azardhel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientDAO {
    public void saveClient(Client client) {
        String sql = "INSERT INTO client (id, nom, prenom, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, client.getId());        // UUID
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getPrenom());
            stmt.setString(4, client.getEmail());

            stmt.executeUpdate();

            System.out.println("✅ Client enregistré en base !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                clients.add(new Client(id, nom, prenom, email)); // ✅ ici on garde le vrai UUID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
