package www.kaznu.kz.projects.m2.api.pusher;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pusher.client.AuthorizationFailureException;
import com.pusher.client.Authorizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.AuthData;
import www.kaznu.kz.projects.m2.utils.Logger;

public class PusherChannel implements Constants, Authorizer {
    private int resultCode;
    private String resultMessage;
    private String channelName;
    private final AuthData authData;
    private String responseData;

    @Override
    public String authorize(String channelName, String socketId) throws AuthorizationFailureException {
        try {
            Logger.d(new JSONObject(responseData).getJSONObject("authData").toString());
            return new JSONObject(responseData).getJSONObject("authData").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    public interface CustomOnLoadListener {
        void onComplete(int data, String message, String channel, AuthData auth);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public PusherChannel(Context context, String socket_id, String token) {

        String params = "?socket_id=" + socket_id;

        authData = new AuthData();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PUSHER_AUTH.concat(params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                responseData = response;

                try {
                    final JSONObject root = new JSONObject(response);

                    JSONObject auth = root.getJSONObject("authData");

                    authData.setAuth(auth.getString("auth"));
//                    authData.setChannelData(auth.getString("channel_data"));

                    channelName = root.getString("chanelName");
                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    if (resultCode == 1) {
                        Logger.d("Pusher channel is done!");
                    }

                    if(listener != null) {
                        listener.onComplete(resultCode, resultMessage, channelName, authData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.d("Response catch: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.d("Response error: " + error.toString());
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
