// TEMP VERSION OF INVOICE AND PAYMENT CLASS

package model;

import database.HotelDatabase;
import enums.PaymentMethod;
import exceptions.InvalidDateException;
import exceptions.InvalidReservationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private Reservation reservation;
    private double totalAmount;
    private LocalDate paymentDate;

    private List<Payment> payments = new ArrayList<>();

    private boolean generated = false;

    // Constructor
    public Invoice(Reservation reservation, double v) throws InvalidDateException, InvalidReservationException {
        this.reservation = reservation;
        this.totalAmount = reservation.calculateTotal();
    }

    // Generate invoice (connects to system)
    public void generateInvoice() {
        if (generated) return;

        HotelDatabase.invoices.add(this);
        generated = true;

        System.out.println("Invoice generated for reservation");
        System.out.println("Room: " + reservation.getRoom().getRoomNumber());
        System.out.println("Guest: " + reservation.getGuest().getUsername());
        System.out.println("Total: " + totalAmount);
    }

    // Pay full amount (default)
    public void pay(double amount) {
        addPayment(amount, PaymentMethod.CASH);
    }

    // Pay with method
    public void addPayment(double amount, PaymentMethod method) {

        if (amount <= 0) {
            System.out.println("Invalid payment amount");
            return;
        }

        payments.add(new Payment(amount, method));

        double paid = getPaidAmount();

        if (paid >= totalAmount) {
            paymentDate = LocalDate.now();
            System.out.println("Payment completed successfully");

            reservation.complete(); // important system link
        } else {
            System.out.println("Partial payment done");
        }
    }

    // Total paid
    public double getPaidAmount() {
        double sum = 0;
        for (Payment p : payments) {
            sum += p.getAmount();
        }
        return sum;
    }

    // Remaining balance
    public double getRemaining() {
        return totalAmount - getPaidAmount();
    }

    // Status check
    public boolean isPaid() {
        return getPaidAmount() >= totalAmount;
    }

    // Print invoice
    public void printReceipt() {
        System.out.println("\n===== INVOICE =====");
        System.out.println("Guest: " + reservation.getGuest().getUsername());
        System.out.println("Room: " + reservation.getRoom().getRoomNumber());
        System.out.println("Total: " + totalAmount);
        System.out.println("Paid: " + getPaidAmount());
        System.out.println("Remaining: " + getRemaining());

        System.out.println("\nPayments:");
        for (Payment p : payments) {
            System.out.println(p.getMethod() + " -> " + p.getAmount());
        }

        System.out.println("\nStatus: " + (isPaid() ? "PAID" : "PENDING"));
    }

    // Getters
    public Reservation getReservation() {
        return reservation;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
    }
}

class Payment {
    private double amount;
    private PaymentMethod method;

    public Payment(double amount, PaymentMethod method) {
        this.amount = amount;
        this.method = method;
    }

    public double getAmount() { return amount; }
    public PaymentMethod getMethod() { return method; }
}