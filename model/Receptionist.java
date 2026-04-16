package model;

// Receptionist class that extends Staff
public class Receptionist extends Staff {
    // Constructor for Receptionist
    public Receptionist(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours);
    }
    // Override the performActions method to provide receptionist-specific functionality
    @Override
    public void performActions() {
        // code to perform receptionist-specific actions
        System.out.println("Performing receptionist actions...");
    }

// Method to check in a guest
    public void checkInGuest(){
        System.out.println("Checking in a guest...");
    }
    // Method to check out a guest
    public void checkOutGuest(){
            System.out.println("Checking out a guest...");
        }


}
