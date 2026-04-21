import database.HotelDatabase;
import exceptions.InvalidInputException;
import exceptions.InvalidPriceException;
import exceptions.InvalidReservationException;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    // helper functions

    // function that ensures an integer is input
    public static int readInt(Scanner scanner) throws InvalidInputException {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // clear invalid input
            throw new InvalidInputException("Input must be an integer");
        }
    }


    public static void main(String[] args) throws InvalidPriceException, InvalidReservationException {

    Scanner scanner = new Scanner(System.in); // scanner object creation
    HotelDatabase.initializeData(); // dummy data is created

        System.out.println("\nWelcome To Hotel Reservation System");

         while(true){

             int role;
             while (true){
                 System.out.println("\nChoose your role:");
                 System.out.println("\n1: Guest");
                 System.out.println("\n2: Receptionist");
                 System.out.println("\n3: Admin");
                 System.out.println("\n0: Exit");


                 try {
                     role = readInt(scanner);

                     if (role >= 0 && role <= 3) {
                         break;
                     }

                     System.out.println("Invalid option. Choose 0-3.");

                 } catch (InvalidInputException e) {
                     System.out.println(e.getMessage());
                 }
             }

             if (role == 1) {

                 System.out.println("Guest part...");
             }

             else if (role == 2) {
                 System.out.println("Receptionist part...");
             }

             else if (role == 3) {
                 System.out.println("Admin part...");
             }

             else {
                 System.out.println("Exiting... Thank You!");
                 break;
             }

         }

    }
}