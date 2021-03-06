package www.kaznu.kz.projects.m2.api.pusher;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.utils.Logger;

public class MessageListData implements Constants, Serializable {
    private int resultCode;
    private String resultMessage;
    private final ArrayList<Chat> chats;
    private int size;

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public interface CustomOnLoadListener {
        void onComplete(int code, String message, ArrayList<Chat> chats);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public MessageListData(Context context, int owner, String token) {

        chats = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PUSHER_GET_MY_CHATS + "?asOwner=" + owner, response -> {

            try {
                final JSONObject root = new JSONObject(response);

                JSONArray jsonChats = root.getJSONArray("chats");

                for (int i = 0; i < jsonChats.length(); i++) {
                    JSONObject data = jsonChats.getJSONObject(i);
                    Chat chat = new Chat();

                    chat.setCompanyName(data.getString("companyName"));
                    chat.setImageLink(data.getString("imagelink"));
                    chat.setMe(data.getInt("me"));
                    chat.setCompany(data.getInt("company"));
                    chat.setRefRealty(data.getInt("refRealty"));
                    chat.setCount(data.getInt("count"));
                    chat.setCountNew(data.getInt("countNew"));
                    chat.setMeOwner(data.getInt("meOwner"));
                    chat.setLastMessage(data.getString("lastMessage"));
                    chat.setHaveRequest(data.getBoolean("haveRequest"));
                    chat.setSocket_id(data.getString("socket_id"));

                    chats.add(chat);
                }

                resultCode = root.getInt("ResultCode");
                resultMessage = root.getString("ResultMessage");

                if (resultCode == 1) {
                    setSize(chats.size());
                }

                if (listener != null) {
                    listener.onComplete(resultCode, resultMessage, chats);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Logger.d("Response catch: " + e.toString());
            }
        }, error -> {
            Logger.d("Response error: " + error.toString());
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
            public Map<String, String> getHeaders() {
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
