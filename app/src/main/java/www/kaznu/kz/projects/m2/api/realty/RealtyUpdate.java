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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Realty;

public class RealtyUpdate implements Constants {
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

    public RealtyUpdate(Context context, Realty realty, String token) {
        this.context = context;

        RealtyCreation realtyCreation = new RealtyCreation(context, token);

        realtyCreation.setOnLoadListener(new RealtyCreation.CustomOnLoadListener() {
            @Override
            public void onComplete(int data) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                realty.setId(data);

                Log.d("M2TAG", realty.getBody());

                final String requestBody = realty.getBody();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_REALTY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            final JSONObject root = new JSONObject(response);

                            resultCode = root.getInt("ResultCode");
                            resultMessage = root.getString("ResultMessage");

                            if(listener != null) {
                                listener.onComplete(resultCode, resultMessage);
                            }

                            if (resultCode == 1) {
                                Log.d("M2TAG", "Realty completely created");
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
        });
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }
}
