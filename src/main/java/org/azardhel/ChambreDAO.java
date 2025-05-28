package org.azardhel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChambreDAO {
    public void saveChambre(Chambre chambre) {
        String sql = "INSERT INTO chambre (id, type, prix) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, chambre.getId());        // UUID
            stmt.setString(2, chambre.getType().toString());
            stmt.setDouble(3, chambre.getPrix());


            stmt.executeUpdate();

            System.out.println("✅ Chambre enregistrée en base !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
