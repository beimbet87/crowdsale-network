package www.kaznu.kz.projects.m2.api.book;

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
import www.kaznu.kz.projects.m2.models.BookingApplication;

public class OwnerBooking implements Constants {
    private int resultCode;
    private String resultMessage;

    public ArrayList<BookingApplication> getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(ArrayList<BookingApplication> activeBookings) {
        this.activeBookings = activeBookings;
    }

    public ArrayList<BookingApplication> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(ArrayList<BookingApplication> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    private ArrayList<BookingApplication> activeBookings;
    private ArrayList<BookingApplication> bookingHistory;

    public interface CustomOnLoadListener {
        void onComplete(ArrayList<BookingApplication> bookings, ArrayList<BookingApplication> history);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }


    public OwnerBooking(Context context, String token) {

        this.activeBookings = new ArrayList<>();
        this.bookingHistory = new ArrayList<>();
        this.listener = null;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_OWNER_BOOKING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    if (resultCode == 1) {
                        JSONArray bookingsJSON = root.getJSONArray("activeBookings");

                        for (int i = 0; i < bookingsJSON.length(); i++) {
                            JSONObject booking = bookingsJSON.getJSONObject(i);
                            BookingApplication data = new BookingApplication();
                            data.setRefRealtyGuest(booking.getInt("refRealtyGuest"));
                            data.setLinkImage(booking.getString("linkImage"));
                            data.setRefRealtyOwner(booking.getInt("refRealtyOwner"));
                            data.setAppid(booking.getInt("appid"));
                            data.setOwnerAccept(booking.getInt("ownerAccept"));
                            data.setClientAccept(booking.getInt("clientAccept"));
                            data.setAddress(booking.getString("adress"));
                            data.setRefRealty(booking.getInt("refRealty"));
                            data.setPricePerDay(booking.getDouble("pricePerDay"));
                            data.setTimeStart(booking.getString("timeStart"));
                            data.setTimeEnd(booking.getString("timeEnd"));
                            data.setAccept(booking.getInt("accept"));
                            activeBookings.add(data);
                        }

                        JSONArray historyJSON = root.getJSONArray("bookingHistory");

                        for (int i = 0; i < historyJSON.length(); i++) {
                            JSONObject booking = historyJSON.getJSONObject(i);
                            BookingApplication data = new BookingApplication();
                            data.setRefRealtyGuest(booking.getInt("refRealtyGuest"));
                            data.setLinkImage(booking.getString("linkImage"));
                            data.setRefRealtyOwner(booking.getInt("refRealtyOwner"));
                            data.setAppid(booking.getInt("appid"));
                            data.setOwnerAccept(booking.getInt("ownerAccept"));
                            data.setClientAccept(booking.getInt("clientAccept"));
                            data.setAddress(booking.getString("adress"));
                            data.setRefRealty(booking.getInt("refRealty"));
                            data.setPricePerDay(booking.getDouble("pricePerDay"));
                            data.setTimeStart(booking.getString("timeStart"));
                            data.setTimeEnd(booking.getString("timeEnd"));
                            data.setAccept(booking.getInt("accept"));
                            bookingHistory.add(data);
                        }

                        if (listener != null) {
                            listener.onComplete(activeBookings, bookingHistory);
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

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
