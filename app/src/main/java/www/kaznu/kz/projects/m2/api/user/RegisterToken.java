package www.kaznu.kz.projects.m2.api.user;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

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
import www.kaznu.kz.projects.m2.utils.TinyDB;

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

        Logger.d("Phone: " + phone.replaceAll("[ \\-()]", "") + " and code: " + code);

        this.context = context;

        TinyDB data = new TinyDB(this.context);

        RequestQueue userRequestQueue = Volley.newRequestQueue(context);

        StringRequest userStringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_TOKEN, response -> {

            try {

                final JSONObject root = new JSONObject(response);

                data.putString(SHARED_ACCESS_TOKEN, root.getString("access_token"));
                data.putString(SHARED_TOKEN_TYPE, root.getString("token_type"));
                data.putInt(SHARED_EXPIRES_IN, root.getInt("expires_in"));

                if(listener != null) {
                    listener.onComplete(root.getString("access_token"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "JSON Error: " + e.toString(), Toast.LENGTH_SHORT)
                        .show();
                Logger.d("Response error: " + e.toString());
            }
        }, error -> {
            Logger.d("Response error 2: " + error.toString());
            Toast.makeText(activity.getApplicationContext(), "Connection Error: " + error.toString(), Toast.LENGTH_SHORT)
                    .show();
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
