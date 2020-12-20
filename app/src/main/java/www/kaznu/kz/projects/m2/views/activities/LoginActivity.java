package www.kaznu.kz.projects.m2.views.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.api.book.ClientBookings;
import www.kaznu.kz.projects.m2.api.book.OwnerBooking;
import www.kaznu.kz.projects.m2.api.pusher.MessageListData;
import www.kaznu.kz.projects.m2.api.rate.UserRate;
import www.kaznu.kz.projects.m2.api.user.UserInfo;
import www.kaznu.kz.projects.m2.interfaces.Constants;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.BookingApplication;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.models.RateModel;
import www.kaznu.kz.projects.m2.models.User;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class LoginActivity extends IntroActivity implements Constants {
    final String URL = "http://someproject-001-site1.itempurl.com/token";
    TextView tvRegister;
    Button btnLogin;
    EditText etLogin, etPassword;
    String token = null;
    SharedPreferences tokenPreferences;
    SharedPreferences userPreferences;
    int resultCode = 0;
    String message;

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TinyDB data = new TinyDB(this);


        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        Permissions.check(this/*context*/, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
//                Toast.makeText(LoginActivity.this, "Все права разрешены!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
            }
        });

        tvRegister = findViewById(R.id.tv_registration);
        btnLogin = findViewById(R.id.btn_login);
        etLogin = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tokenPreferences = getSharedPreferences("M2_TOKEN", 0);
                        SharedPreferences.Editor editor = tokenPreferences.edit();
                        try {
                            String tempToken;
                            final JSONObject jsonRoot = new JSONObject(response);
                            editor.putString("access_token", jsonRoot.getString("access_token"));
                            editor.putString("token_type", jsonRoot.getString("token_type"));
                            editor.putInt("expires_in", jsonRoot.getInt("expires_in"));
                            editor.apply();

                            tempToken = jsonRoot.getString("access_token");

                            data.putString(SHARED_ACCESS_TOKEN, jsonRoot.getString("access_token"));
                            data.putString(SHARED_TOKEN_TYPE, jsonRoot.getString("token_type"));
                            data.putInt(SHARED_EXPIRES_IN, jsonRoot.getInt("expires_in"));

                            new UserInfo(getApplicationContext(), tempToken).setOnLoadListener(new UserInfo.CustomOnLoadListener() {
                                @Override
                                public void onComplete(User user) {
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

                                    UserRate userRate = new UserRate(getApplicationContext(), user.getId(), 0, tempToken);

                                    userRate.setOnLoadListener(new UserRate.CustomOnLoadListener() {
                                        @Override
                                        public void onComplete(ArrayList<RateModel> rates, int count, double average) {
                                            data.putInt(SHARED_USER_RATE_COUNT, count);
                                            data.putDouble(SHARED_USER_RATE_AVERAGE, average);
                                            data.putListRateModel(SHARED_USER_RATE, rates);
                                        }
                                    });

                                    UserRate ownerRate = new UserRate(getApplicationContext(), user.getId(), 1, tempToken);

                                    ownerRate.setOnLoadListener(new UserRate.CustomOnLoadListener() {
                                        @Override
                                        public void onComplete(ArrayList<RateModel> rates, int count, double average) {
                                            data.putInt(SHARED_OWNER_RATE_COUNT, count);
                                            data.putDouble(SHARED_OWNER_RATE_AVERAGE, average);
                                            data.putListRateModel(SHARED_OWNER_RATE, rates);
                                        }
                                    });

                                    ClientBookings clientBookings = new ClientBookings(getApplicationContext(), tempToken);

                                    clientBookings.setOnLoadListener(new ClientBookings.CustomOnLoadListener() {
                                        @Override
                                        public void onComplete(ArrayList<BookingApplication> bookings, ArrayList<BookingApplication> history) {
                                            data.putListBookingModel(SHARED_USER_BOOKING, bookings);
                                            data.putListBookingModel(SHARED_USER_BOOKING_HISTORY, history);
                                        }
                                    });

                                    OwnerBooking ownerBooking = new OwnerBooking(getApplicationContext(), tempToken);

                                    ownerBooking.setOnLoadListener((bookings, history) -> {
                                        data.putListBookingModel(SHARED_OWNER_BOOKING, bookings);
                                        data.putListBookingModel(SHARED_OWNER_BOOKING_HISTORY, history);
                                    });

                                    MessageListData clientMessageList = new MessageListData(getApplicationContext(), 0, tempToken);
                                    clientMessageList.setOnLoadListener((code, message, chats) -> data.putListMessageModel(SHARED_USER_MESSAGE_LIST, chats));

                                    MessageListData ownerMessageList = new MessageListData(getApplicationContext(), 1, tempToken);
                                    ownerMessageList.setOnLoadListener((code, message, chats) -> data.putListMessageModel(SHARED_OWNER_MESSAGE_LIST, chats));
                                }
                            });

                            Intent intent = new Intent(LoginActivity.this, ProfileTypeActivity.class);
                            startActivity(intent);
//                            finish();

                        } catch (JSONException e) {
                            final JSONObject jsonRoot;
                            try {
                                jsonRoot = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), jsonRoot.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LOG_RESPONSE", error.toString());
                    }
                }) {
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
                        params.put("username", etLogin.getText().toString());
                        params.put("password", etPassword.getText().toString());

                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}