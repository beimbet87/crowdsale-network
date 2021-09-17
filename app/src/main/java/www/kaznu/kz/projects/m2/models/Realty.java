package www.kaznu.kz.projects.m2.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Realty implements Serializable {
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
    private int refCountry;
    private int floorBuild;
    private String cityName;
    private String countryName;
    private double kitchenSpace;
    private double plotArea;
    private double adjTerritory;
    private double energyCons;
    private double industrialPremises;
    private double warehouse;
    private double office;


    public int getRefCountry() {
        return refCountry;
    }

    public void setRefCountry(int refCountry) {
        this.refCountry = refCountry;
    }

    public String getBody() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("status", this.getStatus());
            jsonBody.put("rentPeriod", this.getRentPeriod());
            jsonBody.put("id", this.getId());
            jsonBody.put("floor", this.getFloor());
            jsonBody.put("floorebuild", this.getFloorBuild());
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
            jsonBody.put("refСountry", this.getRefCity()); //
            jsonBody.put("cityName", this.getRefCountry());
            jsonBody.put("countryName", this.getCountryName());
            jsonBody.put("kitchenSpace", this.getKitchenSpace()); //
            jsonBody.put("plotArea", this.getPlotArea());
            jsonBody.put("adjTerritory", this.getAdjTerritory());
            jsonBody.put("energyCons", this.getEnergyCons());
            jsonBody.put("industrialPremises", this.getIndustrialPremises());
            jsonBody.put("warehouse", this.getWarehouse());
            jsonBody.put("office", this.getOffice());

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getKitchenSpace() {
        return kitchenSpace;
    }

    public void setKitchenSpace(double kitchenSpace) {
        this.kitchenSpace = kitchenSpace;
    }

    public double getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(double plotArea) {
        this.plotArea = plotArea;
    }

    public double getAdjTerritory() {
        return adjTerritory;
    }

    public void setAdjTerritory(double adjTerritory) {
        this.adjTerritory = adjTerritory;
    }

    public double getEnergyCons() {
        return energyCons;
    }

    public void setEnergyCons(double energyCons) {
        this.energyCons = energyCons;
    }

    public double getIndustrialPremises() {
        return industrialPremises;
    }

    public void setIndustrialPremises(double industrialPremises) {
        this.industrialPremises = industrialPremises;
    }

    public double getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(double warehouse) {
        this.warehouse = warehouse;
    }

    public double getOffice() {
        return office;
    }

    public void setOffice(double office) {
        this.office = office;
    }
}
