package org.azardhel;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {

        Client mathieu = new Client("Smith","Mathieu","Smith.joe@gmail.com");
        Client Raphael = new Client("Mognlijzf","Raphael","Raph@gmail.com");

        Chambre chambre = new Chambre(TypeDeChambre.DOUBLE);
        Chambre chambre2 = new Chambre(TypeDeChambre.SUITE_SENIOR);

        ReservationService reservationService = new ReservationService();

        Reservation reservation = new Reservation(mathieu,chambre,LocalDate.now(),LocalDate.now().plusDays(3));
        Reservation reservation2 = new Reservation(Raphael,chambre,LocalDate.now().plusDays(4),LocalDate.now().plusDays(8));
        reservationService.makeReservation(reservation);
        reservationService.makeReservation(reservation2);



        reservationService.isRoomsAvailable(reservation);
        reservationService.isRoomsAvailable(reservation2);

        reservationService.showAllReservations();
    }
}