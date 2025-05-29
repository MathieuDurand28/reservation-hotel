package org.azardhel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {
    public static void main(String[] args) throws SQLException {
        org.h2.tools.Server.main("-web", "-webAllowOthers", "-webPort", "8082");
        DatabaseManager.initDatabase();

        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        while (choix != 6) {
            System.out.println("\n=== Menu Réservation Hôtel ===");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Ajouter une chambre");
            System.out.println("3. Réserver une chambre");
            System.out.println("4. Voir toutes les réservations");
            System.out.println("5. Annuler une réservation");
            System.out.println("6. Quitter");
            System.out.print("Votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine(); // vide la ligne

            switch (choix) {
                case 1 -> {
                    System.out.println("Nom du client : ");
                    String nom = scanner.nextLine();

                    System.out.println("Prénom du client : ");
                    String prenom = scanner.nextLine();

                    System.out.println("Email du client : ");
                    String email = scanner.nextLine();

                    Client client = new Client(nom, prenom, email);
                    new ClientDAO().saveClient(client);
                }
                case 2 -> {
                    System.out.println("Types de chambre disponibles :");
                    for (TypeDeChambre t : TypeDeChambre.values()) {
                        System.out.println("- " + t.name() + " → " + t.getLibelle());
                    }

                    System.out.print("Entrez le type : ");
                    String type = scanner.nextLine();

                    try {
                        TypeDeChambre typeChambre = TypeDeChambre.valueOf(type.toUpperCase());
                        ChambreDAO chambreDAO = new ChambreDAO();

                        if (chambreDAO.chambreExiste(typeChambre)) {
                            System.out.println("❌ Une chambre de ce type existe déjà !");
                        } else {
                            chambreDAO.saveChambre(new Chambre(typeChambre));
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Type de chambre incorrect !");
                    }
                }
                case 3 -> {
                    ClientDAO clientDAO = new ClientDAO();
                    ChambreDAO chambreDAO = new ChambreDAO();
                    ReservationDAO reservationDAO = new ReservationDAO();

                    // 1. Afficher clients
                    List<Client> clients = new ClientDAO().getAllClients();
                    if (clients.isEmpty()) {
                        System.out.println("❌ Aucun client disponible.");
                        break;
                    }

                    System.out.println("Clients disponibles :");
                    for (int i = 0; i < clients.size(); i++) {
                        System.out.println((i + 1) + ". " + clients.get(i).getPrenom() + " " + clients.get(i).getNom());
                    }

                    System.out.print("Choisissez un client (n°) : ");
                    int choixClient = Integer.parseInt(scanner.nextLine()) - 1;
                    Client client = clients.get(choixClient);

                    // 2. Afficher chambres
                    List<Chambre> chambres = chambreDAO.getAllChambres();
                    if (chambres.isEmpty()) {
                        System.out.println("❌ Aucune chambre disponible.");
                        break;
                    }

                    System.out.println("Chambres disponibles :");
                    for (int i = 0; i < chambres.size(); i++) {
                        System.out.println((i + 1) + ". " + chambres.get(i).getType() + " (" + chambres.get(i).getPrix() + " €/nuit)");
                    }

                    System.out.print("Choisissez une chambre (n°) : ");
                    int choixChambre = Integer.parseInt(scanner.nextLine()) - 1;
                    Chambre chambre = chambres.get(choixChambre);

                    // 3. Demander les dates
                    System.out.print("Date d'arrivée (aaaa-mm-jj) : ");
                    LocalDate arrivee = LocalDate.parse(scanner.nextLine());

                    System.out.print("Date de départ (aaaa-mm-jj) : ");
                    LocalDate depart = LocalDate.parse(scanner.nextLine());

                    // 4. Vérifier si la chambre est dispo
                    Reservation nouvelleReservation = new Reservation(client, chambre, arrivee, depart);

                    if (reservationDAO.isRoomsAvailable(nouvelleReservation)) {
                        reservationDAO.saveReservation(nouvelleReservation);
                        System.out.println("✅ Réservation enregistrée !");
                    } else {
                        System.out.println("❌ Chambre déjà réservée à ces dates.");
                    }
                }
                case 4 -> {
                    List<ReservationDetaillee> liste = new ReservationDAO().getAllReservations();
                    if (liste.isEmpty()) {
                        System.out.println("Aucune réservation pour le moment.");
                    }
                    liste.forEach(System.out::println);
                }
                case 5 -> {
                    ReservationDAO reservationDAO = new ReservationDAO();
                    List<Reservation> reservations = reservationDAO.getAllReservationsRaw();

                    if (reservations.isEmpty()) {
                        System.out.println("❌ Aucune réservation à annuler.");
                        break;
                    }

                    System.out.println("Réservations existantes :");
                    for (int i = 0; i < reservations.size(); i++) {
                        Reservation r = reservations.get(i);
                        System.out.println((i + 1) + ". " + r.getClient().getPrenom() + " " + r.getClient().getNom()
                                + " - " + r.getChambre().getType() + " du " + r.getDateArrivee() + " au " + r.getDateDepart());
                    }

                    System.out.print("Choisissez une réservation à annuler (n°) : ");
                    int choixAnnul = Integer.parseInt(scanner.nextLine()) - 1;

                    if (choixAnnul >= 0 && choixAnnul < reservations.size()) {
                        UUID resId = reservations.get(choixAnnul).getId();

                        System.out.print("❓ Confirmer la suppression ? (o/n) : ");
                        String confirmation = scanner.nextLine().trim().toLowerCase();

                        if (confirmation.equals("o") || confirmation.equals("oui")) {
                            reservationDAO.cancelReservation(resId);
                        } else {
                            System.out.println("❎ Suppression annulée.");
                        }
                    } else {
                        System.out.println("❌ Choix invalide.");
                    }
                }
                case 6 -> System.out.println("👋 À bientôt !");
                default -> System.out.println("❌ Choix invalide !");
            }
        }

/*
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

        List<ReservationDetaillee> reservations = new ReservationDAO().getAllReservations();
        reservations.forEach(System.out::println);
*/
    }


}