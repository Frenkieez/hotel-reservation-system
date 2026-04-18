package model;

import enums.Role;
import java.time.LocalDate;

/*
 * TEMPORARY VERSION of Admin class
 * Used until we integrate full real classes
 */
public class Admin extends Staff {

    public Admin(String username, String password, LocalDate dob, int hours, Role role) {
        super(username, password, dob, hours, role);
    }

    public void addRoom(Room room) {
        System.out.println("Room added (temp)");
    }

    public void updateRoom(Room room) {
        System.out.println("Room updated (temp)");
    }

    public void deleteRoom(int roomNumber) {
        System.out.println("Room deleted (temp)");
    }

    public void manageAmenities(Amenity amenity) {
        System.out.println("Amenity managed (temp)");
    }

    public void manageRoomTypes(RoomType type) {
        System.out.println("RoomType managed (temp)");
    }

    @Override
    public void performDuties() {
        System.out.println("Admin duties (temp)");
    }
}