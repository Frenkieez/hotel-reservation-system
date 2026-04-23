package model;
import enums.ReservationStatus;
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
        for (Room r : HotelDatabase.getRooms()) {
            if (r.getRoomNumber() == room.getRoomNumber()) {
                System.out.println("Room number already exists");
                return;
            }
        }
        HotelDatabase.getRooms().add(room);
        System.out.println("Room added successfully");
    }

    // Method to read room information
    @Override
    public void viewRoom() {
        for (Room r : HotelDatabase.getRooms()) {
            //getRoomNumber and getPrice are methods in the other classes that will be implemented when the classes are finished
            System.out.println("Room Number: " + r.getRoomNumber() + " | Type: " + r.getType().getName() + " | Price: " + r.getType().getPricePerNight());
        }
    }

    // Method to update room information
    @Override
    public void updateRoom(int roomNumber, double newPrice) throws InvalidPriceException {

        if (newPrice < 0) throw new InvalidPriceException("Price cannot be negative.");

        for (Room r : HotelDatabase.getRooms()) {
            if (r.getRoomNumber() == roomNumber) {
                r.getType().setPricePerNight(newPrice);
                System.out.println("Room updated");
                return;
            }
        }

        // CHANGED: clear message
        System.out.println("There is no room called " + roomNumber);
    }

    // Method to remove a room
    @Override
    public void deleteRoom(int roomNumber) {

        // check active reservations first
        for (Reservation res : HotelDatabase.getReservations()) {
            if (res.getRoom().getRoomNumber() == roomNumber &&
                    res.getStatus() != ReservationStatus.CANCELLED &&
                    res.getStatus() != ReservationStatus.COMPLETED) {

                System.out.println("Cannot delete room: it has active reservations");
                return;
            }
        }

        for (int i = 0; i < HotelDatabase.getRooms().size(); i++) {
            if (HotelDatabase.getRooms().get(i).getRoomNumber() == roomNumber) {
                HotelDatabase.getRooms().remove(i);
                System.out.println("Room deleted");
                return;
            }
        }

        // CHANGED
        System.out.println("There is no room called " + roomNumber);
    }


    // ===== AMENITIES =====

    @Override
    public void addAmenity(Amenity amenity) {
        HotelDatabase.getAmenities().add(amenity);
        System.out.println("Amenity added");
        //amenities is the arraylist of amenities in the database class, will be fixed when database class is finished
    }

    @Override
    public void viewAmenity() {
        for (Amenity a : HotelDatabase.getAmenities()) {
            System.out.println("Amenity: " + a.getName());
        }
    }

    @Override
    public void updateAmenity(String oldName, String newName) {
        for (Amenity a : HotelDatabase.getAmenities()) {
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

        if (!HotelDatabase.getAmenities().contains(amenity)) {
            // CHANGED
            System.out.println("There is no amenity called " + amenity.getName());
            return;
        }

        HotelDatabase.getAmenities().remove(amenity);
        System.out.println("Amenity deleted");
    }

    // ===== ROOM TYPES =====

    @Override
    public void addRoomType(RoomType t) {
        HotelDatabase.getRoomTypes().add(t);
        System.out.println("Room Type added");
    }

    @Override
    public void viewRoomTypes() {
        for (RoomType t : HotelDatabase.getRoomTypes()) {
            System.out.println("Type: " + t.getName() + " | Base Price: " + t.getPricePerNight());
        }
    }

    @Override
    public void updateRoomType(String oldName, String newName) {

        for (RoomType t : HotelDatabase.getRoomTypes()) {
            if (t.getName().equalsIgnoreCase(oldName)) {
                t.setName(newName);
                System.out.println("Room Type updated");
                return;
            }
        }
        // CHANGED
        System.out.println("There is no room type called " + oldName);
    }

    @Override
    public void deleteRoomType(RoomType roomType) {
        if (!HotelDatabase.getRoomTypes().contains(roomType)) {
            // CHANGED
            System.out.println("There is no room type called " + roomType.getName());
            return;
        }
        HotelDatabase.getRoomTypes().remove(roomType);
        System.out.println("Room Type deleted");
    }


}


