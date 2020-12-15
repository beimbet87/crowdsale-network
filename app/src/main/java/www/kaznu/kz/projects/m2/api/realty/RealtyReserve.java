package www.kaznu.kz.projects.m2.api.realty;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;

public class RealtyReserve implements Constants {
    private int realtyId;
    private int resultCode;
    private String resultMessage;
    private Context context;

    public interface CustomOnLoadListener {
        void onComplete(int data);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public RealtyReserve(Context context, String token) {
        this.context = context;

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RESERVE_NEW_REALTY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);
                    realtyId = root.getInt("realtyid");
                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    if(listener != null) {
                        listener.onComplete(root.getInt("realtyid"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("M2TAG", "Response catch: " + e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("M2TAG", "Response error: " + error.toString());
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
        };
        requestQueue.add(stringRequest);
    }
}
