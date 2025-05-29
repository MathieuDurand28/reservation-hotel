package org.azardhel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChambreDAO {
    public void saveChambre(Chambre chambre) {
        String sql = "INSERT INTO chambre (id, type, libelle, prix) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, chambre.getId());
            stmt.setString(2, chambre.getType().name());
            stmt.setString(3, chambre.getType().getLibelle());
            stmt.setDouble(4, chambre.getPrix());


            stmt.executeUpdate();

            System.out.println("✅ Chambre enregistrée en base !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean chambreExiste(TypeDeChambre type) {
        String sql = "SELECT COUNT(*) FROM chambre WHERE type = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Chambre> getAllChambres() {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambre";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                TypeDeChambre type = TypeDeChambre.valueOf(rs.getString("type"));
                double prix = rs.getDouble("prix");
                chambres.add(new Chambre(id, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }

}
