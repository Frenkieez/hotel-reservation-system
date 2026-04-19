package cli;
//
//import database.HotelDatabase;
//import enums.Gender;
//import exceptions.InvalidInputException;
//import exceptions.InvalidResgistrationException;
//import model.Guest;
//
//import java.time.LocalDate;
//import java.util.Scanner;
//
//import static cli.Main.readInt;
//
//
//
//public class GuestAuth {
//    Scanner scanner = new Scanner(System.in);
//
//    static Guest guestAuth(Scanner sc) throws InvalidResgistrationException {
//
//        while (true) {
//
//            System.out.println("\n=== GUEST ACCESS ===");
//            System.out.println("1. Login");
//            System.out.println("2. Register");
//            System.out.println("0. Back");
//
//            int choice;
//
//            try {
//                choice = readInt(sc);
//            } catch (InvalidInputException e) {
//                System.out.println(e.getMessage());
//                continue;
//            }
//
//            // REGISTER
//            if (choice == 2) {
//
//                System.out.println("Username:");
//                String u = sc.next();
//
//                System.out.println("Password:");
//                String p = sc.next();
//
//                Guest g = new Guest(
//                        u, p,
//                        LocalDate.of(2000,1,1),
//                        1000,
//                        "N/A",
//                        Gender.MALE
//                );
//
//                g.register(u, p);
//                HotelDatabase.guests.add(g);
//
//                System.out.println("Registered successfully");
//            }
//
//
//            // LOGIN
//            else if (choice == 1) {
//
//                System.out.println("Username:");
//                String u = sc.next();
//
//                System.out.println("Password:");
//                String p = sc.next();
//
//                for (Guest g : HotelDatabase.guests) {
//                    try {
//                        g.login(u, p);
//                        System.out.println("Login success");
//                        return g;
//                    } catch (Exception ignored) {}
//                }
//
//                System.out.println("Invalid credentials");
//            }
//
//            else if (choice == 0) {
//                return null;
//            }
//
//            else {
//                System.out.println("Invalid option");
//            }
//        }
//    }
//}
