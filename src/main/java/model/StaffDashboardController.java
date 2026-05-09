package model;

import database.HotelDatabase;
import enums.ReservationStatus;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class StaffDashboardController {

    private Staff staff;

    private final ArrayList<Reservation> displayedReservations = new ArrayList<>();

    @FXML private Label staffLabel;
    @FXML private Tab adminTab;

    @FXML private TextArea staffOutput;
    @FXML private ListView<String> reservationList;

    @FXML private TextField roomNumberField;
    @FXML private TextField roomPriceField;
    @FXML private ComboBox<String> roomTypeBox;

    // Amenity fields
    @FXML private TextField amenityNameField;
    @FXML private TextField amenityOldNameField;
    @FXML private TextField amenityNewNameField;
    @FXML private TextField amenityDeleteNameField;
    @FXML private TextArea amenityOutput;

    // Room Type fields
    @FXML private TextField roomTypeNameField;
    @FXML private TextField roomTypePriceField;
    @FXML private TextField roomTypeOldNameField;
    @FXML private TextField roomTypeNewNameField;
    @FXML private TextField roomTypeDeleteNameField;
    @FXML private TextArea roomTypeOutput;

    @FXML
    public void initialize() {
        roomTypeBox.getItems().clear();
    }

    public void setStaff(Staff staff) {
        this.staff = staff;

        staffLabel.setText("Logged in as: " + staff.getUsername() + " (" + staff.getRole() + ")");

        refreshRoomTypesCombo();
        refreshReservations();

        if (!(staff instanceof Admin)) {
            adminTab.setDisable(true);
        }
    }

    @FXML
    private void viewGuests() {
        StringBuilder sb = new StringBuilder();

        if (HotelDatabase.getGuests().isEmpty()) {
            staffOutput.setText("No guests found.");
            return;
        }

        for (Guest g : HotelDatabase.getGuests()) {
            sb.append("Username: ").append(g.getUsername()).append("\n");
            sb.append("Balance: ").append(g.getBalance()).append("\n");
            sb.append("Address: ").append(g.getAddress()).append("\n");
            sb.append("Gender: ").append(g.getGender()).append("\n");
            sb.append("----------------------\n");
        }

        staffOutput.setText(sb.toString());
    }

    @FXML
    private void viewRooms() {
        StringBuilder sb = new StringBuilder();

        if (HotelDatabase.getRooms().isEmpty()) {
            staffOutput.setText("No rooms found.");
            return;
        }

        for (Room r : HotelDatabase.getRooms()) {
            sb.append("Room: ").append(r.getRoomNumber()).append("\n");
            sb.append("Type: ").append(r.getType().getName()).append("\n");
            sb.append("Price: ").append(r.getType().getPricePerNight()).append("\n");
            sb.append("Amenities: ").append(r.getAmenities().size()).append("\n");
            sb.append("----------------------\n");
        }

        staffOutput.setText(sb.toString());
    }

    @FXML
    private void refreshReservations() {
        displayedReservations.clear();
        reservationList.getItems().clear();

        for (Reservation r : HotelDatabase.getReservations()) {
            displayedReservations.add(r);
            reservationList.getItems().add(
                    "Guest: " + r.getGuest().getUsername()
                            + " | Room: " + r.getRoom().getRoomNumber()
                            + " | " + r.getStatus()
                            + " | " + r.getCheckIn()
                            + " to " + r.getCheckOut()
            );
        }
    }

    @FXML
    private void confirmReservation() {
        int index = reservationList.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            HotelFXApp.showError("Reservation Error", "Choose a reservation first.");
            return;
        }

        try {
            Reservation reservation = displayedReservations.get(index);
            reservation.confirm();

            HotelFXApp.showInfo("Success", "Reservation confirmed.");
            refreshReservations();

        } catch (Exception e) {
            HotelFXApp.showError("Reservation Error", e.getMessage());
        }
    }

    @FXML
    private void checkInGuest() {
        int index = reservationList.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            HotelFXApp.showError("Check-in Error", "Choose a reservation first.");
            return;
        }

        Reservation reservation = displayedReservations.get(index);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            HotelFXApp.showError("Check-in Error", "Reservation must be CONFIRMED first.");
            return;
        }

        HotelFXApp.showInfo("Success", "Guest checked in.");
    }

    @FXML
    private void checkOutGuest() {
        int index = reservationList.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            HotelFXApp.showError("Check-out Error", "Choose a reservation first.");
            return;
        }

        try {
            Reservation reservation = displayedReservations.get(index);

            if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
                HotelFXApp.showError("Check-out Error", "Reservation must be CONFIRMED first.");
                return;
            }

            double total = reservation.calculateTotal();
            Invoice invoice = new Invoice(reservation, total);

            HotelDatabase.getInvoices().add(invoice);
            reservation.getGuest().getInvoices().add(invoice);

            reservation.complete();

            HotelFXApp.showInfo("Success", "Check-out completed. Invoice amount: " + total);
            refreshReservations();

        } catch (Exception e) {
            HotelFXApp.showError("Check-out Error", e.getMessage());
        }
    }

    // ======================
    // ROOM CRUD WITH STRICT VALIDATION
    // ======================

    @FXML
    private void addRoom() {
        if (!(staff instanceof Admin)) {
            HotelFXApp.showError("Access Error", "Only admins can add rooms.");
            return;
        }

        String roomNumberText = roomNumberField.getText() == null ? "" : roomNumberField.getText().trim();
        String selectedTypeName = roomTypeBox.getValue();

        if (roomNumberText.isEmpty()) {
            HotelFXApp.showError("Room Error", "Room number is required.");
            return;
        }

        int roomNumber;

        try {
            roomNumber = Integer.parseInt(roomNumberText);
        } catch (NumberFormatException e) {
            HotelFXApp.showError("Room Error", "Room number must be a valid integer.");
            return;
        }

        if (roomNumber <= 0) {
            HotelFXApp.showError("Room Error", "Room number must be greater than zero.");
            return;
        }

        if (selectedTypeName == null || selectedTypeName.isBlank()) {
            HotelFXApp.showError("Room Error", "Please choose a room type.");
            return;
        }

        for (Room room : HotelDatabase.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                HotelFXApp.showError("Room Error", "A room with this number already exists.");
                return;
            }
        }

        RoomType selectedType = null;

        for (RoomType type : HotelDatabase.getRoomTypes()) {
            if (type.getName().equals(selectedTypeName)) {
                selectedType = type;
                break;
            }
        }

        if (selectedType == null) {
            HotelFXApp.showError("Room Error", "Selected room type was not found.");
            return;
        }

        Room newRoom = new Room(roomNumber, selectedType);
        HotelDatabase.getRooms().add(newRoom);

        HotelFXApp.showInfo("Success", "Room added successfully.");
        roomNumberField.clear();
        viewRooms();
    }

    @FXML
    private void updateRoomPrice() {
        if (!(staff instanceof Admin)) {
            HotelFXApp.showError("Access Error", "Only admins can update room prices.");
            return;
        }

        String roomNumberText = roomNumberField.getText() == null ? "" : roomNumberField.getText().trim();
        String priceText = roomPriceField.getText() == null ? "" : roomPriceField.getText().trim();

        if (roomNumberText.isEmpty()) {
            HotelFXApp.showError("Room Error", "Room number is required.");
            return;
        }

        if (priceText.isEmpty()) {
            HotelFXApp.showError("Room Error", "New price is required.");
            return;
        }

        int roomNumber;
        double price;

        try {
            roomNumber = Integer.parseInt(roomNumberText);
        } catch (NumberFormatException e) {
            HotelFXApp.showError("Room Error", "Room number must be a valid integer.");
            return;
        }

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            HotelFXApp.showError("Room Error", "New price must be a valid number.");
            return;
        }

        if (roomNumber <= 0) {
            HotelFXApp.showError("Room Error", "Room number must be greater than zero.");
            return;
        }

        if (price <= 0) {
            HotelFXApp.showError("Room Error", "New price must be greater than zero.");
            return;
        }

        Room selectedRoom = null;

        for (Room room : HotelDatabase.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            HotelFXApp.showError("Room Error", "No room found with number " + roomNumber + ".");
            return;
        }

        try {
            selectedRoom.getType().setPricePerNight(price);

            HotelFXApp.showInfo("Success", "Room price updated successfully.");
            roomNumberField.clear();
            roomPriceField.clear();
            viewRooms();

        } catch (Exception e) {
            HotelFXApp.showError("Room Error", e.getMessage());
        }
    }

    @FXML
    private void deleteRoom() {
        if (!(staff instanceof Admin)) {
            HotelFXApp.showError("Access Error", "Only admins can delete rooms.");
            return;
        }

        String roomNumberText = roomNumberField.getText() == null ? "" : roomNumberField.getText().trim();

        if (roomNumberText.isEmpty()) {
            HotelFXApp.showError("Room Error", "Room number is required.");
            return;
        }

        int roomNumber;

        try {
            roomNumber = Integer.parseInt(roomNumberText);
        } catch (NumberFormatException e) {
            HotelFXApp.showError("Room Error", "Room number must be a valid integer.");
            return;
        }

        if (roomNumber <= 0) {
            HotelFXApp.showError("Room Error", "Room number must be greater than zero.");
            return;
        }

        Room selectedRoom = null;

        for (Room room : HotelDatabase.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            HotelFXApp.showError("Room Error", "No room found with number " + roomNumber + ".");
            return;
        }

        for (Reservation reservation : HotelDatabase.getReservations()) {
            if (reservation.getRoom().getRoomNumber() == roomNumber
                    && reservation.getStatus() != ReservationStatus.CANCELLED
                    && reservation.getStatus() != ReservationStatus.COMPLETED) {

                HotelFXApp.showError(
                        "Room Error",
                        "Cannot delete room " + roomNumber + " because it has active reservations."
                );
                return;
            }
        }

        HotelDatabase.getRooms().remove(selectedRoom);

        HotelFXApp.showInfo("Success", "Room deleted successfully.");
        roomNumberField.clear();
        viewRooms();
    }

    // ======================
    // AMENITY CRUD
    // ======================

    @FXML
    private void addAmenity() {
        if (!(staff instanceof Admin)) return;

        String name = amenityNameField.getText().trim();

        if (name.isEmpty()) {
            HotelFXApp.showError("Amenity Error", "Amenity name is required.");
            return;
        }

        for (Amenity amenity : HotelDatabase.getAmenities()) {
            if (amenity.getName().equalsIgnoreCase(name)) {
                HotelFXApp.showError("Amenity Error", "This amenity already exists.");
                return;
            }
        }

        Admin admin = (Admin) staff;
        admin.addAmenity(new Amenity(name));

        HotelFXApp.showInfo("Success", "Amenity added successfully.");
        amenityNameField.clear();
        viewAmenities();
    }

    @FXML
    private void viewAmenities() {
        StringBuilder sb = new StringBuilder();

        if (HotelDatabase.getAmenities().isEmpty()) {
            amenityOutput.setText("No amenities found.");
            return;
        }

        sb.append("Amenities:\n");
        sb.append("----------------------\n");

        for (Amenity amenity : HotelDatabase.getAmenities()) {
            sb.append("- ").append(amenity.getName()).append("\n");
        }

        amenityOutput.setText(sb.toString());
    }

    @FXML
    private void updateAmenity() {
        if (!(staff instanceof Admin)) return;

        String oldName = amenityOldNameField.getText().trim();
        String newName = amenityNewNameField.getText().trim();

        if (oldName.isEmpty() || newName.isEmpty()) {
            HotelFXApp.showError("Amenity Error", "Old name and new name are required.");
            return;
        }

        boolean found = false;

        for (Amenity amenity : HotelDatabase.getAmenities()) {
            if (amenity.getName().equalsIgnoreCase(oldName)) {
                found = true;
                break;
            }
        }

        if (!found) {
            HotelFXApp.showError("Amenity Error", "Amenity not found.");
            return;
        }

        for (Amenity amenity : HotelDatabase.getAmenities()) {
            if (amenity.getName().equalsIgnoreCase(newName)) {
                HotelFXApp.showError("Amenity Error", "Another amenity already has this name.");
                return;
            }
        }

        Admin admin = (Admin) staff;
        admin.updateAmenity(oldName, newName);

        HotelFXApp.showInfo("Success", "Amenity updated successfully.");
        amenityOldNameField.clear();
        amenityNewNameField.clear();
        viewAmenities();
    }

    @FXML
    private void deleteAmenity() {
        if (!(staff instanceof Admin)) return;

        String name = amenityDeleteNameField.getText().trim();

        if (name.isEmpty()) {
            HotelFXApp.showError("Amenity Error", "Amenity name is required.");
            return;
        }

        Amenity selectedAmenity = null;

        for (Amenity amenity : HotelDatabase.getAmenities()) {
            if (amenity.getName().equalsIgnoreCase(name)) {
                selectedAmenity = amenity;
                break;
            }
        }

        if (selectedAmenity == null) {
            HotelFXApp.showError("Amenity Error", "Amenity not found.");
            return;
        }

        Admin admin = (Admin) staff;
        admin.deleteAmenity(selectedAmenity);

        HotelFXApp.showInfo("Success", "Amenity deleted successfully.");
        amenityDeleteNameField.clear();
        viewAmenities();
    }

    // ======================
    // ROOM TYPE CRUD
    // ======================

    @FXML
    private void addRoomType() {
        if (!(staff instanceof Admin)) return;

        try {
            String name = roomTypeNameField.getText().trim();

            if (name.isEmpty()) {
                HotelFXApp.showError("Room Type Error", "Room type name is required.");
                return;
            }

            double price = Double.parseDouble(roomTypePriceField.getText().trim());

            if (price <= 0) {
                HotelFXApp.showError("Room Type Error", "Price must be greater than zero.");
                return;
            }

            for (RoomType type : HotelDatabase.getRoomTypes()) {
                if (type.getName().equalsIgnoreCase(name)) {
                    HotelFXApp.showError("Room Type Error", "This room type already exists.");
                    return;
                }
            }

            Admin admin = (Admin) staff;
            admin.addRoomType(new RoomType(name, price));

            HotelFXApp.showInfo("Success", "Room type added successfully.");
            roomTypeNameField.clear();
            roomTypePriceField.clear();

            refreshRoomTypesCombo();
            viewRoomTypes();

        } catch (NumberFormatException e) {
            HotelFXApp.showError("Room Type Error", "Price must be a valid number.");
        } catch (Exception e) {
            HotelFXApp.showError("Room Type Error", e.getMessage());
        }
    }

    @FXML
    private void viewRoomTypes() {
        StringBuilder sb = new StringBuilder();

        if (HotelDatabase.getRoomTypes().isEmpty()) {
            roomTypeOutput.setText("No room types found.");
            return;
        }

        sb.append("Room Types:\n");
        sb.append("----------------------\n");

        for (RoomType type : HotelDatabase.getRoomTypes()) {
            sb.append("- ")
                    .append(type.getName())
                    .append(" | Price per night: ")
                    .append(type.getPricePerNight())
                    .append("\n");
        }

        roomTypeOutput.setText(sb.toString());
    }

    @FXML
    private void updateRoomType() {
        if (!(staff instanceof Admin)) return;

        String oldName = roomTypeOldNameField.getText().trim();
        String newName = roomTypeNewNameField.getText().trim();

        if (oldName.isEmpty() || newName.isEmpty()) {
            HotelFXApp.showError("Room Type Error", "Old name and new name are required.");
            return;
        }

        boolean found = false;

        for (RoomType type : HotelDatabase.getRoomTypes()) {
            if (type.getName().equalsIgnoreCase(oldName)) {
                found = true;
                break;
            }
        }

        if (!found) {
            HotelFXApp.showError("Room Type Error", "Room type not found.");
            return;
        }

        for (RoomType type : HotelDatabase.getRoomTypes()) {
            if (type.getName().equalsIgnoreCase(newName)) {
                HotelFXApp.showError("Room Type Error", "Another room type already has this name.");
                return;
            }
        }

        Admin admin = (Admin) staff;
        admin.updateRoomType(oldName, newName);

        HotelFXApp.showInfo("Success", "Room type updated successfully.");
        roomTypeOldNameField.clear();
        roomTypeNewNameField.clear();

        refreshRoomTypesCombo();
        viewRoomTypes();
    }

    @FXML
    private void deleteRoomType() {
        if (!(staff instanceof Admin)) return;

        String name = roomTypeDeleteNameField.getText().trim();

        if (name.isEmpty()) {
            HotelFXApp.showError("Room Type Error", "Room type name is required.");
            return;
        }

        RoomType selectedType = null;

        for (RoomType type : HotelDatabase.getRoomTypes()) {
            if (type.getName().equalsIgnoreCase(name)) {
                selectedType = type;
                break;
            }
        }

        if (selectedType == null) {
            HotelFXApp.showError("Room Type Error", "Room type not found.");
            return;
        }

        for (Room room : HotelDatabase.getRooms()) {
            if (room.getType().getName().equalsIgnoreCase(name)) {
                HotelFXApp.showError(
                        "Room Type Error",
                        "Cannot delete this room type because some rooms are using it."
                );
                return;
            }
        }

        Admin admin = (Admin) staff;
        admin.deleteRoomType(selectedType);

        HotelFXApp.showInfo("Success", "Room type deleted successfully.");
        roomTypeDeleteNameField.clear();

        refreshRoomTypesCombo();
        viewRoomTypes();
    }

    @FXML
    private void logout() {
        HotelFXApp.showLogin();
    }

    private void refreshRoomTypesCombo() {
        roomTypeBox.getItems().clear();

        for (RoomType type : HotelDatabase.getRoomTypes()) {
            roomTypeBox.getItems().add(type.getName());
        }

        if (!roomTypeBox.getItems().isEmpty()) {
            roomTypeBox.setValue(roomTypeBox.getItems().get(0));
        }
    }
}