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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Message;
import www.kaznu.kz.projects.m2.utils.Logger;

public class Conversations implements Constants {
    private int resultCode;
    private String resultMessage;
    private String channelName;
    private ArrayList<Message> messages;

    public interface CustomOnLoadListener {
        void onComplete(int data, String message, ArrayList<Message> messages);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public Conversations(Context context, int contact, int refRealty, String token) {

        Logger Log = new Logger(context, TAG);

        String params = "?contact=" + contact + "&refRealty=" + refRealty;

        messages = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PUSHER_CONVERSATION.concat(params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject root = new JSONObject(response);

                    JSONArray jsonChats = root.getJSONArray("chats");

                    for (int i = 0; i < jsonChats.length(); i++) {
                        JSONObject data = jsonChats.getJSONObject(i);

                        Message message = new Message();

                        message.setId(data.getInt("id"));
                        message.setSender_id(data.getInt("sender_id"));
                        message.setReceiver_id(data.getInt("receiver_id"));
                        message.setMessage(data.getString("message"));
                        message.setCreated_at(data.getString("created_at"));
                        message.setRefRealty(data.getInt("refRealty"));
                        message.setPrice(data.getDouble("price"));
                        message.setTimeStart(data.getString("timeStart"));
                        message.setTimeEnd(data.getString("timeEnd"));
                        message.setMine(data.getBoolean("mine"));
                        message.setType(data.getInt("type"));

                        messages.add(message);
                    }

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    if (resultCode == 1) {
                        Log.d("Pusher channel is done!");
                    }

                    if(listener != null) {
                        listener.onComplete(resultCode, resultMessage, messages);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Response catch: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response error: " + error.toString());
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
