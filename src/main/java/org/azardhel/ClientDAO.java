package org.azardhel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
