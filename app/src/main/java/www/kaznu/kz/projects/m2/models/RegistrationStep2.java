package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationStep2 {

    String userIdentity;
    int affirmationCode;

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public int getAffirmationCode() {
        return affirmationCode;
    }

    public void setAffirmationCode(int affirmationCode) {
        this.affirmationCode = affirmationCode;
    }

    public RegistrationStep2() {

    }

    public String getBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("affirmationCode", this.getAffirmationCode());
            jsonBody.put("useridentity", this.getUserIdentity());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Registration Step 2 Error: " + e);
        }

        return jsonBody.toString();
    }

}
