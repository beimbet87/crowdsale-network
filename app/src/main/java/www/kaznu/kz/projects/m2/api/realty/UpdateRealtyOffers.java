package www.kaznu.kz.projects.m2.api.realty;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.ConfigValue;

public class UpdateRealtyOffers implements Constants {

    private Context context;
    private ArrayList<ConfigValue> configValues;
    private String token;
    private String resultMessage;
    private int resultCode;

    public interface CustomOnLoadListener {
        void onComplete(int resultCode, String resultMessage);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public UpdateRealtyOffers(Context context, ArrayList<ConfigValue> configValue, String token) {
        this.context = context;
        this.token = token;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONArray jsonBody = new JSONArray();

        try {
            for(int i = 0; i < configValue.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("refRealty", configValue.get(i).getRefRealty());
                data.put("type", configValue.get(i).getType());
                data.put("set", configValue.get(i).isSet());

                jsonBody.put(i, data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Filter Error: " + e);
        }

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_REALTY_OFFERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    if (resultCode == 1) {
                        Log.d("M2TAG", "UpdateRealty Offers is done!");
                    }

                    if(listener != null) {
                        listener.onComplete(resultCode, resultMessage);
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

        requestQueue.add(stringRequest);
    }

}
