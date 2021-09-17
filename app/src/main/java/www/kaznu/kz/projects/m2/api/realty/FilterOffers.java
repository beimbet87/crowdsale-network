package www.kaznu.kz.projects.m2.api.realty;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Polygons;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.models.User;
import www.kaznu.kz.projects.m2.utils.Logger;

public class FilterOffers implements Constants {
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

    public FilterOffers(Context context, Filter filterData, String token) {
        this.context = context;
        offers = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        final String requestBody = filterData.getBody();

        Logger.d("OffersBody: " + requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FILTER_OFFERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    JSONArray jsonOffers = root.getJSONArray("offers");

                    Logger.d("Offers: " + jsonOffers.toString());

                    for (int i = 0; i < jsonOffers.length(); i++) {
                        Offers data = new Offers();
                        JSONObject offer = jsonOffers.getJSONObject(i);
                        JSONObject jsonRealty = offer.getJSONObject("realty");
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
                        owner.setStars(jsonOwner.getInt("stars"));
                        if(jsonOwner.isNull("currency")) {
                            owner.setCurrency(0);
                        } else {
                            owner.setCurrency(jsonOwner.getInt("currency"));
                        }

                        data.setOwner(owner);

                        ArrayList<Search> searches = new ArrayList<>();

                        if(!offer.isNull("searches")) {
                            JSONArray jsonSearches = offer.getJSONArray("searches");

                            Logger.d("Search size ---> " + jsonSearches.length());

                            for (int k = 0; k < jsonSearches.length(); k++) {
                                JSONObject jsonSearch = jsonSearches.getJSONObject(k);
                                Search search = new Search();
                                search.setRefUser(jsonSearch.getInt("refUser"));
                                search.setStatus(jsonSearch.getInt("status"));
                                search.setCount(jsonSearch.getInt("count"));
                                search.setId(jsonSearch.getInt("id"));

                                Filter filter = new Filter();

                                JSONObject jsonFilter = jsonSearch.getJSONObject("filter");

                                if (!jsonFilter.isNull("polygone")) {
                                    JSONArray polygon = jsonFilter.getJSONArray("polygone");
                                    ArrayList<Polygons> polygons = new ArrayList<>();
                                    for (int p = 0; p < polygon.length(); p++) {
                                        JSONObject jsonPolygon = polygon.getJSONObject(p);
                                        Polygons location = new Polygons();
                                        location.setLongitude(jsonPolygon.getDouble("longitude"));
                                        location.setLatitude(jsonPolygon.getDouble("latitude"));

                                        polygons.add(location);
                                    }

                                    filter.setPolygons(polygons);
                                }
                                else {
                                    filter.setPolygons(null);
                                }

                                if(!jsonFilter.isNull("transactionType")) {
                                    filter.setTransactionType(jsonFilter.getInt("transactionType"));
                                } else {
                                    filter.setTransactionType(0);
                                }

                                if(!jsonFilter.isNull("realtyType")) {
                                    filter.setRealtyType(jsonFilter.getInt("realtyType"));
                                }
                                else {
                                    filter.setRealtyType(0);
                                }

                                if(!jsonFilter.isNull("roomCount")) {
                                    ArrayList<Integer> rooms = new ArrayList<>();
                                    JSONArray jsonRooms = jsonFilter.getJSONArray("roomCount");

                                    for (int r = 0; r < jsonRooms.length(); r++) {
                                        rooms.add(jsonRooms.getInt(r));
                                    }

                                    filter.setRoomCount(rooms);
                                } else {
                                    filter.setRoomCount(null);
                                }

                                if(!jsonFilter.isNull("costLowerLimit")) {
                                    filter.setCostLowerLimit(jsonFilter.getDouble("costLowerLimit"));
                                } else {
                                    filter.setCostLowerLimit(0.0);
                                }

                                if(!jsonFilter.isNull("costUpperLimit")) {
                                    filter.setCostUpperLimit(jsonFilter.getDouble("costUpperLimit"));
                                } else {
                                    filter.setCostUpperLimit(0.0);
                                }

                                if(!jsonFilter.isNull("offersOptionsId")) {
                                    ArrayList<Integer> offersOptionsId = new ArrayList<>();
                                    JSONArray jsonOffersOptionId = jsonFilter.getJSONArray("offersOptionsId");

                                    for (int o = 0; o < jsonOffersOptionId.length(); o++) {
                                        offersOptionsId.add(jsonOffersOptionId.getInt(o));
                                    }

                                    filter.setOffersOptionsId(offersOptionsId);
                                } else {
                                    filter.setOffersOptionsId(null);
                                }

                                if(!jsonFilter.isNull("propertiesID")) {
                                    ArrayList<Integer> propertiesId = new ArrayList<>();
                                    JSONArray jsonPropertiesId = jsonFilter.getJSONArray("propertiesID");

                                    for (int p = 0; p < jsonPropertiesId.length(); p++) {
                                        propertiesId.add(jsonPropertiesId.getInt(p));
                                    }

                                    filter.setPropertiesId(propertiesId);
                                } else {
                                    filter.setPropertiesId(null);
                                }

                                if(!jsonFilter.isNull("rentPeriod")) {
                                    filter.setRentPeriod(jsonFilter.getInt("rentPeriod"));
                                } else {
                                    filter.setRentPeriod(0);
                                }
                                if(!jsonFilter.isNull("startTime")) {
                                    filter.setStartDate(jsonFilter.getString("startTime"));
                                } else {
                                    filter.setStartDate(null);
                                }
                                if(!jsonFilter.isNull("endTime")) {
                                    filter.setEndDate(jsonFilter.getString("endTime"));
                                } else {
                                    filter.setEndDate(null);
                                }
                                if(!jsonFilter.isNull("offset")) {
                                    filter.setOffset(jsonFilter.getInt("offset"));
                                } else {
                                    filter.setOffset(0);
                                }
                                if(!jsonFilter.isNull("limit")) {
                                    filter.setLimit(jsonFilter.getInt("limit"));
                                } else {
                                    filter.setLimit(0);
                                }
                                if(!jsonFilter.isNull("refUser")) {
                                    filter.setRefUser(jsonFilter.getInt("refUser"));
                                } else {
                                    filter.setRefUser(0);
                                }

                                search.setFilter(filter);
                                searches.add(search);
                            }

                            data.setSearches(searches);

                            ArrayList<Integer> offersOptionsId = new ArrayList<>();
                            JSONArray jsonOffersOptionsId = offer.getJSONArray("offersOptionsId");

                            for (int k = 0; k < jsonOffersOptionsId.length(); k++) {
                                offersOptionsId.add(jsonOffersOptionsId.getInt(k));
                            }

                            data.setOffersOptionsId(offersOptionsId);

                            ArrayList<Integer> properties = new ArrayList<>();
                            JSONArray jsonProperties = offer.getJSONArray("properties");

                            for (int k = 0; k < jsonProperties.length(); k++) {
                                properties.add(jsonProperties.getInt(k));
                            }

                            data.setProperties(properties);

                            ArrayList<String> imagesLink = new ArrayList<>();
                            JSONArray jsonImages = offer.getJSONArray("imagesLink");

                            for (int k = 0; k < jsonImages.length(); k++) {
                                imagesLink.add(jsonImages.getString(k));
                            }

                            data.setImagesLink(imagesLink);

                            offers.add(data);
                        }
                        else {
                            Logger.d("Searches are null");
                        }
                    }

                    if (resultCode == 1) {
                        Logger.d("Filter Offers is done!");
                    }

                    if (listener != null) {
                        listener.onComplete(offers);
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}
