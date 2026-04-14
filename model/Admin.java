package model;

import database.HotelDatabase;

public class Admin extends Staff {
    public Admin(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours);
    }

    @Override
    public void performActions() {
        // code to perform admin-specific actions
        System.out.println("Performing admin actions...");
    }
    public void addRoom(Room room){
        System.out.println("Adding a new room...");
         HotelDatabase.rooms.add(room);
         //rooms is the arraylist of room in the database class, will be fixed when database class is finised
    }
    public void removeRoom(Room room){
        System.out.println("Removing a room...");
        HotelDatabase.room.remove(room);
        //rooms is the arraylist of room in the database class, will be fixed when database class is finised
    }
    public void UpdateRoom(){
        System.out.println("Updating room information...");
    }
    public void readRoom(){
        System.out.println("Reading room information...");
    }

}
