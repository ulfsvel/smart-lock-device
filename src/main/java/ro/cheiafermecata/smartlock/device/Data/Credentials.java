package ro.cheiafermecata.smartlock.device.Data;

public class Credentials {

    private String username;

    private String device;

    private String password;

    public Credentials(String username, String device, String password) {
        this.username = username;
        this.device = device;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getDevice() {
        return device;
    }

    public String getPassword() {
        return password;
    }
}
