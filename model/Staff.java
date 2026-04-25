package model;

import database.HotelDatabase;
import enums.Role;

// Staff class that is a parent class for Admin and Receptionist
public abstract class Staff {
    // Common attributes for all staff members
    private String username;
    private String password;
    private String dateOfBirth;
    private int workingHours;
    //Role enum
    private Role role;
    // Constructor for Staff
    public Staff(String username, String password, String dateOfBirth, int workingHours, Role role) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.workingHours = workingHours;
        this.role = role;
    }
    // Getters and setters for Staff attributes
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        // Validate that the username is not null before setting it
        if(username != null) {
            this.username = username;
        } else {
            System.out.println("Invalid username. Please provide a non-null value.");
        }

    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        // Validate that the password is not null and has a minimum length before setting it
        if(password != null && password.length() >= 8) {
            this.password = password;
        } else {
            System.out.println("Invalid password. Please provide a non-null value.");
        }

    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        // Validate that the date of birth is not null before setting it
        if(dateOfBirth != null) {
            this.dateOfBirth = dateOfBirth;
        } else {
            System.out.println("Invalid date of birth. Please provide a non-null value.");
        }

    }
    public int getWorkingHours() {
        return workingHours;
    }
    public void setWorkingHours(int workingHours) {
        // Validate that the working hours is a non-negative value before setting it
        if(workingHours >= 0) {
            this.workingHours = workingHours;
        } else {
            System.out.println("Invalid working hours. Please provide a non-negative value.");
        }

    }
    // Method to view guest information
    public void viewGuest(){
        // code to view guest information
        for(int i = 0; i < HotelDatabase.getGuests().size(); i++){
            Guest g = HotelDatabase.getGuests().get(i);
            System.out.println("Username: " + g.getUsername());
            System.out.println("Balance: " + g.getBalance());
            System.out.println("Address: " + g.getAddress());
            System.out.println("Gender: " + g.getGender());
            System.out.println("----------------");
        }
    }
    // Method to view room information
    public void viewRoom(){
        // code to view room information
        for (int i = 0; i < HotelDatabase.getRooms().size(); i++) {
            Room r = HotelDatabase.getRooms().get(i);
            System.out.println("Room Number: " + r.getRoomNumber());
            System.out.println("Room Type: " + r.getType());
            System.out.println("----------------");
        }
    }
    // Method to view booking information
    public void viewBooking(){
        // code to view booking information
        for (int i = 0; i < HotelDatabase.getReservations().size(); i++) {
            Reservation res = HotelDatabase.getReservations().get(i);
            System.out.println("Room: " + res.getRoom().getRoomNumber());
            System.out.println("Status: " + res.getStatus());
            System.out.println("Check-in: " + res.getCheckIn());
            System.out.println("Check-out: " + res.getCheckOut());
            System.out.println("-------------------");
        }

    }
    // Abstract method to be implemented by subclasses for specific actions
    public abstract void performActions();





}