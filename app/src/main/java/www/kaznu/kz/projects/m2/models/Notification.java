package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Notification {
    private int type;
    private String body;
    private String deviceId;
    private String applicationId;
    private String senderId;

    public String getNotificationBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("applicationID", this.getApplicationId());
            jsonBody.put("senderId", this.getSenderId());
            jsonBody.put("Type", this.getType());
            jsonBody.put("body", this.getBody());
            jsonBody.put("deviceId", this.getDeviceId());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Message Error: " + e);
        }

        return jsonBody.toString();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
