package www.kaznu.kz.projects.m2.api.realty;

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
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.models.User;

public class UserApplications implements Constants {
    private ArrayList<Offers> offers;
    private Context context;
    private int resultCode;
    private String resultMessage;

    public interface CustomOnLoadListener {
        void onComplete(ArrayList<Offers> offers);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public UserApplications(Context context, int status, String token) {
        this.context = context;
        offers = new ArrayList<>();

        String params = "?status=" + status;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REALTY_USER_APPLICATIONS.concat(params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    JSONArray jsonOffers = root.getJSONArray("offers");

//                    Log.d("M2TAG", "Offers: " + jsonOffers.toString());

                    for (int i = 0; i < jsonOffers.length(); i++) {
                        Offers data = new Offers();
                        JSONObject offer = jsonOffers.getJSONObject(i);
                        JSONObject jsonRealty = offer.getJSONObject("realty");
//                        Log.d("M2TAG", "Offers: " + jsonRealty.toString());
                        Realty realty = new Realty();
                        realty.setFloorBuild(jsonRealty.getInt("floorebuild"));
                        realty.setStatus(jsonRealty.getInt("status"));
                        realty.setRentPeriod(jsonRealty.getInt("rentPeriod"));
                        realty.setId(jsonRealty.getInt("id"));
                        realty.setFloor(jsonRealty.getInt("floor"));
                        realty.setHeader(jsonRealty.getString("header"));
                        realty.setDescription(jsonRealty.getString("description"));
                        realty.setLongitude(jsonRealty.getDouble("longitude"));
                        realty.setLatitude(jsonRealty.getDouble("latitude"));
                        realty.setRealtyType(jsonRealty.getInt("realtyType"));
                        realty.setRoomCount(jsonRealty.getInt("roomCount"));
                        realty.setCost(jsonRealty.getDouble("cost"));
                        realty.setArea(jsonRealty.getDouble("area"));
                        realty.setLivingSpace(jsonRealty.getDouble("livingSpace"));
                        realty.setTransactionType(jsonRealty.getInt("transactionType"));
                        realty.setAge(jsonRealty.getString("age"));
                        realty.setAddress(jsonRealty.getString("adress"));
                        realty.setRefCity(jsonRealty.getInt("refCity"));

                        data.setRealty(realty);

                        JSONObject jsonOwner = offer.getJSONObject("owner");

                        User owner = new User();
                        owner.setImageLink(jsonOwner.getString("imageLink"));
                        owner.setDescription(jsonOwner.getString("description"));
                        owner.setId(jsonOwner.getInt("id"));
                        owner.setSex(jsonOwner.getInt("sex"));
                        owner.setName(jsonOwner.getString("name"));
                        owner.setSurname(jsonOwner.getString("surname"));
                        owner.setBirth(jsonOwner.getString("birth"));
                        owner.setEmail(jsonOwner.getString("email"));
                        owner.setPhone(jsonOwner.getString("phone"));

                        data.setOwner(owner);

                        ArrayList<Search> searches = new ArrayList<>();

                        JSONArray jsonSearches = offer.getJSONArray("searches");

                        for (int k = 0; k < jsonSearches.length(); k++) {
                            JSONObject jsonSearch = jsonSearches.getJSONObject(k);
                            Search search = new Search();
                            search.setRefUser(jsonSearch.getInt("refUser"));
                            search.setStatus(jsonSearch.getInt("status"));
                            search.setCount(jsonSearch.getInt("count"));
                            search.setId(jsonSearch.getInt("id"));

//                            Filter filter = new Filter();
//
//                            JSONObject jsonFilter = jsonSearch.getJSONObject("filter");
//
//                            JSONArray polygon = jsonFilter.getJSONArray("polygone");
//                            ArrayList<Polygons> polygons = new ArrayList<>();
//                            for (int p = 0; p < polygon.length(); p++) {
//                                JSONObject jsonPolygon = polygon.getJSONObject(p);
//                                Polygons location = new Polygons();
//                                location.setLongitude(jsonPolygon.getDouble("longitude"));
//                                location.setLatitude(jsonPolygon.getDouble("latitude"));
//
//                                polygons.add(location);
//                            }
//
//                            filter.setPolygons(polygons);
//
//                            filter.setTransactionType(jsonFilter.getInt("transactionType"));
//                            filter.setRealtyType(jsonFilter.getInt("realtyType"));
//
//                            ArrayList<Integer> rooms = new ArrayList<>();
//                            JSONArray jsonRooms = jsonFilter.getJSONArray("roomCount");
//
//                            for(int r = 0; r < jsonRooms.length(); r++) {
//                                rooms.add(jsonRooms.getInt(r));
//                            }
//
//                            filter.setRoomCount(rooms);
//
//                            filter.setCostLowerLimit(jsonFilter.getDouble("costLowerLimit"));
//                            filter.setCostUpperLimit(jsonFilter.getDouble("costUpperLimit"));
//
//                            ArrayList<Integer> offersOptionsId = new ArrayList<>();
//                            JSONArray jsonOffersOptionId = jsonFilter.getJSONArray("offersOptionsId");
//
//                            for(int o = 0; o < jsonOffersOptionId.length(); o++) {
//                                offersOptionsId.add(jsonOffersOptionId.getInt(o));
//                            }
//
//                            filter.setOffersOptionsId(offersOptionsId);
//
//                            ArrayList<Integer> propertiesId = new ArrayList<>();
//                            JSONArray jsonPropertiesId = jsonFilter.getJSONArray("propertiesID");
//
//                            for(int p = 0; p < jsonPropertiesId.length(); p++) {
//                                propertiesId.add(jsonPropertiesId.getInt(p));
//                            }
//
//                            filter.setPropertiesId(propertiesId);
//
//                            filter.setRentPeriod(jsonFilter.getInt("rentPeriod"));
//                            filter.setStartDate(jsonFilter.getString("startTime"));
//                            filter.setEndDate(jsonFilter.getString("endTime"));
//                            filter.setOffset(jsonFilter.getInt("offset"));
//                            filter.setLimit(jsonFilter.getInt("limit"));
//                            filter.setRefUser(jsonFilter.getInt("refUser"));
//
//                            search.setFilter(filter);
                            searches.add(search);
                        }

                        data.setSearches(searches);

                        ArrayList<Integer> offersOptionsId = new ArrayList<>();
                        JSONArray jsonOffersOptionsId = offer.getJSONArray("offersOptionsId");

                        for(int k = 0; k < jsonOffersOptionsId.length(); k++) {
                            offersOptionsId.add(jsonOffersOptionsId.getInt(k));
                        }

                        data.setOffersOptionsId(offersOptionsId);

                        ArrayList<Integer> properties = new ArrayList<>();
                        JSONArray jsonProperties = offer.getJSONArray("properties");

                        for(int k = 0; k < jsonProperties.length(); k++) {
                            properties.add(jsonProperties.getInt(k));
                        }

                        data.setProperties(properties);

                        ArrayList<String> imagesLink = new ArrayList<>();
                        JSONArray jsonImages = offer.getJSONArray("imagesLink");

                        for(int k = 0; k < jsonImages.length(); k++) {
                            imagesLink.add(jsonImages.getString(k));
                        }

                        data.setImagesLink(imagesLink);

                        offers.add(data);
                    }

                    if (resultCode == 1) {
                        Log.d("M2TAG", "Filter Offers is done!");
                    }

                    if(listener != null) {
                        listener.onComplete(offers);
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
