package interfaces;
import model.Room;


public interface Manageable {

    // ===== ROOMS =====
    void addRoom(Room room);
    void viewRooms();
    void updateRoom(int roomNumber, double newPrice);
    void deleteRoom(int roomNumber);

    // ===== AMENITIES =====
    void addAmenity(Amenity a);
    void viewAmenities();
    void updateAmenity(String oldName, String newName);
    void deleteAmenity(String name);

    // ===== ROOM TYPES =====
    void addRoomType(RoomType t);
    void viewRoomTypes();
    void updateRoomType(String oldName, String newName);
    void deleteRoomType(String name);
}
