/*TO-DO :
- balance methods





/////////////////////////////////
ZAKER :

 - instanceof





 */



import database.HotelDatabase;
import enums.Gender;
import exceptions.InvalidInputException;
import exceptions.InvalidPriceException;
import exceptions.InvalidReservationException;
import model.*;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    // Global variables / objects
    static LocalDate today = LocalDate.now(); // have a global day objects that stores the details of (today)


    // helper functions

    // function that ensures an integer is input
    public static int readIntBetween(Scanner scanner, int min, int max) throws InvalidInputException {
        int value;

        try {
            value = scanner.nextInt();
            scanner.nextLine(); // to clean the input buffer from the \n (newline)
        } catch (InputMismatchException e) {
            scanner.nextLine(); // to clean the input buffer from the \n (newline)
            throw new InvalidInputException("Input must be an integer");
        }

        if (value < min || value > max) {
            throw new InvalidInputException("Input must be between " + min + " and " + max); // here is the reprompt if value is invalid
        }

        return value;
    }


    public static Guest guestAuth(Scanner scanner) {

        while (true) {

            System.out.println("\n=== GUEST ACCESS ===");
            System.out.println("1: Login");
            System.out.println("2: Register");
            System.out.println("0: Back");

            int choice;

            try {
                choice = readIntBetween(scanner, 0, 2);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // REGISTER
            if (choice == 2) {

                System.out.print("Username: ");
                String username = scanner.nextLine();

                String password;
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (password.length() < 6) {
                        System.out.println("Password must be at least 6 characters");
                        continue;
                    }
                    break;
                }

                System.out.print("Address: ");
                String address = scanner.nextLine();

                Gender gender;
                while (true) {
                    System.out.print("1: Male .. 2: Female\nGender: ");
                    try {
                        int genderId = readIntBetween(scanner, 1, 2);
                        gender = (genderId == 1) ? Gender.MALE : Gender.FEMALE;
                        break;
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                }

                Guest g = new Guest(
                        username,
                        password,
                        java.time.LocalDate.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth()),
                        1000,
                        address,
                        gender
                );

                try {
                    g.register(username, password);
                    System.out.println("Registered successfully");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // LOGIN (FIXED)
            else if (choice == 1) {

                System.out.print("Username: ");
                String username = scanner.nextLine();

                String password;
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (password.length() < 6) {
                        System.out.println("Password must be at least 6 characters");
                        continue;
                    }
                    break;
                }

                Guest found = null;

                for (Guest g : HotelDatabase.guests) {
                    if (g.getUsername().equals(username)) {
                        found = g;
                        break;
                    }
                }

                if (found == null) {
                    System.out.println("Invalid credentials");
                    continue;
                }

                try {
                    found.login(username, password);
                    System.out.println("Login success");
                    return found;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if (choice == 0) {
                return null;
            }
        }
    }


    public static Receptionist receptionistAuth(Scanner scanner) { // method that makes sure that receptionist is authentic (returns a Receptionist type)

        while (true) {

            System.out.println("\n=== RECEPTIONIST ACCESS ==="); // only for better shape
            System.out.println("1: Login");
            System.out.println("0: Back");

            int choice;

            try {
                choice = readIntBetween(scanner, 0, 1);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage()); // if any error occurs, this throws an exception
                continue; // loops until user enters a valid input
            }

            if (choice == 1) {

                System.out.print("Username: "); // nextline
                String username = scanner.nextLine();

                String password;
                while (true) { // this loop validates the password to be >= 6 characters
                    System.out.print("Password: "); // prompts for password
                    password = scanner.nextLine();

                    if (password.length() < 6) {
                        System.out.println("Password must be at least 6 characters");
                        continue;
                    }
                    break;
                }

                for (Staff s : HotelDatabase.staffMembers) { // loops in all staff members in database
                    if (s instanceof Receptionist) { // looks if s is an instance of Receptionist
                        Receptionist receptionist = (Receptionist) s;

                        if (receptionist.getUsername().equals(username) && receptionist.getPassword().equals(password)) {
                            System.out.println("Login success");
                            return receptionist;
                        }
                    }
                }

                System.out.println("Invalid credentials");
            }

            else if (choice == 0) {
                return null;
            }
        }
    }


    public static Admin adminAuth(Scanner scanner) { // method that makes sure that Admin is authentic (returns a Admin type)

        while (true) {

            System.out.println("\n=== ADMIN ACCESS ==="); // only for better shape
            System.out.println("1: Login");
            System.out.println("0: Back");

            int choice;

            try {
                choice = readIntBetween(scanner, 0, 1);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage()); // if any error occurs, this throws an exception
                continue;
            }

            if (choice == 1) {

                System.out.print("Username: "); // CHANGED: FIXED next()/nextLine bug
                String username = scanner.nextLine();

                String password;
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (password.length() < 6) {
                        System.out.println("Password must be at least 6 characters");
                        continue;
                    }
                    break;
                }

                for (Staff s : HotelDatabase.staffMembers) {
                    if (s instanceof Admin) {
                        Admin admin = (Admin) s;

                        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                            System.out.println("Login success");
                            return admin;
                        }
                    }
                }

                System.out.println("Invalid credentials");
            }

            else if (choice == 0) {
                return null;
            }
        }
    }


    // Panels
    public static void guestPanel(Scanner scanner, Guest guest) {

        while (true) {

            System.out.println("\n=== GUEST PANEL ===");
            System.out.println("1: View available rooms");
            System.out.println("2: Make reservation");
            System.out.println("3: View my reservations");
            System.out.println("4: Cancel reservation");
            System.out.println("0: Logout");

            int choice;

            try {
                choice = readIntBetween(scanner, 0, 4);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // ======================
            // VIEW AVAILABLE ROOMS (USING isAvailable)
            // ======================
            if (choice == 1) {

                try {

                    System.out.print("Enter check-in day: ");
                    int inDay = readIntBetween(scanner, 1, 31);

                    System.out.print("Enter check-in month: ");
                    int inMonth = readIntBetween(scanner, 1, 12);

                    System.out.print("Enter check-in year: ");
                    int inYear = readIntBetween(scanner, 2024, 2100);

                    System.out.print("Enter check-out day: ");
                    int outDay = readIntBetween(scanner, 1, 31);

                    System.out.print("Enter check-out month: ");
                    int outMonth = readIntBetween(scanner, 1, 12);

                    System.out.print("Enter check-out year: ");
                    int outYear = readIntBetween(scanner, 2024, 2100);

                    LocalDate checkIn = LocalDate.of(inYear, inMonth, inDay);
                    LocalDate checkOut = LocalDate.of(outYear, outMonth, outDay);

                    if (!checkOut.isAfter(checkIn)) {
                        System.out.println("Invalid date range");
                        continue;
                    }

                    boolean found = false;

                    for (Room r : HotelDatabase.rooms) {

                        if (r.isAvailable(checkIn, checkOut)) { // USING YOUR METHOD

                            System.out.println("Room: " + r.getRoomNumber());
                            System.out.println("Type: " + r.getType().getName());
                            System.out.println("Price per night: " + r.getType().getPricePerNight());
                            System.out.println("----------------");

                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("No rooms available for these dates");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // ======================
            // MAKE RESERVATION (USING Guest + Reservation LOGIC)
            // ======================
            else if (choice == 2) {

                try {

                    System.out.print("Room number: ");
                    int roomNumber = readIntBetween(scanner, 1, 9999);

                    Room selected = null;

                    for (Room r : HotelDatabase.rooms) {
                        if (r.getRoomNumber() == roomNumber) {
                            selected = r;
                            break;
                        }
                    }

                    if (selected == null) {
                        System.out.println("Room not found");
                        continue;
                    }

                    System.out.print("Enter check-in day: ");
                    int inDay = readIntBetween(scanner, 1, 31);

                    System.out.print("Enter check-in month: ");
                    int inMonth = readIntBetween(scanner, 1, 12);

                    System.out.print("Enter check-in year: ");
                    int inYear = readIntBetween(scanner, 2026, 2100);

                    System.out.print("Enter check-out day: ");
                    int outDay = readIntBetween(scanner, 1, 31);

                    System.out.print("Enter check-out month: ");
                    int outMonth = readIntBetween(scanner, 1, 12);

                    System.out.print("Enter check-out year: ");
                    int outYear = readIntBetween(scanner, 2026, 2100);

                    LocalDate checkIn = LocalDate.of(inYear, inMonth, inDay);
                    LocalDate checkOut = LocalDate.of(outYear, outMonth, outDay);

                    // USE model validation too
                    Reservation res = guest.makeReservation(selected, checkIn, checkOut); // USING YOUR METHOD

                    double total = res.calculateTotal(); // USING YOUR METHOD

                    System.out.println("Reservation created successfully");
                    System.out.println("Status: " + res.getStatus());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // ======================
            // VIEW RESERVATIONS (USING guest.viewReservations)
            // ======================
            else if (choice == 3) {

                if (guest.viewReservations().isEmpty()) {
                    System.out.println("You did not make any reservations");
                } else {

                    for (Reservation r : guest.viewReservations()) {

                        System.out.println("Room: " + r.getRoom().getRoomNumber());
                        System.out.println("Status: " + r.getStatus());
                        System.out.println("Check-in: " + r.getCheckIn());
                        System.out.println("Check-out: " + r.getCheckOut());

                        try {
                            System.out.println("Total: " + r.calculateTotal()); // USING YOUR METHOD
                        } catch (Exception e) {
                            System.out.println("Error calculating total");
                        }

                        System.out.println("----------------");
                    }
                }
            }

            // ======================
            // CANCEL RESERVATION (CHOICE-BASED)
            // ======================
            else if (choice == 4) {

                try {

                    if (guest.viewReservations().isEmpty()) {
                        System.out.println("No reservations to cancel");
                        continue;
                    }

                    // show reservations with index
                    System.out.println("\nYour Reservations:");

                    for (int i = 0; i < guest.viewReservations().size(); i++) {

                        Reservation r = guest.viewReservations().get(i);

                        System.out.println((i + 1) + ": Room " + r.getRoom().getRoomNumber());
                        System.out.println("   Status: " + r.getStatus());
                        System.out.println("   Check-in: " + r.getCheckIn());
                        System.out.println("   Check-out: " + r.getCheckOut());
                        System.out.println("----------------");
                    }

                    System.out.print("Choose reservation number to cancel: ");
                    int index = readIntBetween(scanner, 1, guest.viewReservations().size());

                    Reservation selected = guest.viewReservations().get(index - 1);

                    guest.cancelReservation(selected); // your validation method
                    selected.cancel(); // state change

                    System.out.println("Reservation cancelled");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // ======================
            // LOGOUT
            // ======================
            else if (choice == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) throws InvalidPriceException, InvalidReservationException {

        Scanner scanner = new Scanner(System.in); // scanner object creation
        HotelDatabase.initializeData(); // dummy data is created

        System.out.println("\nWelcome To Hotel Reservation System"); // just for a better shape

        while (true) {

            int role;
            while (true) {
                System.out.println("\nChoose your role:");
                System.out.println("\n1: Guest");
                System.out.println("\n2: Receptionist");
                System.out.println("\n3: Admin");
                System.out.println("\n0: Exit");

                try {
                    role = readIntBetween(scanner, 0, 3);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (role == 1) {

                Guest guest = guestAuth(scanner);
                if (guest != null) {
                    guestPanel(scanner, guest);
                }
            }

            else if (role == 2) {

                Receptionist receptionist = receptionistAuth(scanner);
            }

            else if (role == 3) {

                Admin admin = adminAuth(scanner);
            }

            else {
                System.out.println("Exiting... Thank You!");
                break;
            }
        }
    }
}