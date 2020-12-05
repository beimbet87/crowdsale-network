package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import com.google.gson.JsonArray;

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
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Filter Error: " + e);
        }

        return jsonBody.toString();
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
