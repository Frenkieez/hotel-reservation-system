import database.HotelDatabase;
import enums.Gender;
import enums.PaymentMethod;
import enums.ReservationStatus;
import exceptions.InvalidDateException;
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

    // method that ensures an integer is input
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


    public static Guest guestAuth(Scanner scanner) { // makes sure guest is an authentic one

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

            // registering path
            if (choice == 2) {

                System.out.print("Username: ");
                String username = scanner.nextLine();

                String password;
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (password.length() < 6) { // validation for password
                        System.out.println("Password must be at least 6 characters");
                        continue;
                    }
                    break;
                }

                System.out.print("Address: ");
                String address = scanner.nextLine();

                Gender gender;
                while (true) { // selecting whether user is a male or female using numbers 1,2
                    System.out.print("1: Male .. 2: Female\nGender: ");
                    try {
                        int genderId = readIntBetween(scanner, 1, 2);
                        gender = (genderId == 1) ? Gender.MALE : Gender.FEMALE;
                        break;
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                }

                Guest g = new Guest( // creating a new guest object
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

            // Logging in path
            else if (choice == 1) {

                System.out.print("Username: ");
                String username = scanner.nextLine();

                String password;
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (password.length() < 6) { // making sure entered password is at least 6 chars
                        System.out.println("Password must be at least 6 characters");
                        continue;
                    }
                    break;
                }

                Guest found = null;

                for (Guest g : HotelDatabase.getGuests()) {
                    if (g.getUsername().equals(username)) {
                        found = g;
                        break;
                    }
                }

                if (found == null) {
                    System.out.println("Invalid credentials"); // if guest is not found inside the database
                    continue;
                }

                try {
                    found.login(username, password); // logging in when credentials are valid
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

                System.out.print("Username: ");
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

                for (Receptionist receptionist : HotelDatabase.getReceptionists()) {

                    if (receptionist.getUsername().equals(username) &&
                            receptionist.getPassword().equals(password)) {

                        System.out.println("Login success");
                        return receptionist;
                    }
                }

                System.out.println("Invalid credentials");
            }

            else if (choice == 0) {
                return null;
            }
        }
    }


    public static Admin adminAuth(Scanner scanner) { // method that makes sure that Admin is authentic (returns Admin type)

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

                for (Admin admin : HotelDatabase.getAdmins()) {

                    if (admin.getUsername().equals(username) &&
                            admin.getPassword().equals(password)) {

                        System.out.println("Login success");
                        return admin;
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
    public static void guestPanel(Scanner scanner, Guest guest) throws InvalidInputException { // guest panel which is only executed after guestAuth()

        while (true) {

            System.out.println("\n=== GUEST PANEL ===");
            System.out.println("1: View available rooms");
            System.out.println("2: Make reservation");
            System.out.println("3: View my reservations");
            System.out.println("4: Cancel reservation");
            System.out.println("5: View invoices");
            System.out.println("6: Pay invoice");
            System.out.println("0: Logout");

            int choice;

            try {
                choice = readIntBetween(scanner, 0, 6);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // View available rooms path
            if (choice == 1) {

                try {
                    // taking checking in and out details
                    System.out.print("Enter check-in day: ");
                    int inDay = readIntBetween(scanner, 1, 31);

                    System.out.print("Enter check-in month: ");
                    int inMonth = readIntBetween(scanner, 1, 12);

                    System.out.print("Enter check-in year: ");
                    int inYear = readIntBetween(scanner, today.getYear(), today.getYear()+100);

                    System.out.print("Enter check-out day: ");
                    int outDay = readIntBetween(scanner, 1, 31);

                    System.out.print("Enter check-out month: ");
                    int outMonth = readIntBetween(scanner, 1, 12);

                    System.out.print("Enter check-out year: ");
                    int outYear = readIntBetween(scanner, today.getYear(), today.getYear()+100);

                    LocalDate checkIn = LocalDate.of(inYear, inMonth, inDay);
                    LocalDate checkOut = LocalDate.of(outYear, outMonth, outDay);

                    if (!checkOut.isAfter(checkIn)) { // making sure we have valid date range
                        System.out.println("Invalid date range");
                        continue;
                    }

                    boolean found = false;

                    for (Room r : HotelDatabase.getRooms()) {

                        if (r.isAvailable(checkIn, checkOut)) { // when room is available its details are printed

                            System.out.println("Room: " + r.getRoomNumber());
                            System.out.println("Type: " + r.getType().getName());
                            System.out.println("Price per night: " + r.getType().getPricePerNight());
                            System.out.println("----------------");

                            found = true;
                        }
                    }

                    if (!found) { // when not found
                        System.out.println("No rooms available for these dates");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Making a reservation path
            else if (choice == 2) {

                try {

                    System.out.print("Room number: ");
                    int roomNumber = readIntBetween(scanner, 1, 9999); // an example of rooms range from 1 -> 9999

                    Room selected = null;

                    for (Room r : HotelDatabase.getRooms()) {
                        if (r.getRoomNumber() == roomNumber) {
                            selected = r;
                            break;
                        }
                    }

                    if (selected == null) { // if room is not found
                        System.out.println("Room not found");
                        continue;
                    }
                    // taking room details from user
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

                    // making a new reservation using the given data
                    Reservation res = guest.makeReservation(selected, checkIn, checkOut);

                    double total = res.calculateTotal();

                    System.out.println("Reservation created successfully");
                    System.out.println("Status: " + res.getStatus());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Viewing reservations path
            else if (choice == 3) {

                if (guest.viewReservations().isEmpty()) { // when not created any reservations yet
                    System.out.println("You did not make any reservations");
                } else {

                    for (Reservation r : guest.viewReservations()) { // printing room details

                        System.out.println("Room: " + r.getRoom().getRoomNumber());
                        System.out.println("Status: " + r.getStatus());
                        System.out.println("Check-in: " + r.getCheckIn());
                        System.out.println("Check-out: " + r.getCheckOut());

                        try {
                            System.out.println("Total: " + r.calculateTotal()); // printing total
                        } catch (Exception e) {
                            System.out.println("Error calculating total");
                        }

                        System.out.println("----------------");
                    }
                }
            }

            // Cancelling a reservation path
            else if (choice == 4) {

                try {

                    if (guest.viewReservations().isEmpty()) { // when not created any reservations yet
                        System.out.println("No reservations to cancel");
                        continue;
                    }

                    // showing reservations with index
                    System.out.println("\nYour Reservations:");

                    for (int i = 0; i < guest.viewReservations().size(); i++) {

                        Reservation r = guest.viewReservations().get(i);

                        System.out.println((i + 1) + ": Room " + r.getRoom().getRoomNumber());
                        System.out.println("Status: " + r.getStatus());
                        System.out.println("Check-in: " + r.getCheckIn());
                        System.out.println("Check-out: " + r.getCheckOut());
                        System.out.println("----------------");
                    }
                    // user chooses number of reservation to be canceled
                    System.out.print("Choose reservation number to cancel: ");
                    int index = readIntBetween(scanner, 1, guest.viewReservations().size());

                    Reservation selected = guest.viewReservations().get(index - 1);

                    guest.cancelReservation(selected); // cancel the reservation
                    selected.cancel(); // change the state to canceled

                    System.out.println("Reservation cancelled");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Invoice viewing path
            else if (choice == 5) {

                if (guest.getInvoices().isEmpty()) { // when not having any invoices yet
                    System.out.println("No invoices found");
                    continue;
                }

                for (int i = 0; i < guest.getInvoices().size(); i++) {

                    Invoice inv = guest.getInvoices().get(i);
                    // printing details of invoice
                    System.out.println((i + 1) + ": Room " + inv.getReservation().getRoom().getRoomNumber());
                    System.out.println("   Total: " + inv.getTotalAmount());
                    System.out.println("   Paid: " + inv.getPaidAmount());
                    System.out.println("   Status: " + (inv.isPaid() ? "PAID" : "PENDING"));
                    System.out.println("----------------");
                }
            }

            // Invoice paying path
            else if (choice == 6) {

                if (guest.getInvoices().isEmpty()) { // when not having any invoices to pay yet
                    System.out.println("No invoices to pay");
                    continue;
                }

                // show invoices
                for (int i = 0; i < guest.getInvoices().size(); i++) {

                    Invoice inv = guest.getInvoices().get(i);

                    System.out.println((i + 1) + ": Room " +
                            inv.getReservation().getRoom().getRoomNumber() +
                            " | Remaining: " +
                            (inv.getTotalAmount() - inv.getPaidAmount()) +
                            " | Status: " +
                            (inv.isPaid() ? "PAID" : "PENDING")); // if isPaid prints PAID else PENDING
                }

                System.out.print("Choose invoice: "); // choosing invoice to pay using index
                int index = readIntBetween(scanner, 1, guest.getInvoices().size());

                Invoice selected = guest.getInvoices().get(index - 1);

                System.out.print("Enter amount to pay: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                System.out.println("Choose payment method:");
                System.out.println("1: CASH");
                System.out.println("2: CARD");
                System.out.println("3: ONLINE");

                int methodChoice = readIntBetween(scanner, 1, 3);

                PaymentMethod method;

                if (methodChoice == 1) {
                    method = PaymentMethod.CASH;
                } else if (methodChoice == 2) {
                    method = PaymentMethod.CARD;
                } else {
                    method = PaymentMethod.ONLINE;
                }

                selected.pay(amount, method);

                System.out.println("Payment processed successfully");
            }

            // Logout
            else if (choice == 0) {
                return;
            }
        }
    }

    // receptionist panel which is only executed after receptionistAuth()
    public static void receptionistPanel(Scanner scanner, Receptionist receptionist) throws InvalidInputException, InvalidDateException, InvalidReservationException {

        while (true) {

            System.out.println("\n=== RECEPTIONIST PANEL ===");
            System.out.println("1: View guests");
            System.out.println("2: View rooms");
            System.out.println("3: View reservations");
            System.out.println("4: Confirm reservation");
            System.out.println("5: Check-in guest");
            System.out.println("6: Check-out guest");
            System.out.println("0: Logout");

            int choice;

            try {
                choice = readIntBetween(scanner, 0, 6);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // View guests
            if (choice == 1) receptionist.viewGuest();

                // View rooms
            else if (choice == 2) receptionist.viewRoom();

                // View reservations
            else if (choice == 3) receptionist.viewBooking();

                // Confirming path
            else if (choice == 4) {

                for (int i = 0; i < HotelDatabase.getReservations().size(); i++) {
                    Reservation r = HotelDatabase.getReservations().get(i);
                    System.out.println((i + 1) + ": " + r.getRoom().getRoomNumber() + " " + r.getStatus());
                }

                int index;

                try {
                    index = readIntBetween(scanner, 1, HotelDatabase.getReservations().size());
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                Reservation r = HotelDatabase.getReservations().get(index - 1);

                try {
                    r.confirm();
                    System.out.println("Reservation confirmed successfully");
                    System.out.println("Room: " + r.getRoom().getRoomNumber());
                    System.out.println("Status: " + r.getStatus());
                    System.out.println("Check-in: " + r.getCheckIn());
                    System.out.println("Check-out: " + r.getCheckOut());
                } catch (InvalidDateException e) {
                    System.out.println("Cannot confirm reservation: " + e.getMessage());
                }
            }

            // Check-in path
            else if (choice == 5) {

                if (HotelDatabase.getReservations().isEmpty()) {
                    System.out.println("No reservations available");
                    continue;
                }

                System.out.println("Choose reservation to check-in:");

                for (int i = 0; i < HotelDatabase.getReservations().size(); i++) {
                    Reservation r = HotelDatabase.getReservations().get(i);

                    System.out.println((i + 1) + ": " +
                            r.getRoom().getRoomNumber() + " " +
                            r.getStatus());
                }

                int index;

                try {
                    index = readIntBetween(scanner, 1, HotelDatabase.getReservations().size());
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                Reservation r = HotelDatabase.getReservations().get(index - 1);

                if (r.getStatus() != ReservationStatus.CONFIRMED) {
                    System.out.println("Not allowed");
                    continue;
                }

                System.out.println("Checked-in successfully");
            }

            // Check-out path
            else if (choice == 6) {

                if (HotelDatabase.getReservations().isEmpty()) { // when not created any reservations yet
                    System.out.println("No reservations available");
                    continue;
                }

                System.out.println("Choose reservation to check-out:");

                for (int i = 0; i < HotelDatabase.getReservations().size(); i++) {

                    Reservation r = HotelDatabase.getReservations().get(i);
                    // printing checkout details
                    System.out.println((i + 1) + ": Room " + r.getRoom().getRoomNumber()
                            + " | Status: " + r.getStatus()
                            + " | Guest: " + r.getGuest().getUsername());
                }

                int index;

                try {
                    index = readIntBetween(scanner, 1, HotelDatabase.getReservations().size());
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                Reservation r = HotelDatabase.getReservations().get(index - 1);

                if (r.getStatus() != ReservationStatus.CONFIRMED) { // the reservation must be confirmed first in order to check-out
                    System.out.println("Cannot check-out (must be CONFIRMED first)");
                    continue;
                }

                double total = r.calculateTotal();

                // creating a new invoice
                Invoice invoice = new Invoice(r, total);

                HotelDatabase.getInvoices().add(invoice);

                // attaching this invoice to that guest
                r.getGuest().getInvoices().add(invoice);

                r.complete();

                System.out.println("Check-out completed");
                System.out.println("Invoice generated for amount: " + total); // tells the user how much to pay (of course this also will be printed in the invoice itself)
            }

            else if (choice == 0) return; // if zero -> return back
        }
    }

    // Admin panel
    public static void adminPanel(Scanner scanner, Admin admin) {  // Admin panel which is only executed after adminAuth()

        while (true) {

            System.out.println("\n=== ADMIN PANEL ===");
            System.out.println("1: View guests");
            System.out.println("2: View rooms");
            System.out.println("3: View reservations");
            System.out.println("4: Manage rooms");
            System.out.println("5: Manage amenities");
            System.out.println("6: Manage room types");
            System.out.println("0: Logout");

            int choice;

            try {
                choice = readIntBetween(scanner, 0, 6);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Viewing guests path
            if (choice == 1) {

                if (HotelDatabase.getGuests().isEmpty()) { // if there are no guests yet
                    System.out.println("No guests found");
                    continue;
                }

                for (Guest g : HotelDatabase.getGuests()) {
                    System.out.println("Username: " + g.getUsername());
                }
            }

            // Viewing rooms path
            else if (choice == 2) {
                admin.viewRoom();
            }

            // Viewing reservations path
            else if (choice == 3) {

                if (HotelDatabase.getReservations().isEmpty()) { // if no reservations yet
                    System.out.println("No reservations found");
                    continue;
                }

                for (Reservation r : HotelDatabase.getReservations()) {
                    // printing reservations details
                    System.out.println("Guest: " + r.getGuest().getUsername());
                    System.out.println("Room: " + r.getRoom().getRoomNumber());
                    System.out.println("Status: " + r.getStatus());
                    System.out.println("Check-in: " + r.getCheckIn());
                    System.out.println("Check-out: " + r.getCheckOut());
                    System.out.println("----------------");
                }
            }

            // Managing rooms path
            else if (choice == 4) {

                System.out.println("\n--- ROOM MANAGEMENT ---");
                System.out.println("1: Add room");
                System.out.println("2: Update room price");
                System.out.println("3: Delete room");

                int subChoice;

                try {
                    subChoice = readIntBetween(scanner, 1, 3);
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                // Adding a room
                if (subChoice == 1) {

                    try {
                        System.out.print("Room number: ");
                        int roomNumber = readIntBetween(scanner, 1, 9999);

                        System.out.println("Choose room type:");
                        for (int i = 0; i < HotelDatabase.getRoomTypes().size(); i++) {
                            System.out.println((i + 1) + ": " + HotelDatabase.getRoomTypes().get(i).getName());
                        }

                        int typeIndex = readIntBetween(scanner, 1, HotelDatabase.getRoomTypes().size());

                        RoomType type = HotelDatabase.getRoomTypes().get(typeIndex - 1);

                        Room newRoom = new Room(roomNumber, type);

                        admin.addRoom(newRoom); // room added

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                // Updating a room
                else if (subChoice == 2) {

                    try {
                        System.out.print("Room number: ");
                        int roomNumber = readIntBetween(scanner, 1, 9999);

                        System.out.print("New price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        admin.updateRoom(roomNumber, price); // room updated

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                // Deleting a room
                else if (subChoice == 3) {

                    try {
                        System.out.print("Room number: ");
                        int roomNumber = readIntBetween(scanner, 1, 9999);

                        admin.deleteRoom(roomNumber); // room deleted

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            // Managing amenities path
            else if (choice == 5) {

                System.out.println("\n--- AMENITY MANAGEMENT ---");
                System.out.println("1: Add amenity");
                System.out.println("2: View amenities");
                System.out.println("3: Update amenity");
                System.out.println("4: Delete amenity");

                int subChoice;

                try {
                    subChoice = readIntBetween(scanner, 1, 4);
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                // Adding amenity
                if (subChoice == 1) {
                    System.out.print("Amenity name: ");
                    String name = scanner.nextLine();

                    admin.addAmenity(new Amenity(name));
                }

                // Viewing amenity
                else if (subChoice == 2) {
                    admin.viewAmenity();
                }

                // Updating amenity
                else if (subChoice == 3) {
                    System.out.print("Old name: ");
                    String oldName = scanner.nextLine();

                    System.out.print("New name: ");
                    String newName = scanner.nextLine();

                    admin.updateAmenity(oldName, newName);
                }

                // Deleting amenity
                else if (subChoice == 4) {

                    System.out.print("Amenity name to delete: ");
                    String name = scanner.nextLine();

                    Amenity toDelete = null;

                    for (Amenity a : HotelDatabase.getAmenities()) {
                        if (a.getName().equalsIgnoreCase(name)) {
                            toDelete = a;
                            break;
                        }
                    }

                    if (toDelete != null) {
                        admin.deleteAmenity(toDelete);
                    } else {
                        System.out.println("Amenity not found");
                    }
                }
            }

            // Managing room types path
            else if (choice == 6) {

                System.out.println("\n--- ROOM TYPE MANAGEMENT ---");
                System.out.println("1: Add type");
                System.out.println("2: View types");
                System.out.println("3: Update type");
                System.out.println("4: Delete type");

                int subChoice;

                try {
                    subChoice = readIntBetween(scanner, 1, 4);
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                // Adding room type
                if (subChoice == 1) {

                    try {
                        System.out.print("Type name: ");
                        String name = scanner.nextLine();

                        System.out.print("Price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        admin.addRoomType(new RoomType(name, price));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                // Viewing room type
                else if (subChoice == 2) {
                    admin.viewRoomTypes();
                }

                // Updating room type
                else if (subChoice == 3) {

                    System.out.print("Old name: ");
                    String oldName = scanner.nextLine();

                    System.out.print("New name: ");
                    String newName = scanner.nextLine();

                    admin.updateRoomType(oldName, newName);
                }

                // Delete room type
                else if (subChoice == 4) {

                    System.out.print("Type name to delete: ");
                    String name = scanner.nextLine();

                    RoomType toDelete = null;

                    for (RoomType t : HotelDatabase.getRoomTypes()) {
                        if (t.getName().equalsIgnoreCase(name)) {
                            toDelete = t;
                            break;
                        }
                    }

                    if (toDelete != null) {
                        admin.deleteRoomType(toDelete);
                    } else {
                        System.out.println("Type not found");
                    }
                }
            }

            else if (choice == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) throws InvalidPriceException, InvalidReservationException, InvalidInputException, InvalidDateException {

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
                    guestPanel(scanner, guest); // call panel if authentic
                }
            }

            else if (role == 2) {

                Receptionist receptionist = receptionistAuth(scanner);
                if (receptionist != null) {
                    receptionistPanel(scanner, receptionist); // call panel if authentic
                }
            }

            else if (role == 3) {

                Admin admin = adminAuth(scanner);

                if (admin != null) {
                    adminPanel(scanner, admin); // call panel if authentic
                }
            }

            else {
                System.out.println("Exiting... Thank You!");
                break;
            }
        }
        scanner.close();
    }
}