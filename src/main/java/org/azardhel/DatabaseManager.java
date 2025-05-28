package org.azardhel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:h2:./data/reservationDB"; // fichier local
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS client (
                    id UUID PRIMARY KEY,
                    nom VARCHAR(255),
                    prenom VARCHAR(255),
                    email VARCHAR(255)
                );
                """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS chambre (
                    id UUID PRIMARY KEY,
                    type VARCHAR(100),
                    prix DOUBLE
                );
                """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reservation (
                    id UUID PRIMARY KEY,
                    client_id UUID,
                    chambre_id UUID,
                    date_arrivee DATE,
                    date_depart DATE,
                    FOREIGN KEY (client_id) REFERENCES client(id),
                    FOREIGN KEY (chambre_id) REFERENCES chambre(id)
                );
                """);

            System.out.println("✅ Base de données initialisée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}