package model;

public abstract class Staff {
    private String username;
    private String password;
    private String dateOfBirth;
    private int workingHours;
    public Staff(String username, String password, String dateOfBirth, int workingHours) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.workingHours = workingHours;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if(username != null) {
          this.username = username;
        } else {
            System.out.println("Invalid username. Please provide a non-null value.");
        }
        
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if(password != null && password.length() >= 8) {
          this.password = password;
        } else {
            System.out.println("Invalid password. Please provide a non-null value.");
        }
        
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        if(dateOfBirth != null) {
          this.dateOfBirth = dateOfBirth;
        } else {
            System.out.println("Invalid date of birth. Please provide a non-null value.");
        }
        
    }
    public int getWorkingHours() {
        return workingHours;
    }
    public void setWorkingHours(int workingHours) {
        if(workingHours >= 0) {
          this.workingHours = workingHours;
        } else {
            System.out.println("Invalid working hours. Please provide a non-negative value.");
        }
        
    }
    
    public void ViewGuest(){
        // code to view guest information
        System.out.println("Viewing guest information...");
    }
    public void ViewRoom(){
        // code to view room information
        System.out.println("Viewing room information...");
    }
    public void ViewBooking(){
        // code to view booking information
        System.out.println("Viewing booking information...");
    }
    public abstract void performActions();


    


}
