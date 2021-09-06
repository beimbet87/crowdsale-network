package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Filter {
    private ArrayList<Polygons> polygons;
    private ArrayList<Integer> roomCount;
    private ArrayList<Integer> offersOptionsId;
    private ArrayList<Integer> propertiesId;
    private int realtyType = -1;
    private int transactionType = -1;
    private int rentPeriod = -1;
    private double costLowerLimit = -1.0;
    private double costUpperLimit = -1.0;
    private String startDate = null;
    private String endDate = null;
    private int offset = -1;
    private int limit = -1;
    private int refUser = -1;
    private String cityName;
    private String countryName;
    private int refCity;
    private int refCountry;
    private double floreFrom;
    private double floreTo;
    private double areaFrom;
    private double areaTo;
    private double livingSpaceFrom;
    private double livingSpaceTo;
    private double kitchenSpaceFrom;
    private double kitchenSpaceTo;
    private double plotAreaFrom;
    private double plotAreaTo;
    private double adjTerritoryFrom;
    private double energyConsTo;
    private double industrialPremisesFrom;
    private double warehouseFrom;
    private double officeFrom;

    public ArrayList<Polygons> getPolygons() {
        return polygons;
    }

    public String getBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            if (this.getPolygons().size() > 0) {
                JSONArray jsonPolygons = new JSONArray();
                for (int i = 0; i < this.getPolygons().size(); i++) {
                    JSONObject elements = new JSONObject();
                    elements.put("longitude", this.getPolygons().get(i).getLongitude());
                    elements.put("latitude", this.getPolygons().get(i).getLatitude());
                    jsonPolygons.put(i, elements);
                }
                jsonBody.put("polygone", jsonPolygons);
            }

            if(this.transactionType > 0)
                jsonBody.put("transactionType", this.getTransactionType());
            if(this.getRealtyType() > 0)
                jsonBody.put("realtyType", this.getRealtyType());
            if (this.getRoomCount().size() > 0) {
                JSONArray rooms = new JSONArray();
                for(int i = 0; i < this.getRoomCount().size(); i++) {
                    rooms.put(this.getRoomCount().get(i));
                }
                jsonBody.put("roomCount", rooms);
            }
            if(this.getCostLowerLimit() > 0.0)
                jsonBody.put("costLowerLimit", this.getCostLowerLimit());
            if(this.getCostUpperLimit() > 0.0)
                jsonBody.put("costUpperLimit", this.getCostUpperLimit());
            if (this.getOffersOptionsId().size() > 0) {
                JSONArray offersOption = new JSONArray();
                for(int i = 0; i < this.getOffersOptionsId().size(); i++) {
                    offersOption.put(this.getOffersOptionsId().get(i));
                }
                jsonBody.put("offersOptionsId", offersOption);
            }
            if (this.getPropertiesId().size() > 0) {
                JSONArray properties = new JSONArray();
                for(int i = 0; i < this.getPropertiesId().size(); i++) {
                    properties.put(this.getPropertiesId().get(i));
                }
                jsonBody.put("propertiesID", properties);
            }
            if(this.getRentPeriod() > 0)
                jsonBody.put("rentPeriod", this.getRentPeriod());
            if(this.getStartDate() != null)
                jsonBody.put("startTime", this.getStartDate());
            if(this.getEndDate() != null)
                jsonBody.put("endTime", this.getEndDate());
            if(this.getOffset() > 0)
                jsonBody.put("offset", this.getOffset());
            if(this.getLimit() > 0)
                jsonBody.put("limit", this.getLimit());
            if(this.getRefUser() > 0)
                jsonBody.put("refUser", this.getRefUser());

            jsonBody.put("cityName", this.getCityName());
            jsonBody.put("countryName", this.getCountryName());

            if(this.getRefCity() > 0)
                jsonBody.put("refCity", this.getRefCity());

            if(this.getRefCountry() > 0)
                jsonBody.put("refCountry", this.getRefCountry());

            if(this.getFloreFrom() > 0.0)
                jsonBody.put("floreFrom", this.getFloreFrom());

            if(this.getFloreTo() > 0.0)
                jsonBody.put("floreTo", this.getFloreTo());

            if(this.getAreaFrom() > 0.0)
                jsonBody.put("areaFrom", this.getAreaFrom());

            if(this.getAreaTo() > 0.0)
                jsonBody.put("areaTo", this.getAreaTo());

            if(this.getLivingSpaceFrom() > 0.0)
                jsonBody.put("livingSpaceFrom", this.getLivingSpaceFrom());

            if(this.getLivingSpaceTo() > 0.0)
                jsonBody.put("livingSpaceTo", this.getLivingSpaceTo());

            if(this.getKitchenSpaceFrom() > 0.0)
                jsonBody.put("kitchenSpaceFrom", this.getKitchenSpaceFrom());

            if(this.getKitchenSpaceTo() > 0.0)
                jsonBody.put("kitchenSpaceTo", this.getKitchenSpaceTo());

            if(this.getPlotAreaFrom() > 0.0)
                jsonBody.put("plotAreaFrom", this.getPlotAreaFrom());

            if(this.getPlotAreaTo() > 0.0)
                jsonBody.put("plotAreaTo", this.getPlotAreaTo());

            if(this.getAdjTerritoryFrom() > 0.0)
                jsonBody.put("adjTerritoryFrom", this.getAdjTerritoryFrom());

            if(this.getEnergyConsTo() > 0.0)
                jsonBody.put("energyConsTo", this.getEnergyConsTo());

            if(this.getIndustrialPremisesFrom() > 0.0)
                jsonBody.put("industrialPremisesFrom", this.getIndustrialPremisesFrom());

            if(this.getWarehouseFrom() > 0.0)
                jsonBody.put("warehouseFrom", this.getWarehouseFrom());

            if(this.getOfficeFrom() > 0.0)
                jsonBody.put("officeFrom", this.getOfficeFrom());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Filter Error: " + e);
        }

        return jsonBody.toString();
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

    public int getRefCity() {
        return refCity;
    }

    public void setRefCity(int refCity) {
        this.refCity = refCity;
    }

    public int getRefCountry() {
        return refCountry;
    }

    public void setRefCountry(int refCountry) {
        this.refCountry = refCountry;
    }

    public double getFloreFrom() {
        return floreFrom;
    }

    public void setFloreFrom(double floreFrom) {
        this.floreFrom = floreFrom;
    }

    public double getFloreTo() {
        return floreTo;
    }

    public void setFloreTo(double floreTo) {
        this.floreTo = floreTo;
    }

    public double getAreaFrom() {
        return areaFrom;
    }

    public void setAreaFrom(double areaFrom) {
        this.areaFrom = areaFrom;
    }

    public double getAreaTo() {
        return areaTo;
    }

    public void setAreaTo(double areaTo) {
        this.areaTo = areaTo;
    }

    public double getLivingSpaceFrom() {
        return livingSpaceFrom;
    }

    public void setLivingSpaceFrom(double livingSpaceFrom) {
        this.livingSpaceFrom = livingSpaceFrom;
    }

    public double getLivingSpaceTo() {
        return livingSpaceTo;
    }

    public void setLivingSpaceTo(double livingSpaceTo) {
        this.livingSpaceTo = livingSpaceTo;
    }

    public double getKitchenSpaceFrom() {
        return kitchenSpaceFrom;
    }

    public void setKitchenSpaceFrom(double kitchenSpaceFrom) {
        this.kitchenSpaceFrom = kitchenSpaceFrom;
    }

    public double getKitchenSpaceTo() {
        return kitchenSpaceTo;
    }

    public void setKitchenSpaceTo(double kitchenSpaceTo) {
        this.kitchenSpaceTo = kitchenSpaceTo;
    }

    public double getPlotAreaFrom() {
        return plotAreaFrom;
    }

    public void setPlotAreaFrom(double plotAreaFrom) {
        this.plotAreaFrom = plotAreaFrom;
    }

    public double getPlotAreaTo() {
        return plotAreaTo;
    }

    public void setPlotAreaTo(double plotAreaTo) {
        this.plotAreaTo = plotAreaTo;
    }

    public double getAdjTerritoryFrom() {
        return adjTerritoryFrom;
    }

    public void setAdjTerritoryFrom(double adjTerritoryFrom) {
        this.adjTerritoryFrom = adjTerritoryFrom;
    }

    public double getEnergyConsTo() {
        return energyConsTo;
    }

    public void setEnergyConsTo(double energyConsTo) {
        this.energyConsTo = energyConsTo;
    }

    public double getIndustrialPremisesFrom() {
        return industrialPremisesFrom;
    }

    public void setIndustrialPremisesFrom(double industrialPremisesFrom) {
        this.industrialPremisesFrom = industrialPremisesFrom;
    }

    public double getWarehouseFrom() {
        return warehouseFrom;
    }

    public void setWarehouseFrom(double warehouseFrom) {
        this.warehouseFrom = warehouseFrom;
    }

    public double getOfficeFrom() {
        return officeFrom;
    }

    public void setOfficeFrom(double officeFrom) {
        this.officeFrom = officeFrom;
    }

    public void setPolygons(ArrayList<Polygons> polygons) {
        this.polygons = polygons;
    }

    public ArrayList<Integer> getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(ArrayList<Integer> roomCount) {
        this.roomCount = roomCount;
    }

    public ArrayList<Integer> getOffersOptionsId() {
        return offersOptionsId;
    }

    public void setOffersOptionsId(ArrayList<Integer> offersOptionsId) {
        this.offersOptionsId = offersOptionsId;
    }

    public ArrayList<Integer> getPropertiesId() {
        return propertiesId;
    }

    public void setPropertiesId(ArrayList<Integer> propertiesId) {
        this.propertiesId = propertiesId;
    }

    public int getRealtyType() {
        return realtyType;
    }

    public void setRealtyType(int realtyType) {
        this.realtyType = realtyType;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public int getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(int rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public double getCostLowerLimit() {
        return costLowerLimit;
    }

    public void setCostLowerLimit(double costLowerLimit) {
        this.costLowerLimit = costLowerLimit;
    }

    public double getCostUpperLimit() {
        return costUpperLimit;
    }

    public void setCostUpperLimit(double costUpperLimit) {
        this.costUpperLimit = costUpperLimit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRefUser() {
        return refUser;
    }

    public void setRefUser(int refUser) {
        this.refUser = refUser;
    }

    public Filter() {
        polygons = new ArrayList<>();
        roomCount = new ArrayList<>();
        offersOptionsId = new ArrayList<>();
        propertiesId = new ArrayList<>();
    }
}
