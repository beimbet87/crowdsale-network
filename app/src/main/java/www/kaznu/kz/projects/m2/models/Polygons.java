package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Polygons {
    private Double longitude;
    private Double latitude;

    public Polygons() {
        longitude = 0.0;
        latitude = 0.0;
    }

    public String getBody() {
        JSONObject jsonBody = new JSONObject();

        try {

            if(!this.getLatitude().isNaN()) {
                jsonBody.put("latitude", this.getLatitude());
            }
            if(!this.getLongitude().isNaN()) {
                jsonBody.put("realtyType", this.getLongitude());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Polygon Error: " + e);
        }

        return jsonBody.toString();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
