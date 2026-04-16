package model;
import enums.Role;
import database.HotelDatabase;
import interfaces.Manageable;
// Admin class that extends Staff
public class Admin extends Staff implements Manageable {
    // Constructor for Admin
    public Admin(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours, Role.ADMIN);
    }
// Override the performActions method to provide admin-specific functionality
    @Override
    public void performActions() {
        // code to perform admin-specific actions
        System.out.println("Performing admin actions...");
    }
    // Method to add a new room
    @Override
    public void addRoom(Room room){
        System.out.println("Adding a new room...");
         HotelDatabase.rooms.add(room);
         //rooms is the arraylist of room in the database class, will be fixed when database class is finised
    }
    // Method to remove a room
    @Override
    public void deleteRoom(Room room){
        System.out.println("Removing a room...");
        HotelDatabase.rooms.remove(room);
        //rooms is the arraylist of room in the database class, will be fixed when database class is finised
    }
    // Method to update room information
    @Override
    public void updateRoom(int roomNumber, double newPrice) {
    for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
        Room r = HotelDatabase.rooms.get(i);
        //getRoomNumber and setPrice are methods in the other classes that will be implemented when the classes are finished
        if (r.getRoomNumber() == roomNumber) {
            r.setPrice(newPrice);
            System.out.println("Room updated");
            return;
        }
    }

    System.out.println("Room not found");
}
    // Method to read room information
    public void readRoom(int roomNumber) {
    for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
        Room r = HotelDatabase.rooms.get(i);
        //getRoomNumber and getPrice are methods in the other classes that will be implemented when the classes are finished
        if (r.getRoomNumber() == roomNumber) {
            System.out.println("Room Number: " + r.getRoomNumber());
            System.out.println("Price: " + r.getPrice());
            return;
        }
    }

    System.out.println("Room not found");
}

}
