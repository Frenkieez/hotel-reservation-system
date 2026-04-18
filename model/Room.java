package model;

import java.time.LocalDate;
import java.util.List;

/*
 * TEMPORARY VERSION of Room class
 * Used until we integrate full real classes
 */
public class Room {

    private int roomNumber;
    private RoomType type;
    private List<Amenity> amenities;
    private boolean isAvailable;

    public Room(int roomNumber, RoomType type, List<Amenity> amenities, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.amenities = amenities;
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable(LocalDate in, LocalDate out) {
        return isAvailable; // simple temp logic
    }

    public double calculatePrice(LocalDate in, LocalDate out) {
        return 100; // fixed temp value
    }

    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    public int getRoomNumber() { return roomNumber; }

    public RoomType getRoomType() { return type; }
}