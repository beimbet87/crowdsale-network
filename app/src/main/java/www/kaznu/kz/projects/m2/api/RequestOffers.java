package www.kaznu.kz.projects.m2.api;

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
import www.kaznu.kz.projects.m2.models.Directory;

public class RequestOffers implements Constants {
    private int resultCode;
    private String resultMessage;
    private Context context;
    private int count;

    private ArrayList<Directory> directories;

    public interface CustomOnLoadListener {
        void onComplete(ArrayList<Directory> data);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public RequestOffers(Context context) {
        this.context = context;
        this.directories = new ArrayList<>();
        this.listener = null;

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_REQUEST_OFFERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");
                    ArrayList<Directory> realtyTypes = new ArrayList<>();

                    if(resultCode == 1) {
                        JSONArray dirsJSON = root.getJSONArray("directories");

                        for (int i = 0; i < dirsJSON.length(); i++) {
                            JSONObject directory = dirsJSON.getJSONObject(i);
                            Directory data = new Directory();
                            data.setCodeId(directory.getInt("codeint"));
                            data.setCodeStr(directory.getString("codestr"));
                            data.setValue(directory.getString("value"));
                            realtyTypes.add(data);
                        }
                        if(listener != null) {
                            listener.onComplete(realtyTypes);
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
        };
        requestQueue.add(stringRequest);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public ArrayList<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(ArrayList<Directory> directories) {
        this.directories = directories;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
