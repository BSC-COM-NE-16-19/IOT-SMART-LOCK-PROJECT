package engineers.iot.smartlockapp.Model;

public class User {
    private String fName, surname,permission;

    public User() {

    }

    public User(String firstName, String lastName, String permission) {
        this.fName = firstName;
        this.surname = lastName;
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
