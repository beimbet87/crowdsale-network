package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.activities.LoginActivity;

public class RegisterFragment07 extends Fragment {
    private static final String PHONE_CODE_URL= "http://someproject-001-site1.itempurl.com/Register/token";

    String resultCode;

    Button registerButton;
    EditText confirmNum01, confirmNum02, confirmNum03, confirmNum04;
    TextView tvPhoneNumber;
    SharedPreferences tokenPreferences;
    public RegisterFragment07() {
        // Required empty public constructor
    }

    public interface DataFromFragment07 {
        public void DataFromFragment07(String data, int number);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_register_07, container, false);

        dataPasser.DataFromFragment07("Основная информация", 6);

        registerButton = fv.findViewById(R.id.btn_register);

        confirmNum01 = fv.findViewById(R.id.et_confirm_01);
        confirmNum02 = fv.findViewById(R.id.et_confirm_02);
        confirmNum03 = fv.findViewById(R.id.et_confirm_03);
        confirmNum04 = fv.findViewById(R.id.et_confirm_04);

        confirmNum01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    confirmNum02.requestFocus();
                }
            }
        });

        confirmNum02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    confirmNum03.requestFocus();
                }
            }
        });

        confirmNum03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    confirmNum04.requestFocus();
                }
            }
        });

        confirmNum04.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    InputMethodManager imm = (InputMethodManager)confirmNum04.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(confirmNum04.getWindowToken(), 0);
                }
            }
        });

        tvPhoneNumber = fv.findViewById(R.id.phone_tv);

        SharedPreferences temp;
        temp = getActivity().getSharedPreferences("M2_REG_INFO", 0);

        final String phone = temp.getString("phone", "");
        final String email = temp.getString("email", "");

        tvPhoneNumber.setText(email);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = confirmNum01.getText().toString() +
                        confirmNum02.getText().toString() +
                        confirmNum03.getText().toString() +
                        confirmNum04.getText().toString();
                RequestQueue userRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("grant_type", "password");
                    jsonBody.put("username", phone);
                    jsonBody.put("password", code);
                    Log.i("USER", code + " " + phone);
                    final String requestBody = jsonBody.toString();
                    StringRequest userStringRequest = new StringRequest(Request.Method.POST, PHONE_CODE_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            tokenPreferences = getActivity().getSharedPreferences("M2_TOKEN", 0);
                            SharedPreferences.Editor editor = tokenPreferences.edit();
                            try {
                                final JSONObject userJsonRoot = new JSONObject(response);
                                editor.putString("access_token", userJsonRoot.getString("access_token"));
                                editor.putString("token_type", userJsonRoot.getString("token_type"));
                                editor.putInt("expires_in", userJsonRoot.getInt("expires_in"));
                                editor.apply();
//                                resultCode = userJsonRoot.getString("error");
//                                Log.i("USER", resultCode);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.register_fragment, new RegisterFragment03()).commit();
//
//                            if(!resultCode.equals("invalid code")) {
//
//                            }
//                            else {
//                                Toast.makeText(getContext(), "Введите правильный код", Toast.LENGTH_SHORT)
//                                        .show();
//                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_RESPONSE", error.toString());
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
                            params.put("grant_type", "password");
                            params.put("username", phone);
                            params.put("password", code);
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

                    userRequestQueue.add(userStringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return fv;
    }

    DataFromFragment07 dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataFromFragment07) context;
    }
}