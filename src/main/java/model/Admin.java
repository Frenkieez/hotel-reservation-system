package model;

import enums.ReservationStatus;
import enums.Role;
import database.HotelDatabase;
import exceptions.InvalidPriceException;
import interfaces.Manageable;

// Admin class that extends Staff
public class Admin extends Staff implements Manageable {

    // Constructor for Admin
    public Admin(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours, Role.ADMIN);
    }

    // Override the performActions method to provide admin functions
    @Override
    public void performActions() {
        System.out.println("Performing admin actions...");
    }

    // ======================
    // ROOMS
    // ======================

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

    @Override
    public void viewRoom() {
        for (Room r : HotelDatabase.getRooms()) {
            System.out.println(
                    "Room Number: " + r.getRoomNumber()
                            + " | Type: " + r.getType().getName()
                            + " | Price: " + r.getType().getPricePerNight()
            );
        }
    }

    @Override
    public void updateRoom(int roomNumber, double newPrice) throws InvalidPriceException {
        if (newPrice < 0) {
            throw new InvalidPriceException("Price cannot be negative.");
        }

        for (Room r : HotelDatabase.getRooms()) {
            if (r.getRoomNumber() == roomNumber) {
                r.getType().setPricePerNight(newPrice);
                System.out.println("Room updated");
                return;
            }
        }

        System.out.println("There is no room called " + roomNumber);
    }

    @Override
    public void deleteRoom(int roomNumber) {
        for (Reservation res : HotelDatabase.getReservations()) {
            if (res.getRoom().getRoomNumber() == roomNumber
                    && res.getStatus() != ReservationStatus.CANCELLED
                    && res.getStatus() != ReservationStatus.COMPLETED) {

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

    // ======================
    // AMENITIES
    // ======================

    @Override
    public void addAmenity(Amenity amenity) {
        HotelDatabase.getAmenities().add(amenity);
        System.out.println("Amenity added");
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
            if (a.getName().equalsIgnoreCase(oldName)) {
                a.setName(newName);
                System.out.println("Amenity updated");
                return;
            }
        }

        System.out.println("There is no amenity called " + oldName);
    }

    @Override
    public void deleteAmenity(Amenity amenity) {
        if (!HotelDatabase.getAmenities().contains(amenity)) {
            System.out.println("There is no amenity called " + amenity.getName());
            return;
        }

        HotelDatabase.getAmenities().remove(amenity);
        System.out.println("Amenity deleted");
    }

    // ======================
    // ROOM TYPES
    // ======================

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

        System.out.println("There is no room type called " + oldName);
    }

    @Override
    public void deleteRoomType(RoomType roomType) {
        if (!HotelDatabase.getRoomTypes().contains(roomType)) {
            System.out.println("There is no room type called " + roomType.getName());
            return;
        }

        HotelDatabase.getRoomTypes().remove(roomType);
        System.out.println("Room Type deleted");
    }

    // ======================
    // GUEST BALANCE
    // ======================

    public void updateGuestBalance(String username, double newBalance) {
        for (Guest g : HotelDatabase.getGuests()) {
            if (g.getUsername().equalsIgnoreCase(username)) {
                g.updateBalance(newBalance);
                System.out.println("Guest balance updated");
                return;
            }
        }

        System.out.println("Guest not found");
    }
}