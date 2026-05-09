package model;

import database.HotelDatabase;
import exceptions.InvalidDateException;
import exceptions.InvalidPriceException;
import exceptions.InvalidReservationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class HotelFXApp extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        try {
            HotelDatabase.initializeData();
            showLogin();
        } catch (InvalidPriceException | InvalidDateException | InvalidReservationException e) {
            showError("Startup Error", e.getMessage());
        }
    }

    public static void showLogin() {
        try {
            Parent root = FXMLLoader.load(HotelFXApp.class.getResource("/fxml/Login.fxml"));

            Scene scene = new Scene(root, 950, 650);
            applyCss(scene);

            mainStage.setTitle("Hotel Reservation System");
            mainStage.setScene(scene);
            mainStage.show();

        } catch (Exception e) {
            showError("FXML Error", e.getMessage());
        }
    }

    public static void showGuestDashboard(Guest guest) {
        try {
            FXMLLoader loader = new FXMLLoader(HotelFXApp.class.getResource("/fxml/GuestDashboard.fxml"));
            Parent root = loader.load();

            GuestDashboardController controller = loader.getController();
            controller.setGuest(guest);

            Scene scene = new Scene(root, 1100, 700);
            applyCss(scene);

            mainStage.setTitle("Guest Dashboard");
            mainStage.setScene(scene);

        } catch (Exception e) {
            showError("FXML Error", e.getMessage());
        }
    }

    public static void showStaffDashboard(Staff staff) {
        try {
            FXMLLoader loader = new FXMLLoader(HotelFXApp.class.getResource("/fxml/StaffDashboard.fxml"));
            Parent root = loader.load();

            StaffDashboardController controller = loader.getController();
            controller.setStaff(staff);

            Scene scene = new Scene(root, 1150, 720);
            applyCss(scene);

            mainStage.setTitle("Staff Dashboard");
            mainStage.setScene(scene);

        } catch (Exception e) {
            showError("FXML Error", e.getMessage());
        }
    }

    private static void applyCss(Scene scene) {
        if (HotelFXApp.class.getResource("/css/style.css") != null) {
            scene.getStylesheets().add(
                    HotelFXApp.class.getResource("/css/style.css").toExternalForm()
            );
        }
    }

    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message == null ? "Unknown error" : message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}