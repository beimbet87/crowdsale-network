package www.kaznu.kz.projects.m2.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.LoginActivity;
import www.kaznu.kz.projects.m2.adapters.CountriesAdapter;
import www.kaznu.kz.projects.m2.api.Countries;
import www.kaznu.kz.projects.m2.api.user.Registration;
import www.kaznu.kz.projects.m2.models.PhoneTextFormatter;

public class RegisterFragment01 extends Fragment {

    Button getCodeButton;
    EditText phoneNumber, countryCode;
    CheckBox cbTermOfUse;
    TextView tvLogin;
    Registration registration;

    public RegisterFragment01() {
        // Required empty public constructor
    }

    public interface DataFromFragment01 {
        void FromFragment01(String data, int number);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_01, container, false);

        getCodeButton = root.findViewById(R.id.get_code);
        phoneNumber = root.findViewById(R.id.et_phone_number);
        countryCode = root.findViewById(R.id.country_code);
        tvLogin = root.findViewById(R.id.login_tv);
        cbTermOfUse = root.findViewById(R.id.cb_term_of_use);

        dataPasser.FromFragment01("Регистрация", 0);

        Spinner countriesSpinner = root.findViewById(R.id.countries_spinner);

        Countries countries = new Countries(requireActivity().getApplicationContext());

        countries.setOnLoadListener(data -> {
            countriesSpinner.setAdapter(new CountriesAdapter(requireActivity().getApplicationContext(), data));
            countriesSpinner.setSelection(0);
        });

        phoneNumber.addTextChangedListener(new PhoneTextFormatter(phoneNumber, "(###) ###-##-##"));

        tvLogin.setOnClickListener(v -> {
            requireActivity().startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });

        getCodeButton.setOnClickListener(v -> {

            if (cbTermOfUse.isChecked()) {
                registration = new Registration(requireContext(), requireActivity(), countryCode, phoneNumber);

                registration.setOnLoadListener((resultCode, resultMessage, userID) -> {
                    if (resultCode == 1) {
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.register_fragment, new RegisterFragment02()).commit();
                    } else {
                        Toast.makeText(getContext(), "Номер телефона уже существует", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Подтвердите Ваше согласие", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        return root;
    }

    DataFromFragment01 dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromFragment01) context;
    }
}