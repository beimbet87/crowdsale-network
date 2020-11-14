package www.kaznu.kz.projects.m2.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Realty {
    private int status;
    private int rentPeriod;
    private int id;
    private int floor;
    private String header;
    private String description;
    private double longitude;
    private double latitude;
    private int realtyType;
    private int roomCount;
    private double cost;
    private double area;
    private double livingSpace;
    private int transactionType;
    private String age;
    private String address;
    private int refCity;
    private int floorBuild;

    public String getBody() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("status", this.getStatus());
            jsonBody.put("rentPeriod", this.getRentPeriod());
            jsonBody.put("id", this.getId());
            jsonBody.put("floor", this.getFloor());
            jsonBody.put("header", this.getHeader());
            jsonBody.put("description", this.getDescription());
            jsonBody.put("longitude", this.getLongitude());
            jsonBody.put("latitude", this.getLatitude());
            jsonBody.put("realtyType", this.getRealtyType());
            jsonBody.put("roomCount", this.getRoomCount());
            jsonBody.put("cost", this.getCost());
            jsonBody.put("area", this.getArea());
            jsonBody.put("livingSpace", this.getLivingSpace());
            jsonBody.put("transactionType", this.getTransactionType());
            jsonBody.put("age", this.getAge());
            jsonBody.put("adress", this.getAddress());
            jsonBody.put("refCity", this.getRefCity());
            jsonBody.put("floorebuild", this.getFloorBuild());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody.toString();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(int rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getRealtyType() {
        return realtyType;
    }

    public void setRealtyType(int realtyType) {
        this.realtyType = realtyType;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(double livingSpace) {
        this.livingSpace = livingSpace;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRefCity() {
        return refCity;
    }

    public void setRefCity(int refCity) {
        this.refCity = refCity;
    }

    public int getFloorBuild() {
        return floorBuild;
    }

    public void setFloorBuild(int floorBuild) {
        this.floorBuild = floorBuild;
    }
}
