package model;

/*
 * TEMPORARY VERSION of RoomType class
 * Used until we integrate full real classes
 */
public class RoomType {

    private String name;
    private double pricePerNight;

    public RoomType(String name, double pricePerNight) {
        this.name = name;
        this.pricePerNight = pricePerNight;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public String getName() {
        return name;
    }
}