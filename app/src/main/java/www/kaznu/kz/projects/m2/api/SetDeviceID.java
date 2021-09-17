package www.kaznu.kz.projects.m2.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;

public class SetDeviceID implements Constants {
    private int resultCode;
    private String resultMessage;

    public interface CustomOnLoadListener {
        void onComplete(int resultCode, String resultMessage);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }


    public SetDeviceID(Context context, String token, String auth_token) {
        this.listener = null;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SET_DEVICE_ID.concat("?token=").concat(token), response -> {
            try {
                final JSONObject root = new JSONObject(response);

                resultCode = root.getInt("ResultCode");
                resultMessage = root.getString("ResultMessage");

                if(resultCode == 1) {
                    if(listener != null) {
                        listener.onComplete(resultCode, resultMessage);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("M2TAG", "Response catch: " + e.toString());
            }

        }, error -> {
            Log.d("M2TAG", "Response error: " + error.toString());
            error.printStackTrace();
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
                params.put("Authorization", "bearer " + auth_token);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
