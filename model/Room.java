/******* THIS IS JUST A PROTOTYPE VERSION FOR THE ROOM CLASS TILL MEMBER 2 STARTS IN IMPLEMENTING THE REAL ONE *******/

package model;

public class Room {

    private int roomNumber;
    private double pricePerNight;
    private RoomType type;

    public Room(int roomNumber, double pricePerNight, RoomType type) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public RoomType getType() {
        return type;
    }
}