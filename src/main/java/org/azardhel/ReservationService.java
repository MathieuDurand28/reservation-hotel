package org.azardhel;
import java.util.ArrayList;
import java.util.List;


public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();

    public Reservation makeReservation(Reservation reservation) {
        if (isRoomsAvailable(reservation) && !isReservationExist(reservation)) {
            reservations.add(reservation);
            return reservation;
        } else {
            System.out.println("Chambre indisponible pour les dates demandées.");
            return null;
        }
    }

    public void cancelReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public boolean isRoomsAvailable(Reservation newRes) {
        for (Reservation existing : reservations) {
            if (existing.getChambre().equals(newRes.getChambre())) {
                boolean chevauche =
                        !(newRes.getDateArrivee().isAfter(existing.getDateDepart()) ||
                                newRes.getDateDepart().isBefore(existing.getDateArrivee()));
                if (chevauche) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isReservationExist(Reservation newRes) {
        for (Reservation existing : reservations) {
            if (existing.getChambre().equals(newRes.getChambre())) {
                return true;
            }
        }
        return false;
    };

    //show reservation details
    public void showReservation(Reservation reservation)
    {
        System.out.println(reservation);
    }

    public void showAllReservations()
    {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
            System.out.println("---------------------------------------");
        }
    }

}
