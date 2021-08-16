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
import www.kaznu.kz.projects.m2.models.RegistrationStep1;
import www.kaznu.kz.projects.m2.utils.Logger;

public class RegistrationAuth implements Constants {

    Context context;
    private int userID;
    private int resultCode;
    private String resultMessage;

    public interface CustomOnLoadListener {
        void onComplete(int resultCode, String resultMessage, int userId);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public RegistrationAuth(Context context, Activity activity, RegistrationStep1 data, String token) {

        this.context = context;

        RequestQueue userRequestQueue = Volley.newRequestQueue(context);
        final String requestBody = data.getBody();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRATION_AUTH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);

                    userID = root.getInt("userId");
                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

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
                Logger.d(error.toString());
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
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", "bearer " + token);
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
    }
}
