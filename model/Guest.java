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

    // constructors
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
        if (username == null || username.isEmpty()){ // validates username
            throw new InvalidResgistrationException("Username MUST not be empty");
        }
        for (Guest guest : HotelDatabase.getGuests()) {
            if (guest.getUsername().equals(username)) { // checking here for duplicate usernames
                throw new InvalidResgistrationException("Username already exists");
            }
        }
        if (password == null || password.length() < 6){ // validates password
            throw new InvalidResgistrationException("Password MUST be at least 6 characters");
        }

        // if all previous tests are passed successfully, the user registers
        setUsername(username);
        setPassword(password);
        // adding to database:
        HotelDatabase.getGuests().add(this);
    }

    public void login(String username, String password) throws InvalidLoginException{
        if (username == null || password == null){ // validating username, password are not empty
            throw new InvalidLoginException("Username or password cannot be null");
        }

        if (! (this.username.equals(username)) ){ // validating correct username
            throw new InvalidLoginException("Username is not found");
        }

        if ( !(this.password.equals(password)) ){ // validating correct password
            throw new InvalidLoginException("Password is incorrect");
        }

        // if all previous tests are passed successfully, user has logged in
    }

    public Reservation makeReservation(Room room, LocalDate checkIn, LocalDate checkOut)
            throws InvalidDateException, InvalidReservationException {

        // validate dates first
        if (checkIn == null || checkOut == null) {
            throw new InvalidDateException("Dates cannot be null");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidDateException("Check-out must be after check-in");
        }

        // check availability BEFORE creating reservation
        if (!room.isAvailable(checkIn, checkOut)) {
            throw new InvalidDateException("Room not available for selected dates");
        }

        // create reservation
        Reservation res = new Reservation(this, room, checkIn, checkOut);

        // default state
        res.setStatus(ReservationStatus.PENDING);

        // adding reservation to list of reservations of guest
        this.reservations.add(res);

        // store in database
        HotelDatabase.getReservations().add(res);

        return res;
    }

    // viewReservations method :
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

    // add reservation method to make it encapsulated
    public void addReservation(Reservation reservation) {

        if (reservation == null) return;

        // prevent duplicates
        if (!this.reservations.contains(reservation)) {
            this.reservations.add(reservation);
        }
    }



    // invoice part
    public void payInvoice(Invoice invoice, double amount) {

        if (!invoices.contains(invoice)) {
            System.out.println("Invoice not found");
            return;
        }

        if (invoice.isPaid()) {
            System.out.println("Already paid");
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

    // setters
    void setUsername(String username) {
        if (username == null || username.isBlank()) return; // validation
        this.username = username;
    }

    void setPassword(String password){
        if (password == null || password.length() < 6) return; // validation
        this.password = password;
    }

    void setDateOfBirth(LocalDate dateOfBirth){
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) return; // validation
        this.dateOfBirth = dateOfBirth;
    }

    void setBalance(double balance){
        if (balance < 0) return; // validation
        this.balance = balance;
    }

    void setAddress(String address){
        if (address == null || address.isBlank()) return; // validation
        this.address = address;
    }

    void setGender(Gender gender){
        if (gender == null) return; // validation
        this.gender = gender;
    }

    // getters
    public String getUsername(){ return this.username; }
    String getPassword(){ return this.password; }
    LocalDate getDateofBirth(){ return this.dateOfBirth; }
    double getBalance(){ return this.balance; }
    String getAddress(){ return this.address; }
    Gender getGender(){ return this.gender; }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(this.reservations); // this line returns a list from the type Reservation for a better encapsulation
    }
    public List<Invoice> getInvoices() {
        return invoices;
    }
}
