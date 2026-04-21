package model;
import enums.Role;
import database.HotelDatabase;
import exceptions.InvalidPriceException;
import interfaces.Manageable;

import java.math.RoundingMode;

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

    // ===== ROOMS =====

    // Method to add a new room
    @Override
    public void addRoom(Room room) {
        HotelDatabase.rooms.add(room);
        //rooms is the arraylist of room in the database class, will be fixed when database class is finished
    }

    // Method to read room information
    @Override
    public void viewRoom() {
        for (Room r : HotelDatabase.rooms) {
            //getRoomNumber and getPrice are methods in the other classes that will be implemented when the classes are finished
            System.out.println("Room Number: " + r.getRoomNumber() + " | Type: " + r.getType().getName() + " | Price: " + r.getType().getPricePerNight());
        }
    }

    // Method to update room information
    @Override
    public void updateRoom(int roomNumber, double newPrice) throws InvalidPriceException {
        if (newPrice < 0) throw new InvalidPriceException("Price cannot be negative.");

        for (Room r : HotelDatabase.rooms) {
            //getRoomNumber and setPrice are methods in the other classes that will be implemented when the classes are finished
            if (r.getRoomNumber() == roomNumber) {
                r.getType().setPricePerNight(newPrice); // edited by 3elba
                System.out.println("Room updated");
                return;
            }
        }
        System.out.println("Room not found.");
    }

    // Method to remove a room
    @Override
    public void deleteRoom(int roomNumber) {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).getRoomNumber() == roomNumber) {
                HotelDatabase.rooms.remove(i);
                System.out.println("Room deleted");
                return;
            }
        }
        //rooms is the arraylist of room in the database class, will be fixed when database class is finished
    }
    // ===== AMENITIES =====

    @Override
    public void addAmenity(Amenity amenity) {
        HotelDatabase.amenities.add(amenity);
        System.out.println("Amenity added");
        //amenities is the arraylist of amenities in the database class, will be fixed when database class is finished
    }

    @Override
    public void viewAmenity() {
        for (Amenity a : HotelDatabase.amenities) {
            System.out.println("Amenity: " + a.getName());
        }
    }

    @Override
    public void updateAmenity(String oldName, String newName) {
        for (Amenity a : HotelDatabase.amenities) {
            //getName and setName are methods in the other classes that will be implemented when the classes are finished
            if (a.getName().equalsIgnoreCase(oldName)) {
                a.setName(newName);
                System.out.println("Amenity updated");
                return;
            }
        }
    }

    @Override
    public void deleteAmenity(Amenity amenity) {
        HotelDatabase.amenities.remove(amenity);
        System.out.println("Amenity deleted");
        //amenities is the arraylist of amenities in the database class, will be fixed when database class is finished
    }

    // ===== ROOM TYPES =====

    @Override
    public void addRoomType(RoomType t) {
        HotelDatabase.roomTypes.add(t);
        System.out.println("Room Type added");
    }

    @Override
    public void viewRoomTypes() {
        for (RoomType t : HotelDatabase.roomTypes) {
            System.out.println("Type: " + t.getName() + " | Base Price: " + t.getPricePerNight());
        }
    }

    @Override
    public void updateRoomType(String oldName, String newName) {
        for (RoomType t : HotelDatabase.roomTypes) {
            if (t.getName().equalsIgnoreCase(oldName)) {
                t.setName(newName);
                System.out.println("Room Type updated");
                return;
            }
        }
    }

    @Override
    public void deleteRoomType(RoomType roomType) {
        HotelDatabase.roomTypes.remove(roomType);
        System.out.println("Room Type deleted");
    }


}


