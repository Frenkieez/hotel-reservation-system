package model;

// imports
import database.HotelDatabase;
import enums.Gender;
import enums.ReservationStatus;
import exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// the class
public class Guest {

    // fields
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;
    private List<Reservation> reservations;
    private List<Invoice> invoices = new ArrayList<>();

    // constructor
    public Guest(String username, String password,
                 LocalDate dateOfBirth, double balance,
                 String address, Gender gender){

        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        setAddress(address);
        setGender(gender);
        this.reservations = new ArrayList<>();
    }

    // methods :
    public void register(String username, String password) throws InvalidResgistrationException {
        if (username == null || username.isEmpty()){
            throw new InvalidResgistrationException("Username MUST not be empty");
        }
        for (Guest guest : HotelDatabase.getGuests()) {
            if (guest.getUsername().equals(username)) {
                throw new InvalidResgistrationException("Username already exists");
            }
        }
        if (password == null || password.length() < 6){
            throw new InvalidResgistrationException("Password MUST be at least 6 characters");
        }

        setUsername(username);
        setPassword(password);
        HotelDatabase.getGuests().add(this);
    }

    public void login(String username, String password) throws InvalidLoginException{
        if (username == null || password == null){
            throw new InvalidLoginException("Username or password cannot be null");
        }

        if (!(this.username.equals(username))){
            throw new InvalidLoginException("Username is not found");
        }

        if (!(this.password.equals(password))){
            throw new InvalidLoginException("Password is incorrect");
        }
    }

    public Reservation makeReservation(Room room, LocalDate checkIn, LocalDate checkOut)
            throws InvalidDateException, InvalidReservationException {

        if (checkIn == null || checkOut == null) {
            throw new InvalidDateException("Dates cannot be null");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidDateException("Check-out must be after check-in");
        }

        if (!room.isAvailable(checkIn, checkOut)) {
            throw new InvalidDateException("Room not available for selected dates");
        }

        Reservation res = new Reservation(this, room, checkIn, checkOut);

        res.setStatus(ReservationStatus.PENDING);

        this.reservations.add(res);

        HotelDatabase.getReservations().add(res);

        return res;
    }

    public List<Reservation> viewReservations() {
        return reservations;
    }

    public void cancelReservation(Reservation reservation) throws InvalidReservationException {
        boolean found = false;
        for (Reservation r : reservations) {
            if (r == reservation) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidReservationException("You have NO access to cancel this reservation");
        }

        reservation.cancel();
    }

    public void addReservation(Reservation reservation) {
        if (reservation == null) return;

        if (!this.reservations.contains(reservation)) {
            this.reservations.add(reservation);
        }
    }

    public void payInvoice(Invoice invoice, double amount) {

        if (!invoices.contains(invoice)) {
            System.out.println("Invoice not found");
            return;
        }

        if (invoice.isPaid()) {
            System.out.println("Already paid");
            return;
        }

        // prevents overpaying
        if (amount > (invoice.getTotalAmount() - invoice.getPaidAmount())) {
            System.out.println("Amount exceeds remaining balance");
            return;
        }

        if (balance < amount) {
            System.out.println("Insufficient balance");
            return;
        }

        balance -= amount;
        invoice.pay(amount);

        System.out.println("Payment successful");
    }

    void setUsername(String username) {
        if (username == null || username.isBlank()) return;
        this.username = username;
    }

    void setPassword(String password){
        if (password == null || password.length() < 6) return;
        this.password = password;
    }

    void setDateOfBirth(LocalDate dateOfBirth){
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) return;
        this.dateOfBirth = dateOfBirth;
    }

    public void setBalance(double balance){
        if (balance < 0) return;
        this.balance = balance;
    }

    void setAddress(String address){
        if (address == null || address.isBlank()) return;
        this.address = address;
    }

    void setGender(Gender gender){
        if (gender == null) return;
        this.gender = gender;
    }

    // controlled balance update
    public void updateBalance(double newBalance) {
        if (newBalance < 0) {
            System.out.println("Invalid balance");
            return;
        }
        this.balance = newBalance;
    }

    public String getUsername(){ return this.username; }
    String getPassword(){ return this.password; }
    LocalDate getDateofBirth(){ return this.dateOfBirth; }
    public double getBalance(){ return this.balance; }
    String getAddress(){ return this.address; }
    Gender getGender(){ return this.gender; }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(this.reservations);
    }
    public List<Invoice> getInvoices() {
        return invoices;
    }
}