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
import model.Admin;
import model.Guest;
import model.Receptionist;
import model.Staff;

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

                System.out.print("Password: ");
                String password = scanner.nextLine();

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