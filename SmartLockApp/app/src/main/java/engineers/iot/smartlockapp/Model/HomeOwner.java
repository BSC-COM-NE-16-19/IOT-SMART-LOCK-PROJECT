package engineers.iot.smartlockapp.Model;

public class HomeOwner {

    private String username, code, phoneNumber;

    public HomeOwner(String username, String code, String phoneNumber) {
        this.username = username;
        this.code = code;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
