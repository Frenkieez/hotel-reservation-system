package model;
import enums.Role;
import database.HotelDatabase;
import exceptions.InvalidPriceException;
import interfaces.Manageable;
// Admin class that extends Staff
public class Admin extends Staff implements Manageable {
    // Constructor for Admin
    public Admin(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours, Role.ADMIN);
    }
    // Override the performActions method to provide admin-specific functionality
    @Override
    public void performActions() {
        // code to perform admin-specific actions
        System.out.println("Performing admin actions...");
    }
    //--Room--
    // Method to add a new room
    @Override
    public void addRoom(Room room){
        HotelDatabase.rooms.add(room);
        //rooms is the arraylist of room in the database class, will be fixed when database class is finished
    }
    // Method to remove a room
    @Override
    public void deleteRoom(Room room){
        HotelDatabase.rooms.remove(room);
        //rooms is the arraylist of room in the database class, will be fixed when database class is finished
    }
    // Method to update room information
    @Override
    public void updateRoom(int roomNumber, double newPrice) throws InvalidPriceException {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            Room r = HotelDatabase.rooms.get(i);
            //getRoomNumber and setPrice are methods in the other classes that will be implemented when the classes are finished
            if (r.getRoomNumber() == roomNumber) {
                r.getType().setPricePerNight(newPrice);                                                                 // edited by 3elba
                System.out.println("Room updated");
                return;
            }
        }
    }

    // Method to read room information
    @Override
    public void readRoom(int roomNumber) {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            Room r = HotelDatabase.rooms.get(i);
            //getRoomNumber and getPrice are methods in the other classes that will be implemented when the classes are finished
            if (r.getRoomNumber() == roomNumber) {
                System.out.println("Room Number: " + r.getRoomNumber());
                System.out.println("Price: " + r.getType().getPricePerNight());                                         // edited by 3elba
                return;
            }
        }


    }
    //--Amenity--
    @Override
    public void addAmenity(Amenity amenity) {
        HotelDatabase.amenities.add(amenity);
        System.out.println("Amenity added");
        //amenities is the arraylist of amenities in the database class, will be fixed when database class is finished

    }
    @Override
    public void deleteAmenity(Amenity amenity) {
        HotelDatabase.amenities.remove(amenity);
        System.out.println("Amenity deleted");

        //amenities is the arraylist of amenities in the database class, will be fixed when database class is finished

    }
    @Override
    public void updateAmenity(String oldName, String newName) {
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            Amenity a = HotelDatabase.amenities.get(i);
            //getName and setName are methods in the other classes that will be implemented when the classes are finished
            if (a.getName().equals(oldName)) {
                a.setName(newName);
                System.out.println("Amenity updated");
                return;
            }
        }
        @Override
        public void viewAmenities() {
            for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
                System.out.println(HotelDatabase.amenities.get(i).getName());
            }
        }



        // --RoomType--
        @Override
        public void addRoomType(RoomType t) {
            HotelDatabase.roomTypes.add(t);
        }

        @Override
        public void viewRoomTypes() {
            for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
                System.out.println(HotelDatabase.roomTypes.get(i).getName());
            }
        }

        @Override
        public void updateRoomType(String oldName, String newName) {
            for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
                RoomType t = HotelDatabase.roomTypes.get(i);

                if (t.getName().equals(oldName)) {
                    t.setName(newName);
                    return;
                }
            }
        }

        @Override
        public void deleteRoomType(String name) {
            for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
                if (HotelDatabase.roomTypes.get(i).getName().equals(name)) {
                    HotelDatabase.roomTypes.remove(i);
                    return;
                }
            }
        }
    }
}

