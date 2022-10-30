package Model;

//station class
public class Station {

    String stationName;
    String address;
    String email;
    String password;
    String stationUsername;
    String oUsername;
    String id;

    public Station() {
    }

    public Station(String stationName, String address) {
        this.stationName = stationName;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationUsername() {
        return stationUsername;
    }

    public void setStationUsername(String stationUsername) {
        this.stationUsername = stationUsername;
    }

    public String getoUsername() {
        return oUsername;
    }

    public void setoUsername(String oUsername) {
        this.oUsername = oUsername;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
