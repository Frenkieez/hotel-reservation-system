// member2's version

package model;
import exceptions.InvalidDateException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Room {

    private int roomNumber;
    private RoomType type;
    private ArrayList<Amenity> amenities;
    private boolean isAvailable;

    // Constructor
    public Room(int roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.amenities = new ArrayList<>();
        this.isAvailable = true;
    }

    // Check availability
    public boolean isAvailable(LocalDate in, LocalDate out) throws InvalidDateException {
        if (in.isAfter(out) || in.isEqual(out)) {
            throw new InvalidDateException("Check-out date must be after check-in date.");
        }
        return isAvailable;
    }

    // Calculate price
    public double calculatePrice(LocalDate in, LocalDate out) throws InvalidDateException {
        long days = ChronoUnit.DAYS.between(in, out);

        if (days <= 0) {
            throw new InvalidDateException("Check-out date must be after check-in date.");
        }

        double basePrice = days * type.getPricePerNight();
        double extra = amenities.size() * 20;

        return basePrice + extra;
    }

    // Set availability
    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    // Getters
    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public boolean getAvailability() {
        return isAvailable;
    }

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }
}