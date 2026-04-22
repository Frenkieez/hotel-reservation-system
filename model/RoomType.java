// member2's version

package model;

import exceptions.InvalidPriceException;

public class RoomType {

    private String name;
    private double pricePerNight;

    // Constructor
    public RoomType(String name, double pricePerNight) throws InvalidPriceException {
        this.name = name;

        if (pricePerNight <= 0) {
            throw new InvalidPriceException("Price per night must be greater than 0.");
        }

        this.pricePerNight = pricePerNight;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    // Setter with validation
    public void setPricePerNight (double pricePerNight) throws InvalidPriceException{
        if (pricePerNight <= 0) {
            throw new InvalidPriceException("Price per night must be greater than 0.");
        }

        this.pricePerNight = pricePerNight;
    }

    public void setName(String name) {                                                                                  // added by 3elba
        this.name = name;
    }

    // outputs room type instead of hashing
    @Override
    public String toString() {
        return name;
    }
}