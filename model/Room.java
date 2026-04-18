/******* THIS IS JUST A PROTOTYPE VERSION FOR THE ROOM CLASS TILL MEMBER 2 STARTS IN IMPLEMENTING THE REAL ONE *******/

package model;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Room {

    private int roomNumber;
    private RoomType type;
    private List<Amenity> amenities;
    private boolean isAvailable = true;

    public boolean isAvailable(LocalDate in, LocalDate out) {
        return isAvailable;
    }

    public double calculatePrice(LocalDate in, LocalDate out) {
        long days = ChronoUnit.DAYS.between(in, out);

        double base = type.getPricePerNight();
        double amenitiesCost = amenities.size() * 10; // simple rule

        return (base + amenitiesCost) * days;
    }

    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    public int getRoomNumber() { return roomNumber; }
}