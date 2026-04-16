package model;

import enums.Role;

// Staff class that serves as a base class for Admin and Receptionist
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
    public void ViewGuest(){
        // code to view guest information
        System.out.println("Viewing guest information...");
    }
    // Method to view room information
    public void ViewRoom(){
        // code to view room information
        System.out.println("Viewing room information...");
    }
    // Method to view booking information
    public void ViewBooking(){
        // code to view booking information
        System.out.println("Viewing booking information...");
    }
    // Abstract method to be implemented by subclasses for specific actions
    public abstract void performActions();


    


}
