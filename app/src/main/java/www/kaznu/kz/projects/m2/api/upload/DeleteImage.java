package www.kaznu.kz.projects.m2.api.upload;

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

public class DeleteImage implements Constants {

    private Context context;
    private int resultCode;
    private String resultMessage;

    public interface CustomOnLoadListener {
        void onComplete(int data, String message);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public DeleteImage(Context context, String link, String token) {
        String params = "?link=" + link;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DELETE_IMAGE.concat(params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    if (resultCode == 1) {
                        Log.d("M2TAG", "Realty publish is done!");
                    }

                    if (listener != null) {
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
        };

        requestQueue.add(stringRequest);
    }
}
