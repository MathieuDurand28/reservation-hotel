package org.azardhel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    public boolean isRoomsAvailable(Reservation newRes) {
        String sql = """
            SELECT COUNT(*) FROM reservation
            WHERE chambre_id = ?
              AND date_arrivee < ?
              AND date_depart > ?
        """;
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, newRes.getChambre().getId());
            stmt.setDate(2, Date.valueOf(newRes.getDateDepart()));
            stmt.setDate(3, Date.valueOf(newRes.getDateArrivee()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReservationDetaillee> getAllReservations() {
        List<ReservationDetaillee> liste = new ArrayList<>();
        String sql = """
        SELECT r.id, r.date_arrivee, r.date_depart,
               c.nom, c.prenom, c.email,
               ch.type,ch.libelle , ch.prix
        FROM reservation r
        JOIN client c ON r.client_id = c.id
        JOIN chambre ch ON r.chambre_id = ch.id
        """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReservationDetaillee rd = new ReservationDetaillee(
                        (UUID) rs.getObject("id"),
                        rs.getDate("date_arrivee").toLocalDate(),
                        rs.getDate("date_depart").toLocalDate(),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("libelle"),
                        rs.getDouble("prix")
                );
                liste.add(rd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    public void cancelReservation(UUID reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, reservationId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Réservation annulée !");
            } else {
                System.out.println("❌ Aucune réservation trouvée avec cet ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getAllReservationsRaw() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = """
        SELECT r.id, r.date_arrivee, r.date_depart,
               c.id AS client_id, c.nom, c.prenom, c.email,
               ch.id AS chambre_id, ch.type, ch.prix
        FROM reservation r
        JOIN client c ON r.client_id = c.id
        JOIN chambre ch ON r.chambre_id = ch.id
    """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        (UUID) rs.getObject("client_id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );

                TypeDeChambre type = TypeDeChambre.valueOf(rs.getString("type"));
                Chambre chambre = new Chambre(
                        (UUID) rs.getObject("chambre_id"),
                        type
                );

                reservations.add(new Reservation(
                        (UUID) rs.getObject("id"),
                        client,
                        chambre,
                        rs.getDate("date_arrivee").toLocalDate(),
                        rs.getDate("date_depart").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
    /*
    public void getAllReservations() {
        String sql = "SELECT * FROM reservation r JOIN client c ON r.client_id = c.id";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ReservationAvecClient rac = new ReservationAvecClient(
                        (UUID) rs.getObject("id"),
                        rs.getDate("date_arrivee").toLocalDate(),
                        rs.getDate("date_depart").toLocalDate(),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );

                System.out.println(rac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     */
}
