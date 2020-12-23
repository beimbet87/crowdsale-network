package www.kaznu.kz.projects.m2.api.user;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class RegisterToken implements Constants {

    Context context;

    public interface CustomOnLoadListener {
        void onComplete(String token);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public RegisterToken(Context context, Activity activity, String phone, String code) {

        this.context = context;

        RequestQueue userRequestQueue = Volley.newRequestQueue(context);

        StringRequest userStringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_TOKEN, response -> {
            SharedPreferences tokenPreferences = activity.getSharedPreferences("M2_TOKEN", 0);
            SharedPreferences.Editor editor = tokenPreferences.edit();
            try {

                final JSONObject root = new JSONObject(response);
                editor.putString("access_token", root.getString("access_token"));
                editor.putString("token_type", root.getString("token_type"));
                editor.putInt("expires_in", root.getInt("expires_in"));
                editor.apply();

                if(listener != null) {
                    listener.onComplete(root.getString("access_token"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Logger.d("Response error: " + e.toString());
            }
        }, error -> {
            Logger.d("Response error 2: " + error.toString());
            VolleyLog.d("M2TAG", error.getMessage());
            error.printStackTrace();
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "password");
                params.put("username", phone.replaceAll("[ \\-()]", ""));
                params.put("password", code);

                return params;
            }
        };

        userRequestQueue.add(userStringRequest);
    }
}
