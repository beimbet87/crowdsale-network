package www.kaznu.kz.projects.m2.api.rate;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.RateModel;

public class RealtyRate implements Constants {
    private int resultCode;
    private String resultMessage;
    private int count;
    private double average;

    private ArrayList<RateModel> rates;

    public interface CustomOnLoadListener {
        void onComplete(ArrayList<RateModel> rates, int count, double average);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }


    public RealtyRate(Context context, int idRealty, String token) {

        this.listener = null;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RATE_REALTY.concat("?idRealty=").concat(String.valueOf(idRealty)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);

                    rates = new ArrayList<>();

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");
                    count = root.getInt("count");
                    average = root.getDouble("average");

                    if(resultCode == 1) {
                        JSONArray rateJSON = root.getJSONArray("rates");

                        for (int i = 0; i < rateJSON.length(); i++) {
                            JSONObject rate = rateJSON.getJSONObject(i);
                            RateModel data = new RateModel();
                            data.setId(rate.getInt("id"));
                            data.setTime(rate.getString("time"));
                            data.setRefFrom(rate.getInt("refFrom"));
                            data.setRefRealty(rate.getInt("refRealty"));
                            data.setStars(rate.getInt("stars"));
                            data.setComment(rate.getString("comment"));
                            data.setName(rate.getString("name"));
                            data.setSurname(rate.getString("surname"));
                            data.setImage(rate.getString("image"));

                            rates.add(data);
                        }
                        if(listener != null) {
                            listener.onComplete(rates, count, average);
                        }
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

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
