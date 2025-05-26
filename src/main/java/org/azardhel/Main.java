package org.azardhel;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Client mathieu = new Client("Smith","John","Smith.joe@gmail.com");
        Chambre chambre = new Chambre(TypeDeChambre.DOUBLE);

        Reservation maResa = new Reservation(mathieu,chambre,LocalDate.of(2025, 4, 22),LocalDate.of(2025, 4, 27));

        maResa.getAllInfos();
    }
}