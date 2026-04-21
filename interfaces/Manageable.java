package interfaces;
import exceptions.InvalidPriceException;
import model.Amenity;
import model.Room;
import model.RoomType;


public interface Manageable {

    // ===== ROOMS =====
    void addRoom(Room room);
    void viewRoom();
    void updateRoom(int roomNumber, double newPrice) throws InvalidPriceException;
    void deleteRoom(int roomNumber);

    // ===== AMENITIES =====
    void addAmenity(Amenity a);
    void viewAmenity();
    void updateAmenity(String oldName, String newName);
    void deleteAmenity(Amenity a);

    // ===== ROOM TYPES =====
    void addRoomType(RoomType t);
    void viewRoomTypes();
    void updateRoomType(String oldName, String newName);
    void deleteRoomType(RoomType roomType);
}