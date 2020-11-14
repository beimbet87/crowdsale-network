package www.kaznu.kz.projects.m2.api.user;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class Registration implements Constants {

    Context context;
    private int userID;
    private int resultCode;
    private String resultMessage;

    public interface CustomOnLoadListener {
        void onComplete(int resultCode, String resultMessage, int userID);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public Registration(Context context, Activity activity, EditText countryCode, EditText phoneNumber) {

        this.context = context;

        Logger Log = new Logger(context, TAG);

        RequestQueue userRequestQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("countryCode", countryCode.getText().toString());
            jsonBody.put("regIdentity", phoneNumber.getText().toString().replaceAll("[ \\-()]", ""));
            SharedPreferences userPreferences = activity.getSharedPreferences("M2_REG_INFO", 0);
            SharedPreferences.Editor editor = userPreferences.edit();
            editor.putString("countryCode", countryCode.getText().toString());
            editor.putString("regIdentity", countryCode.getText().toString() + phoneNumber.getText().toString());
            editor.apply();
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PHONE_REG, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        final JSONObject root = new JSONObject(response);

                        userID = root.getInt("userId");
                        resultCode = root.getInt("ResultCode");
                        resultMessage = root.getString("ResultMessage");

                        Log.d(String.valueOf(userID));

                        if(listener != null) {
                            listener.onComplete(resultCode, resultMessage, userID);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(error.toString());
                    error.printStackTrace();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("countryCode", countryCode.getText().toString());
                    params.put("regIdentity", phoneNumber.getText().toString().replaceAll("[ \\-()]", ""));
                    Log.d(countryCode.getText().toString());
                    Log.d(phoneNumber.getText().toString().replaceAll("[ \\-()]", ""));
                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            userRequestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
