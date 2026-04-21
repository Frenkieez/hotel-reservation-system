// Eslam version 1

package model;

import enums.PaymentMethod;
import interfaces.Payable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice implements Payable {

    private LocalDate paymentDate;
    private double totalAmount;
    private ArrayList<Payment> payments;

    // Constructor
    public Invoice(Reservation reservation, double totalAmount) {
        this.totalAmount = totalAmount;
        this.payments = new ArrayList<>();
    }

    // Print total amount (fixed)
    public void printTotalAmount() {
        System.out.println("Total amount: " + totalAmount);
    }

    // Implement Payable
    @Override
    public void pay(double amount) {
        addPayment(amount, PaymentMethod.CASH); // default method
    }

    // Add payment with method
    public void addPayment(double amount, PaymentMethod method) {
        payments.add(new Payment(amount, method));

        if (getPaidAmount() >= totalAmount) {
            paymentDate = LocalDate.now();
            System.out.println("Payment successful");
        } else {
            System.out.println("Partial payment added");
        }
    }

    // Calculate total paid
    public double getPaidAmount() {
        double sum = 0;
        for (Payment p : payments) {
            sum += p.getAmount();
        }
        return sum;
    }

    // Check if fully paid
    public boolean isFullyPaid() {
        return getPaidAmount() >= totalAmount;
    }

    public void printReceipt() {
        System.out.println("Total amount: " + totalAmount);
        System.out.println("Amount paid: " + getPaidAmount());

        System.out.println("\nPayments:");
        for (Payment p : payments) {
            System.out.println("- " + p.getMethod() + ": " + p.getAmount() + " on " + p.getDate());
        }

        if (isFullyPaid()) {
            System.out.println("\nStatus: Paid");
        } else {
            System.out.println("\nStatus: Pending (remaining: " + (totalAmount - getPaidAmount()) + ")");
        }
    }

    public void generateInvoice() {
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
    }
}