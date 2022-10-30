package Model;

//owner class
public class Owner {

    String username;
    String vehicleNo;
    String fuelType;
    String ownerId;

    public Owner() {
    }

    public Owner(String username, String vehicleNo, String fuelType) {
        this.username = username;
        this.vehicleNo = vehicleNo;
        this.fuelType = fuelType;
    }

    public String getUsername() {
        return username;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
