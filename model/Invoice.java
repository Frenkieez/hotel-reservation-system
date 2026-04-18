package model;

/*
 * TEMPORARY VERSION of Invoice class
 * Used until we integrate full real classes
 */
public class Invoice {

    private Reservation reservation;
    private double totalAmount;

    public Invoice(Reservation reservation) {
        this.reservation = reservation;
    }

    public void generateInvoice() {
        totalAmount = 100; // fixed temp value
    }

    public void pay(double amount) {
        System.out.println("Payment done (temp)");
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}