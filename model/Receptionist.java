package model;
import database.HotelDatabase;
import enums.PaymentMethod;
import enums.ReservationStatus;
import enums.Role;
import exceptions.InvalidDateException;
import exceptions.InvalidReservationException;

// Receptionist class that extends Staff
public class Receptionist extends Staff {
    // Constructor for Receptionist
    public Receptionist(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours, Role.RECEPTIONIST);
    }
    // Override the performActions method to provide receptionist-specific functionality
    @Override
    public void performActions() {
        // code to perform receptionist-specific actions
        System.out.println("Performing receptionist actions...");
    }

    // Method to check in a guest
    public void checkInGuest(int roomNumber) {

        for (Reservation reservation : HotelDatabase.reservations) {

            if (reservation.getRoom().getRoomNumber() == roomNumber) {

                if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                    reservation.complete();
                    System.out.println("Guest checked in");
                } else {
                    System.out.println("Reservation not confirmed");
                }

                return;
            }
        }

        System.out.println("Reservation not found");
    }

    // Method to check out a guest
    public void checkOutGuest(int roomNumber) throws InvalidDateException, InvalidReservationException {

        for (Reservation reservation : HotelDatabase.reservations) {

            if (reservation.getRoom().getRoomNumber() == roomNumber) {

                if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
                    System.out.println("Guest not checked in yet");
                    return;
                }

                double price = reservation.calculateTotal();

                // create invoice (simple version)
                Invoice invoice = new Invoice(reservation, price);

                // mark reservation completed
                reservation.complete();

                // store invoice globally
                HotelDatabase.invoices.add(invoice);

                // IMPORTANT: also link to guest if you have it
                reservation.getGuest().getInvoices().add(invoice);

                System.out.println("Guest checked out successfully");
                System.out.println("Invoice created: " + price);

                return;
            }
        }

        System.out.println("Reservation not found");
    }

}