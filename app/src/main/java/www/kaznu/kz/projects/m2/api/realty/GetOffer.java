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
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.User;
import www.kaznu.kz.projects.m2.utils.Logger;

public class GetOffer implements Constants {
    private Context context;
    private int resultCode;
    private String resultMessage;
    private int realtyId;

    public interface CustomOnLoadListener {
        void onComplete(Offers offers);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public GetOffer(Context context, int realtyId, String token) {
        this.context = context;
        this.realtyId = realtyId;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_OFFER_BY_ID.concat("?id=").concat(Integer.toString(this.realtyId)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    JSONObject jsonOffers = root.getJSONObject("Result");

                    Offers data = new Offers();
                    JSONObject jsonRealty = jsonOffers.getJSONObject("realty");
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

                    JSONObject jsonOwner = jsonOffers.getJSONObject("owner");

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

                    ArrayList<Integer> offersOptionsId = new ArrayList<>();
                    JSONArray jsonOffersOptionsId = jsonOffers.getJSONArray("offersOptionsId");

                    for (int k = 0; k < jsonOffersOptionsId.length(); k++) {
                        offersOptionsId.add(jsonOffersOptionsId.getInt(k));
                    }

                    data.setOffersOptionsId(offersOptionsId);

                    ArrayList<Integer> properties = new ArrayList<>();
                    JSONArray jsonProperties = jsonOffers.getJSONArray("properties");

                    for (int k = 0; k < jsonProperties.length(); k++) {
                        properties.add(jsonProperties.getInt(k));
                    }

                    data.setProperties(properties);

                    ArrayList<String> imagesLink = new ArrayList<>();
                    JSONArray jsonImages = jsonOffers.getJSONArray("imagesLink");

                    for (int k = 0; k < jsonImages.length(); k++) {
                        imagesLink.add(jsonImages.getString(k));
                    }

                    data.setImagesLink(imagesLink);

                    Logger.d("Offer with id: " + resultCode + " is: " + jsonOffers.toString());

                    if (resultCode == 1) {
                        Logger.d("Offer is done!");
                        if (listener != null) {
                            listener.onComplete(data);
                        }
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}
