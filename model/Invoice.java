package model;

import enums.PaymentMethod;
import java.time.LocalDate;
import java.util.ArrayList;

// Links this invoice to the reservation it belongs to
public class Invoice {

    private Reservation reservation;
    private double totalAmount;
    private double paidAmount;

    private boolean paid;
    private LocalDate createdAt;

    private PaymentMethod paymentMethod;
    // Stores all payments made
    private ArrayList<String> paymentHistory;

    public Invoice(Reservation reservation, double totalAmount) {

        this.reservation = reservation;
        this.totalAmount = totalAmount;

        this.paidAmount = 0;
        this.paid = false;

        this.createdAt = LocalDate.now();

        this.paymentHistory = new ArrayList<>();
    }

    // Handles payment process
    public void pay(double amount, PaymentMethod method) {

        if (amount <= 0) {
            System.out.println("Invalid payment amount");
            return;
        }

        if (paid) {
            System.out.println("Invoice already fully paid");
            return;
        }

        this.paymentMethod = method;

        paidAmount += amount;

        paymentHistory.add(method + " -> " + amount);

        if (paidAmount >= totalAmount) {
            paidAmount = totalAmount;
            paid = true;
            System.out.println("Invoice fully paid using " + method);
        } else {
            System.out.println("Partial payment done using " + method);
            System.out.println("Remaining: " + (totalAmount - paidAmount));
        }
    }

    //Overload the pay method
    public void pay(double amount) {
        pay(amount, PaymentMethod.CASH);
    }

    //    Displays full invoice details for the guest or
    //     receptionist (Receipt)
    public void printInvoice() {

        System.out.println("\n========== INVOICE ==========");

        System.out.println("Guest: " + reservation.getGuest().getUsername());
        System.out.println("Room: " + reservation.getRoom().getRoomNumber());

        System.out.println("Check-in: " + reservation.getCheckIn());
        System.out.println("Check-out: " + reservation.getCheckOut());

        System.out.println("Created At: " + createdAt);

        System.out.println("Total: " + totalAmount);
        System.out.println("Paid: " + paidAmount);

        System.out.println("Status: " + (paid ? "PAID" : "PENDING"));

        System.out.println("Payment Method: " +
                (paymentMethod == null ? "NOT SET" : paymentMethod));

        System.out.println("\nHistory:");
        for (String log : paymentHistory) {
            System.out.println("- " + log);
        }

        System.out.println("=============================\n");
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Reservation getReservation() {
        return reservation;
    }
}