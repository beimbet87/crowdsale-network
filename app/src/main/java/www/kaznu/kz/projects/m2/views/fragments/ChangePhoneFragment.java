package www.kaznu.kz.projects.m2.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.CountriesCustomAdapter;
import www.kaznu.kz.projects.m2.adapters.GenderTypeAdapter;
import www.kaznu.kz.projects.m2.api.user.RegistrationAuth;
import www.kaznu.kz.projects.m2.api.user.UserInfo;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.PhoneTextFormatter;
import www.kaznu.kz.projects.m2.models.RegistrationStep1;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.models.User;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.TinyDB;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.utils.VolleyMultipartRequest;

import static android.app.Activity.RESULT_OK;
import static www.kaznu.kz.projects.m2.interfaces.Constants.BASE_URL;

public class ChangePhoneFragment extends Fragment implements View.OnClickListener {

    int userId;
    Button btnGetCode;

    @Override
    public void onClick(View v) {

    }

    public interface DataFromChangePhoneFragment {
        void FromChangePhoneFragment(String data, int number);
    }

    public ChangePhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_change_phone, container, false);

        btnGetCode = fv.findViewById(R.id.btn_get_code);

        Spinner countriesSpinner = fv.findViewById(R.id.countries_spinner);

        String[] countries = {"Казахстан", "Россия", "Франция", "Китай"};
        countriesSpinner.setAdapter(new CountriesCustomAdapter(requireContext(), countries));
        countriesSpinner.setSelection(0);
        EditText phoneNumber = fv.findViewById(R.id.et_phone_number);
        phoneNumber.addTextChangedListener(new PhoneTextFormatter(phoneNumber, "(###) ###-##-##"));

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationStep1 data = new RegistrationStep1();
                data.setRegIdentity("+7" + phoneNumber.getText().toString().replaceAll("[ \\-()]", ""));
                data.setCountryCode("");
                RegistrationAuth auth = new RegistrationAuth(requireContext(), requireActivity(), data, new Tokens(requireContext()).getAccessToken());
                auth.setOnLoadListener(new RegistrationAuth.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int resultCode, String resultMessage, int userId) {

                        new TinyDB(requireContext()).putString("regIdentity", "+7" + phoneNumber.getText().toString().replaceAll("[ \\-()]", ""));

                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.change_data, new ConfirmationPhoneFragment()).commit();
                    }
                });
            }
        });

        dataPasser.FromChangePhoneFragment("Смена телефона", 0);

        return fv;
    }

    DataFromChangePhoneFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromChangePhoneFragment) context;
    }

}