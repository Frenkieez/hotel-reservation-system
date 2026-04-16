package model;

public class Receptionist extends Staff {
    public Receptionist(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, workingHours);
    }
    
    @Override
    public void performActions() {
        // code to perform receptionist-specific actions
        System.out.println("Performing receptionist actions...");
    }

    public void checkInGuest(){
        System.out.println("Checking in a guest...");
    }
    public void checkOutGuest(){
            System.out.println("Checking out a guest...");
        }


}
