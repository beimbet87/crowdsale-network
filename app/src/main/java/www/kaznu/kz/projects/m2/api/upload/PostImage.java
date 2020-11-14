package www.kaznu.kz.projects.m2.api.upload;

import android.content.Context;
import android.util.Log;

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

import www.kaznu.kz.projects.m2.interfaces.Constants;

public class PostImage implements Constants {

    private Context context;
    private int resultCode;
    private String resultMessage;
    private ArrayList<String> links;

    public interface CustomOnLoadListener {
        void onComplete(ArrayList<String> links);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public PostImage(Context context, int source) {
        this.context = context;
        links = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);
                    JSONArray jsonLinks = root.getJSONArray("links");
                    for (int i = 0; i < jsonLinks.length(); i++) {
                        links.add(jsonLinks.getString(i));
                    }
                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    if(listener != null) {
                        listener.onComplete(links);
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
        };
        requestQueue.add(stringRequest);
    }
}
