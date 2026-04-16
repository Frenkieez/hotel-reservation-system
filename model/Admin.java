package model;

import database.HotelDatabase;

// Admin class that extends Staff
public class Admin extends Staff {
    // Constructor for Admin
    public Admin(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours);
    }
// Override the performActions method to provide admin-specific functionality
    @Override
    public void performActions() {
        // code to perform admin-specific actions
        System.out.println("Performing admin actions...");
    }
    // Method to add a new room
    public void addRoom(Room room){
        System.out.println("Adding a new room...");
         HotelDatabase.rooms.add(room);
         //rooms is the arraylist of room in the database class, will be fixed when database class is finised
    }
    // Method to remove a room
    public void removeRoom(Room room){
        System.out.println("Removing a room...");
        HotelDatabase.room.remove(room);
        //rooms is the arraylist of room in the database class, will be fixed when database class is finised
    }
    // Method to update room information
    public void UpdateRoom(){
        System.out.println("Updating room information...");
    }
    // Method to read room information
    public void readRoom(){
        System.out.println("Reading room information...");
    }

}
