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
import www.kaznu.kz.projects.m2.models.BookOfferFromOwnerModel;
import www.kaznu.kz.projects.m2.models.BookingApplication;

public class LastBookingOfferInfo implements Constants {
    private int resultCode;
    private String resultMessage;

    private BookOfferFromOwnerModel bookOfferFromOwnerModel;

    public interface CustomOnLoadListener {
        void onComplete(BookOfferFromOwnerModel bookOffer);
    }

    public CustomOnLoadListener listener;

    public void setOnLoadListener(CustomOnLoadListener listener) {
        this.listener = listener;
    }


    public LastBookingOfferInfo(Context context, int refRealty, String token) {

        this.listener = null;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_OWNER_BOOKING.concat("?refRealty=").concat(String.valueOf(refRealty)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject root = new JSONObject(response);

                    bookOfferFromOwnerModel = new BookOfferFromOwnerModel();

                    resultCode = root.getInt("ResultCode");
                    resultMessage = root.getString("ResultMessage");

                    bookOfferFromOwnerModel.setHasOffers(root.getBoolean("hasOffers"));
                    bookOfferFromOwnerModel.setRefBooking(root.getInt("refBooking"));
                    bookOfferFromOwnerModel.setRefFrom(root.getInt("refFrom"));
                    bookOfferFromOwnerModel.setRefTo(root.getInt("refTo"));
                    bookOfferFromOwnerModel.setRefRealty(root.getInt("refRealty"));
                    bookOfferFromOwnerModel.setPrice(root.getDouble("price"));
                    bookOfferFromOwnerModel.setTime(root.getString("time"));

                    if (listener != null) {
                        listener.onComplete(bookOfferFromOwnerModel);
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
