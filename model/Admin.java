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

    // Override the performActions method to provide admin functions
    @Override
    public void performActions() {
        // code to perform admin-specific actions
        System.out.println("Performing admin actions...");
    }

    // Rooms

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
        System.out.println("There is no room called " + roomNumber);
    }


    // Amentities
    //method to add a new amenity
    @Override
    public void addAmenity(Amenity amenity) {
        HotelDatabase.getAmenities().add(amenity);
        System.out.println("Amenity added");
    }
    //method to read amenity information
    @Override
    public void viewAmenity() {
        for (Amenity a : HotelDatabase.getAmenities()) {
            System.out.println("Amenity: " + a.getName());
        }
    }
    //method to update amenity information
    @Override
    public void updateAmenity(String oldName, String newName) {
        for (Amenity a : HotelDatabase.getAmenities()) {
            a.setName(newName);
            System.out.println("Amenity updated");
            return;
        }
    }

        //method to remove an amenity
        @Override
        public void deleteAmenity(Amenity amenity) {

            if (!HotelDatabase.getAmenities().contains(amenity)) {

                System.out.println("There is no amenity called " + amenity.getName());
                return;
            }

            HotelDatabase.getAmenities().remove(amenity);
            System.out.println("Amenity deleted");
        }

        // Room Types
//method to add a new room type
        @Override
        public void addRoomType(RoomType t) {
            HotelDatabase.getRoomTypes().add(t);
            System.out.println("Room Type added");
        }
        // method to read room type information
        @Override
        public void viewRoomTypes() {
            for (RoomType t : HotelDatabase.getRoomTypes()) {
                System.out.println("Type: " + t.getName() + " | Base Price: " + t.getPricePerNight());
            }
        }
        //method to update room type information
        @Override
        public void updateRoomType(String oldName, String newName) {

            for (RoomType t : HotelDatabase.getRoomTypes()) {
                if (t.getName().equalsIgnoreCase(oldName)) {
                    t.setName(newName);
                    System.out.println("Room Type updated");
                    return;
                }
            }
            System.out.println("There is no room type called " + oldName);
        }
        //method to remove a room type
        @Override
        public void deleteRoomType(RoomType roomType) {
            if (!HotelDatabase.getRoomTypes().contains(roomType)) {
                System.out.println("There is no room type called " + roomType.getName());
                return;
            }
            HotelDatabase.getRoomTypes().remove(roomType);
            System.out.println("Room Type deleted");
        }
}