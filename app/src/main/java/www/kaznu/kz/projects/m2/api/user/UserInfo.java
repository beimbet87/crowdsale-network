package www.kaznu.kz.projects.m2.api.user;

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
import www.kaznu.kz.projects.m2.models.User;

public class UserInfo implements Constants {
    private int resultCode;
    private String resultMessage;
    private String countryCode;
    private String countryName;
    private Context context;
    private int count;
    private String token;

    public interface CustomOnLoadListener {
        void onComplete(User data);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public UserInfo(Context context, String token) {
        this.context = context;
        this.listener = null;
        this.token = token;

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_USER_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);
                    User data = new User();

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");
                    countryCode = root.getString("countryCode");
                    countryName = root.getString("countryName");

                    if(resultCode == 1) {
                        JSONObject directory = root.getJSONObject("um");
                        data.setImageLink(directory.getString("imageLink"));
                        data.setDescrition(directory.getString("description"));
                        data.setId(directory.getInt("id"));
                        data.setSex(directory.getInt("sex"));
                        data.setStars(directory.getInt("stars"));
                        data.setCurrency(directory.getInt("currency"));
                        data.setName(directory.getString("name"));
                        data.setSurname(directory.getString("surname"));
                        data.setBirth(directory.getString("birth"));
                        data.setEmail(directory.getString("email"));
                        data.setPhone(directory.getString("phone"));

                        if(listener != null) {
                            listener.onComplete(data);
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

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }
}
