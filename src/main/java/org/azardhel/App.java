package org.azardhel;
import java.sql.SQLException;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws SQLException {
        org.h2.tools.Server.main("-web", "-webAllowOthers", "-webPort", "8082");
        DatabaseManager.initDatabase();


        Client mathieu = new Client("Smith","Mathieu","Smith.joe@gmail.com");
        new ClientDAO().saveClient(mathieu);
        Client raphael = new Client("Mognlijzf","Raphael","Raph@gmail.com");
        new ClientDAO().saveClient(raphael);

        Chambre chambre = new Chambre(TypeDeChambre.DOUBLE);
        new ChambreDAO().saveChambre(chambre);
        Chambre chambre2 = new Chambre(TypeDeChambre.SUITE_SENIOR);
        new ChambreDAO().saveChambre(chambre2);

        ReservationService reservationService = new ReservationService();

        Reservation reservation = new Reservation(mathieu,chambre,LocalDate.now(),LocalDate.now().plusDays(3));
        new ReservationDAO().saveReservation(reservation);
        Reservation reservation2 = new Reservation(raphael,chambre,LocalDate.now().plusDays(4),LocalDate.now().plusDays(8));
        new ReservationDAO().saveReservation(reservation2);

        Reservation reservation3 = new Reservation(raphael,chambre2,LocalDate.now().plusDays(4),LocalDate.now().plusDays(8));
        new ReservationDAO().saveReservation(reservation3);
        Reservation reservation4 = new Reservation(mathieu,chambre2,LocalDate.now().plusDays(10),LocalDate.now().plusDays(14));
        new ReservationDAO().saveReservation(reservation4);


        reservationService.makeReservation(reservation);
        reservationService.makeReservation(reservation2);
        reservationService.makeReservation(reservation3);
        reservationService.makeReservation(reservation4);



        reservationService.isRoomsAvailable(reservation);
        reservationService.isRoomsAvailable(reservation2);
        reservationService.isRoomsAvailable(reservation3);
        reservationService.isRoomsAvailable(reservation4);

        reservationService.showAllReservations();


    }
}