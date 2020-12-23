package www.kaznu.kz.projects.m2.api.user;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.api.book.ClientBookings;
import www.kaznu.kz.projects.m2.api.book.OwnerBooking;
import www.kaznu.kz.projects.m2.api.pusher.MessageListData;
import www.kaznu.kz.projects.m2.api.rate.UserRate;
import www.kaznu.kz.projects.m2.api.realty.UserApplications;
import www.kaznu.kz.projects.m2.api.searches.MySearches;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class Token implements Constants {

    private final Context context;
    private final TinyDB data;
    private String token;

    public interface CustomOnLoadListener {
        void onComplete(String t);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }

    public Token(Context context, String userName, String password) {

        this.context = context;

        data = new TinyDB(this.context);

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TOKEN, response -> {

            try {
                final JSONObject jsonRoot = new JSONObject(response);

                token = jsonRoot.getString("access_token");

                data.putString(SHARED_ACCESS_TOKEN, jsonRoot.getString("access_token"));
                data.putString(SHARED_TOKEN_TYPE, jsonRoot.getString("token_type"));
                data.putInt(SHARED_EXPIRES_IN, jsonRoot.getInt("expires_in"));
                Logger.d("Access ---> Token: " + token);
                new UserInfo(this.context, token).setOnLoadListener(user -> {
                    data.putInt(SHARED_USER_ID, user.getId());
                    data.putInt(SHARED_USER_SEX, user.getSex());
                    data.putString(SHARED_USER_NAME, user.getName());
                    data.putString(SHARED_USER_SURNAME, user.getSurname());
                    data.putString(SHARED_USER_BIRTH, user.getBirth());
                    data.putString(SHARED_USER_EMAIL, user.getEmail());
                    data.putString(SHARED_USER_PHONE, user.getPhone());
                    data.putString(SHARED_USER_IMAGE_LINK, user.getImageLink());
                    data.putString(SHARED_USER_DESCRIPTION, user.getDescription());
                    data.putInt(SHARED_USER_CURRENCY, user.getCurrency());
                    data.putInt(SHARED_USER_STARS, user.getStars());
                    data.putString(SHARED_USER_COUNTRY_CODE, user.getCountryCode());
                    data.putString(SHARED_USER_COUNTRY_NAME, user.getCountryName());

                    Logger.d("User ---> Name: " + user.getName());

                    UserRate userRate = new UserRate(this.context, user.getId(), 0, token);

                    userRate.setOnLoadListener((rates, count, average) -> {
                        data.putInt(SHARED_USER_RATE_COUNT, count);
                        data.putDouble(SHARED_USER_RATE_AVERAGE, average);
                        data.putListRateModel(SHARED_USER_RATE, rates);
                    });

                    UserRate ownerRate = new UserRate(this.context, user.getId(), 1, token);

                    ownerRate.setOnLoadListener((rates, count, average) -> {
                        data.putInt(SHARED_OWNER_RATE_COUNT, count);
                        data.putDouble(SHARED_OWNER_RATE_AVERAGE, average);
                        data.putListRateModel(SHARED_OWNER_RATE, rates);
                    });

                    ClientBookings clientBookings = new ClientBookings(this.context, token);

                    clientBookings.setOnLoadListener((bookings, history) -> {
                        data.putListBookingModel(SHARED_USER_BOOKING, bookings);
                        data.putListBookingModel(SHARED_USER_BOOKING_HISTORY, history);
                    });

                    OwnerBooking ownerBooking = new OwnerBooking(this.context, token);

                    ownerBooking.setOnLoadListener((bookings, history) -> {
                        data.putListBookingModel(SHARED_OWNER_BOOKING, bookings);
                        data.putListBookingModel(SHARED_OWNER_BOOKING_HISTORY, history);
                    });

                    MySearches searches = new MySearches(this.context, token);
                    searches.setOnLoadListener(new MySearches.CustomOnLoadListener() {
                        @Override
                        public void onComplete(ArrayList<Search> searches) {
                            data.putListSearchModel(SHARED_USER_SEARCH_LIST, searches);
                        }
                    });

                    UserApplications published = new UserApplications(this.context, 1, token);
                    published.setOnLoadListener(new UserApplications.CustomOnLoadListener() {
                        @Override
                        public void onComplete(ArrayList<Offers> offers) {
                            data.putListOfferModel(SHARED_USER_PUBLISHED_ADVERT_LIST, offers);
                        }
                    });

                    UserApplications unpublished = new UserApplications(this.context, 0, token);
                    unpublished.setOnLoadListener(new UserApplications.CustomOnLoadListener() {
                        @Override
                        public void onComplete(ArrayList<Offers> offers) {
                            data.putListOfferModel(SHARED_USER_UNPUBLISHED_ADVERT_LIST, offers);
                        }
                    });

                    MessageListData clientMessageList = new MessageListData(this.context, 0, token);
                    clientMessageList.setOnLoadListener((code, message, chats) -> data.putListMessageModel(SHARED_USER_MESSAGE_LIST, chats));

                    MessageListData ownerMessageList = new MessageListData(this.context, 1, token);
                    ownerMessageList.setOnLoadListener((code, message, chats) -> data.putListMessageModel(SHARED_OWNER_MESSAGE_LIST, chats));
                });

                if(listener != null) {
                    listener.onComplete(token);
                    Logger.d("Access ---> Token: " + token);
                }

            } catch (JSONException e) {
                final JSONObject jsonRoot;
                try {
                    jsonRoot = new JSONObject(response);
                    Toast.makeText(this.context, jsonRoot.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }, error -> Logger.e(error.toString())) {
            @Override
            public String getBodyContentType() {
                return "text/plain; charset=utf-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "password");
                params.put("username", userName);
                params.put("password", password);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
