package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationStep1 {

    String countryCode;
    String regIdentity;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    int userId;

    public RegistrationStep1() {

    }

    public String getBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("countryCode", this.getCountryCode());
            jsonBody.put("regIdentity", this.getRegIdentity());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Registration Step 1 Error: " + e);
        }

        return jsonBody.toString();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegIdentity() {
        return regIdentity;
    }

    public void setRegIdentity(String regIdentity) {
        this.regIdentity = regIdentity;
    }
}
