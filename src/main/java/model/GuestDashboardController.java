package model;

import database.HotelDatabase;
import enums.PaymentMethod;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;

public class GuestDashboardController {

    private Guest guest;

    private final ArrayList<Room> displayedRooms = new ArrayList<>();
    private final ArrayList<Reservation> displayedReservations = new ArrayList<>();
    private final ArrayList<Invoice> displayedInvoices = new ArrayList<>();

    @FXML private Label welcomeLabel;
    @FXML private Label profileLabel;

    @FXML private DatePicker checkInPicker;
    @FXML private DatePicker checkOutPicker;
    @FXML private ListView<String> roomsList;

    // Room filter controls
    @FXML private ComboBox<String> roomTypeFilterBox;
    @FXML private TextField maxPriceField;
    @FXML private ComboBox<String> amenityFilterBox;

    @FXML private ListView<String> reservationsList;

    @FXML private ListView<String> invoicesList;
    @FXML private TextField paymentAmountField;
    @FXML private ComboBox<String> paymentMethodBox;
    @FXML private TextField paymentEmailField;
    @FXML private PasswordField paymentPasswordField;

    public void setGuest(Guest guest) {
        this.guest = guest;

        welcomeLabel.setText("Welcome, " + guest.getUsername());

        paymentMethodBox.getItems().addAll("CASH", "CARD", "ONLINE");
        paymentMethodBox.setValue("CASH");

        setupFilters();

        showProfile();
        refreshRooms();
        refreshReservations();
        refreshInvoices();
    }

    private void setupFilters() {
        roomTypeFilterBox.getItems().clear();
        amenityFilterBox.getItems().clear();

        roomTypeFilterBox.getItems().add("All Types");
        amenityFilterBox.getItems().add("All Amenities");

        for (RoomType type : HotelDatabase.getRoomTypes()) {
            roomTypeFilterBox.getItems().add(type.getName());
        }

        for (Amenity amenity : HotelDatabase.getAmenities()) {
            amenityFilterBox.getItems().add(amenity.getName());
        }

        roomTypeFilterBox.setValue("All Types");
        amenityFilterBox.setValue("All Amenities");
    }

    private void showProfile() {
        profileLabel.setText(
                "Username: " + guest.getUsername() + "\n" +
                        "Balance: " + guest.getBalance() + "\n" +
                        "Address: " + guest.getAddress() + "\n" +
                        "Gender: " + guest.getGender()
        );
    }

    @FXML
    private void refreshRooms() {
        displayedRooms.clear();
        roomsList.getItems().clear();

        LocalDate in = checkInPicker.getValue();
        LocalDate out = checkOutPicker.getValue();

        String selectedType = roomTypeFilterBox.getValue();
        String selectedAmenity = amenityFilterBox.getValue();

        double maxPrice = -1;

        if (maxPriceField.getText() != null && !maxPriceField.getText().trim().isEmpty()) {
            try {
                maxPrice = Double.parseDouble(maxPriceField.getText().trim());

                if (maxPrice < 0) {
                    HotelFXApp.showError("Filter Error", "Maximum price cannot be negative.");
                    return;
                }

            } catch (NumberFormatException e) {
                HotelFXApp.showError("Filter Error", "Maximum price must be a valid number.");
                return;
            }
        }

        for (Room room : HotelDatabase.getRooms()) {
            try {
                if (in != null && out != null && !room.isAvailable(in, out)) {
                    continue;
                }

                if (selectedType != null && !selectedType.equals("All Types")) {
                    if (!room.getType().getName().equalsIgnoreCase(selectedType)) {
                        continue;
                    }
                }

                if (maxPrice >= 0 && room.getType().getPricePerNight() > maxPrice) {
                    continue;
                }

                if (selectedAmenity != null && !selectedAmenity.equals("All Amenities")) {
                    boolean hasAmenity = false;

                    for (Amenity amenity : room.getAmenities()) {
                        if (amenity.getName().equalsIgnoreCase(selectedAmenity)) {
                            hasAmenity = true;
                            break;
                        }
                    }

                    if (!hasAmenity) {
                        continue;
                    }
                }

                displayedRooms.add(room);
                roomsList.getItems().add(roomText(room));

            } catch (Exception e) {
                HotelFXApp.showError("Room Error", e.getMessage());
            }
        }

        if (displayedRooms.isEmpty()) {
            roomsList.getItems().add("No rooms match the selected filters.");
        }
    }

    @FXML
    private void resetFilters() {
        checkInPicker.setValue(null);
        checkOutPicker.setValue(null);
        roomTypeFilterBox.setValue("All Types");
        amenityFilterBox.setValue("All Amenities");
        maxPriceField.clear();

        refreshRooms();
    }

    @FXML
    private void makeReservation() {
        int index = roomsList.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            HotelFXApp.showError("Reservation Error", "Choose a room first.");
            return;
        }

        if (displayedRooms.isEmpty() || index >= displayedRooms.size()) {
            HotelFXApp.showError("Reservation Error", "Please select a valid room.");
            return;
        }

        LocalDate in = checkInPicker.getValue();
        LocalDate out = checkOutPicker.getValue();

        if (in == null || out == null) {
            HotelFXApp.showError("Reservation Error", "Choose check-in and check-out dates.");
            return;
        }

        try {
            Room selectedRoom = displayedRooms.get(index);
            Reservation reservation = guest.makeReservation(selectedRoom, in, out);

            HotelFXApp.showInfo("Success", "Reservation created. Status: " + reservation.getStatus());

            refreshRooms();
            refreshReservations();

        } catch (Exception e) {
            HotelFXApp.showError("Reservation Error", e.getMessage());
        }
    }

    @FXML
    private void refreshReservations() {
        displayedReservations.clear();
        reservationsList.getItems().clear();

        if (guest.viewReservations().isEmpty()) {
            reservationsList.getItems().add("No reservations found.");
            return;
        }

        int counter = 1;

        for (Reservation reservation : guest.viewReservations()) {
            displayedReservations.add(reservation);
            reservationsList.getItems().add(reservationHistoryText(reservation, counter));
            counter++;
        }
    }

    @FXML
    private void cancelReservation() {
        int index = reservationsList.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            HotelFXApp.showError("Cancel Error", "Choose a reservation first.");
            return;
        }

        if (displayedReservations.isEmpty() || index >= displayedReservations.size()) {
            HotelFXApp.showError("Cancel Error", "Please select a valid reservation.");
            return;
        }

        try {
            Reservation selected = displayedReservations.get(index);
            guest.cancelReservation(selected);

            HotelFXApp.showInfo("Success", "Reservation cancelled.");
            refreshReservations();
            refreshRooms();

        } catch (Exception e) {
            HotelFXApp.showError("Cancel Error", e.getMessage());
        }
    }

    @FXML
    private void refreshInvoices() {
        displayedInvoices.clear();
        invoicesList.getItems().clear();

        if (guest.getInvoices().isEmpty()) {
            invoicesList.getItems().add("No invoices found.");
            return;
        }

        for (Invoice invoice : guest.getInvoices()) {
            displayedInvoices.add(invoice);
            invoicesList.getItems().add(invoiceText(invoice));
        }
    }

    @FXML
    private void payInvoice() {
        int index = invoicesList.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            HotelFXApp.showError("Payment Error", "Choose an invoice first.");
            return;
        }

        if (displayedInvoices.isEmpty() || index >= displayedInvoices.size()) {
            HotelFXApp.showError("Payment Error", "Please select a valid invoice.");
            return;
        }

        try {
            double amount = Double.parseDouble(paymentAmountField.getText().trim());
            PaymentMethod method = PaymentMethod.valueOf(paymentMethodBox.getValue());

            Invoice invoice = displayedInvoices.get(index);

            String email = paymentEmailField.getText().trim();
            String password = paymentPasswordField.getText().trim();

            try {
                Method m = Invoice.class.getMethod(
                        "pay",
                        double.class,
                        PaymentMethod.class,
                        String.class,
                        String.class
                );

                Object result = m.invoke(invoice, amount, method, email, password);

                if (result instanceof Boolean && !(Boolean) result) {
                    HotelFXApp.showError("Payment Error", "Payment failed.");
                    return;
                }

            } catch (NoSuchMethodException e) {
                invoice.pay(amount, method);
            }

            HotelFXApp.showInfo("Success", "Payment processed.");
            showProfile();
            refreshInvoices();

        } catch (NumberFormatException e) {
            HotelFXApp.showError("Payment Error", "Amount must be a valid number.");
        } catch (Exception e) {
            HotelFXApp.showError("Payment Error", e.getMessage());
        }
    }

    @FXML
    private void logout() {
        HotelFXApp.showLogin();
    }

    private String roomText(Room room) {
        return "Room " + room.getRoomNumber()
                + " | Type: " + room.getType().getName()
                + " | Price: " + room.getType().getPricePerNight()
                + " | Amenities: " + amenitiesText(room);
    }

    private String amenitiesText(Room room) {
        if (room.getAmenities().isEmpty()) {
            return "None";
        }

        StringBuilder sb = new StringBuilder();

        for (Amenity amenity : room.getAmenities()) {
            sb.append(amenity.getName()).append(", ");
        }

        return sb.substring(0, sb.length() - 2);
    }

    private String reservationHistoryText(Reservation reservation, int number) {
        String totalText;

        try {
            totalText = String.valueOf(reservation.calculateTotal());
        } catch (Exception e) {
            totalText = "Cannot calculate";
        }

        return "Reservation #" + number + "\n"
                + "Guest: " + reservation.getGuest().getUsername() + "\n"
                + "Room Number: " + reservation.getRoom().getRoomNumber() + "\n"
                + "Room Type: " + reservation.getRoom().getType().getName() + "\n"
                + "Check-in: " + reservation.getCheckIn() + "\n"
                + "Check-out: " + reservation.getCheckOut() + "\n"
                + "Status: " + reservation.getStatus() + "\n"
                + "Estimated Total: " + totalText + "\n"
                + "----------------------------------------";
    }

    private String invoiceText(Invoice invoice) {
        return "Room " + invoice.getReservation().getRoom().getRoomNumber()
                + " | Total: " + invoice.getTotalAmount()
                + " | Paid: " + invoice.getPaidAmount()
                + " | Status: " + (invoice.isPaid() ? "PAID" : "PENDING");
    }
}