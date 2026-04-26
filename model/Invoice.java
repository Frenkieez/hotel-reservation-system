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

    private LocalDate paidAt;

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
    public boolean pay(double amount, PaymentMethod method, String email, String password) {

        // SAFE GUARD: prevent paying already completed invoices
        if (paid) {
            return false;
        }

        // basic validation
        if (amount <= 0) {
            return false;
        }

        double remaining = totalAmount - paidAmount;

        if (amount > remaining) {
            return false;
        }

        // ONLINE PAYMENT VALIDATION
        if (method == PaymentMethod.ONLINE) {

            if (email == null || password == null) {
                return false;
            }

            if (email.length() < 8 || password.length() < 6) {
                return false;
            }

            boolean hasAt = false;

            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    hasAt = true;
                    break;
                }
            }

            if (!hasAt) {
                return false;
            }
        }

        // CASH validation using guest balance
        if (method == PaymentMethod.CASH) {

            if (reservation.getGuest().getBalance() < amount) {
                return false;
            }

            reservation.getGuest().updateBalance(
                    reservation.getGuest().getBalance() - amount
            );
        }

        // APPLY PAYMENT
        this.paymentMethod = method;

        paidAmount += amount;

        paymentHistory.add(method + " -> " + amount);

        // FINAL STATE UPDATE
        if (paidAmount >= totalAmount) {

            paidAmount = totalAmount;
            paid = true;
            paidAt = LocalDate.now();
        }

        return true;
    }

    public void printInvoiceDetails() {

        System.out.println("========== INVOICE ==========");

        System.out.println("Room: " + reservation.getRoom().getRoomNumber());

        System.out.println("Total: " + totalAmount);

        System.out.println("Paid: " + paidAmount);

        System.out.println("Remaining: " + (totalAmount - paidAmount));

        System.out.println("Status: " + (paid ? "PAID" : "PENDING"));

        if (paidAt != null) {
            System.out.println("Paid At: " + paidAt);
        } else {
            System.out.println("Paid At: NOT COMPLETED");
        }

        System.out.println("Payment Method: " + (paymentMethod != null ? paymentMethod : "N/A"));

        System.out.println("=============================");
    }

    //Overload the pay method
    public boolean pay(double amount) {
        return pay(amount, PaymentMethod.CASH, null, null);
    }

    public boolean pay(double amount, PaymentMethod method) {
        return pay(amount, method, null, null);
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