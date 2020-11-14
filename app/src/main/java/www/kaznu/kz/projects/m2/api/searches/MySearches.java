package www.kaznu.kz.projects.m2.api.searches;

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
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.Polygons;
import www.kaznu.kz.projects.m2.models.Search;

public class MySearches implements Constants {

    private ArrayList<Search> searches;
    private Context context;
    private int resultCode;
    private String resultMessage;

    public interface CustomOnLoadListener {
        void onComplete(ArrayList<Search> searches);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public MySearches(Context context, String token) {
        this.context = context;
        searches = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_MY_SEARCHES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    JSONArray jsonSearches = root.getJSONArray("searches");

                    for (int k = 0; k < jsonSearches.length(); k++) {
                        JSONObject jsonSearch = jsonSearches.getJSONObject(k);
                        Search search = new Search();
                        search.setRefUser(jsonSearch.getInt("refUser"));
                        search.setStatus(jsonSearch.getInt("status"));
                        search.setCount(jsonSearch.getInt("count"));
                        search.setId(jsonSearch.getInt("id"));

                        Filter filter = new Filter();

                        JSONObject jsonFilter = jsonSearch.getJSONObject("filter");

                        if(!jsonFilter.isNull("polygone")) {
                            JSONArray polygon = jsonFilter.getJSONArray("polygone");
                            ArrayList<Polygons> polygons = new ArrayList<>();
                            for (int p = 0; p < polygon.length(); p++) {
                                JSONObject jsonPolygon = polygon.getJSONObject(p);
                                Polygons location = new Polygons();
                                if(!jsonPolygon.isNull("longitude")) {
                                    location.setLongitude(jsonPolygon.getDouble("longitude"));
                                }
                                if(!jsonPolygon.isNull("latitude")) {
                                    location.setLatitude(jsonPolygon.getDouble("latitude"));
                                }

                                polygons.add(location);
                            }

                            filter.setPolygons(polygons);
                        }

                        if(!jsonFilter.isNull("transactionType")) {
                            filter.setTransactionType(jsonFilter.getInt("transactionType"));
                        }

                        if(!jsonFilter.isNull("realtyType")) {
                            filter.setRealtyType(jsonFilter.getInt("realtyType"));
                        }

                        if(!jsonFilter.isNull("roomCount")) {
                            ArrayList<Integer> rooms = new ArrayList<>();
                            JSONArray jsonRooms = jsonFilter.getJSONArray("roomCount");

                            for (int r = 0; r < jsonRooms.length(); r++) {
                                rooms.add(jsonRooms.getInt(r));
                            }

                            filter.setRoomCount(rooms);
                        }

                        if(!jsonFilter.isNull("costLowerLimit")) {
                            filter.setCostLowerLimit(jsonFilter.getDouble("costLowerLimit"));
                        }
                        if(!jsonFilter.isNull("costUpperLimit")) {
                            filter.setCostUpperLimit(jsonFilter.getDouble("costUpperLimit"));
                        }

                        if(!jsonFilter.isNull("offersOptionsId")) {
                            ArrayList<Integer> offersOptionsId = new ArrayList<>();
                            JSONArray jsonOffersOptionId = jsonFilter.getJSONArray("offersOptionsId");

                            for (int o = 0; o < jsonOffersOptionId.length(); o++) {
                                offersOptionsId.add(jsonOffersOptionId.getInt(o));
                            }

                            filter.setOffersOptionsId(offersOptionsId);
                        }

                        if(!jsonFilter.isNull("propertiesID")) {
                            ArrayList<Integer> propertiesId = new ArrayList<>();
                            JSONArray jsonPropertiesId = jsonFilter.getJSONArray("propertiesID");

                            for (int p = 0; p < jsonPropertiesId.length(); p++) {
                                propertiesId.add(jsonPropertiesId.getInt(p));
                            }

                            filter.setPropertiesId(propertiesId);
                        }
                        if(!jsonFilter.isNull("rentPeriod")) {
                            filter.setRentPeriod(jsonFilter.getInt("rentPeriod"));
                        }
                        if(!jsonFilter.isNull("startTime")) {
                            filter.setStartDate(jsonFilter.getString("startTime"));
                        }
                        if(!jsonFilter.isNull("endTime")) {
                            filter.setEndDate(jsonFilter.getString("endTime"));
                        }
                        if(!jsonFilter.isNull("offset")) {
                            filter.setOffset(jsonFilter.getInt("offset"));
                        }
                        if(!jsonFilter.isNull("limit")) {
                            filter.setLimit(jsonFilter.getInt("limit"));
                        }
                        if(!jsonFilter.isNull("refUser")) {
                            filter.setRefUser(jsonFilter.getInt("refUser"));
                        }

                        search.setFilter(filter);

                        searches.add(search);
                    }

                    if (resultCode == 1) {
                        Log.d("M2TAG", "Filter Offers is done!");
                    }

                    if (listener != null) {
                        listener.onComplete(searches);
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
