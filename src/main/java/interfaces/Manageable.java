package interfaces;
import exceptions.InvalidPriceException;
import model.Amenity;
import model.Room;
import model.RoomType;


public interface Manageable {

    //  Rooms's methods used to manage hotel rooms
    void addRoom(Room room);
    void viewRoom();
    void updateRoom(int roomNumber, double newPrice) throws InvalidPriceException;
    void deleteRoom(int roomNumber);

    // Amenity's methods used to handling amenities
    void addAmenity(Amenity a);
    void viewAmenity();
    void updateAmenity(String oldName, String newName);
    void deleteAmenity(Amenity a);

    // Room's methods used to handle type(single,double..etc)
    void addRoomType(RoomType t);
    void viewRoomTypes();
    void updateRoomType(String oldName, String newName);
    void deleteRoomType(RoomType roomType);
}