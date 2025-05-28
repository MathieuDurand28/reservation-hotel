package org.azardhel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ReservationDAO {
    public void saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (id, client_id, chambre_id, date_arrivee, date_depart) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, reservation.getId());
            stmt.setObject(2, reservation.getClientId());
            stmt.setObject(3, reservation.getChambreId());
            stmt.setObject(4, reservation.getDateArrivee());
            stmt.setObject(5, reservation.getDateDepart());

            stmt.executeUpdate();

            System.out.println("✅ Réservation enregistrée en base !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
