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

        for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
            Reservation res = HotelDatabase.reservations.get(i);
            //getRoomNumber, getStatus, getRoom pay are methods in the other classes that will be implemented when the classes are finished

            if (res.getRoom().getRoomNumber() == roomNumber) {

                if (res.getStatus() == ReservationStatus.CONFIRMED) {
                    res.setStatus(ReservationStatus.COMPLETED);
                    return;
                } else {
                    System.out.println("Reservation not confirmed");
                    return;
                }
            }
        }

        //System.out.println("Reservation not found");
    }

    // Method to check out a guest
    public void checkOutGuest(int roomNumber) throws InvalidDateException, InvalidReservationException {

        for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
            Reservation res = HotelDatabase.reservations.get(i);
            //getRoomNumber, getStatus, getRoomType, getPrice, setPaymentMethod, pay are methods in the other classes that will be implemented when the classes are finished
            if (res.getRoom().getRoomNumber() == roomNumber) {

                if (res.getStatus() == ReservationStatus.CONFIRMED) {

                    double price = res.calculateTotal();

                    Invoice invoice = new Invoice(res);
                    invoice.generateInvoice();

                    invoice.setPaymentMethod(PaymentMethod.CASH);
                    invoice.pay(price);

                    res.complete();

                    System.out.println("Guest checked out and paid");

                    return;
                } else {
                    System.out.println("Guest not checked in yet");
                    return;
                }
            }
        }

        //System.out.println("Reservation not found");
    }

}