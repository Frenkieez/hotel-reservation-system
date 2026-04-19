package database;
import java.util.ArrayList;
import model.Room;
import model.RoomType;
import model.Amenity;
import model.Guest;
import model.Reservation;

public class HotelDatabase {
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<Guest> guests = new ArrayList<>();
    public static ArrayList<Amenity> amenities = new ArrayList<>();
     public static ArrayList<RoomType> roomTypes = new ArrayList<>();
}
