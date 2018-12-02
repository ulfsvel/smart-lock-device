package ro.cheiafermecata.smartlock.device.Data;

public class Credentials {

    private String username;

    private Long device;

    private String password;

    public Credentials(){

    }

    public Credentials(String username, Long device, String password) {
        this.username = username;
        this.device = device;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Long getDevice() {
        return device;
    }

    public String getPassword() {
        return password;
    }

    public void setDevice(Long device) {
        this.device = device;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
