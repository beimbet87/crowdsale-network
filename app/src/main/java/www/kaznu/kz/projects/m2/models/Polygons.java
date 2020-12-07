package www.kaznu.kz.projects.m2.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Polygons implements Parcelable {
    private Double longitude;
    private Double latitude;

    public Polygons() {
        longitude = 0.0;
        latitude = 0.0;
    }

    protected Polygons(Parcel in) {
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
    }

    public static final Creator<Polygons> CREATOR = new Creator<Polygons>() {
        @Override
        public Polygons createFromParcel(Parcel in) {
            return new Polygons(in);
        }

        @Override
        public Polygons[] newArray(int size) {
            return new Polygons[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
    }
}
