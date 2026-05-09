package model;

import database.HotelDatabase;
import enums.Gender;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class LoginController {

    @FXML private ComboBox<String> roleBox;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private TextField regUsernameField;
    @FXML private PasswordField regPasswordField;
    @FXML private TextField regAddressField;
    @FXML private ComboBox<String> regGenderBox;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("Guest", "Receptionist", "Admin");
        roleBox.setValue("Guest");

        regGenderBox.getItems().addAll("MALE", "FEMALE");
        regGenderBox.setValue("MALE");
    }

    @FXML
    private void login() {
        String role = roleBox.getValue();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            HotelFXApp.showError("Login Error", "Username and password are required.");
            return;
        }

        if (role.equals("Guest")) {
            for (Guest guest : HotelDatabase.getGuests()) {
                if (guest.getUsername().equals(username)) {
                    try {
                        guest.login(username, password);
                        HotelFXApp.showGuestDashboard(guest);
                        return;
                    } catch (Exception e) {
                        HotelFXApp.showError("Login Error", e.getMessage());
                        return;
                    }
                }
            }
        }

        if (role.equals("Receptionist")) {
            for (Receptionist receptionist : HotelDatabase.getReceptionists()) {
                if (receptionist.getUsername().equals(username)
                        && receptionist.getPassword().equals(password)) {
                    HotelFXApp.showStaffDashboard(receptionist);
                    return;
                }
            }
        }

        if (role.equals("Admin")) {
            for (Admin admin : HotelDatabase.getAdmins()) {
                if (admin.getUsername().equals(username)
                        && admin.getPassword().equals(password)) {
                    HotelFXApp.showStaffDashboard(admin);
                    return;
                }
            }
        }

        HotelFXApp.showError("Login Error", "Invalid credentials.");
    }

    @FXML
    private void registerGuest() {
        String username = regUsernameField.getText().trim();
        String password = regPasswordField.getText().trim();
        String address = regAddressField.getText().trim();
        String genderValue = regGenderBox.getValue();

        if (username.isEmpty() || password.isEmpty() || address.isEmpty()) {
            HotelFXApp.showError("Register Error", "All registration fields are required.");
            return;
        }

        Gender gender = genderValue.equals("MALE") ? Gender.MALE : Gender.FEMALE;

        Guest guest = new Guest(
                username,
                password,
                LocalDate.of(2000, 1, 1),
                1000,
                address,
                gender
        );

        try {
            guest.register(username, password);
            HotelFXApp.showInfo("Success", "Guest registered successfully.");
            HotelFXApp.showGuestDashboard(guest);
        } catch (Exception e) {
            HotelFXApp.showError("Register Error", e.getMessage());
        }
    }
}