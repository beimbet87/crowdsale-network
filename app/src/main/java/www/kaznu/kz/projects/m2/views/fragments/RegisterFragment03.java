package www.kaznu.kz.projects.m2.views.fragments;

import static www.kaznu.kz.projects.m2.interfaces.Constants.SHARED_PASSWORD;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class RegisterFragment03 extends Fragment {

    Button nextButton;
    EditText etRegPassword, etRegRePassword;
    TextView tvError;
    String regPassword, reRegPassword;

    public RegisterFragment03() {
        // Required empty public constructor
    }
    public interface DataFromFragment03 {
        void FromFragment03(String data, int number);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_register_03, container, false);

        nextButton = fv.findViewById(R.id.btn_next);
        etRegPassword = fv.findViewById(R.id.et_reg_password);
        etRegRePassword = fv.findViewById(R.id.et_reg_repassword);
        tvError = fv.findViewById(R.id.tv_error);

        TinyDB data = new TinyDB(requireContext());

        dataPasser.FromFragment03("Завершение регистрации", 2);

        nextButton.setOnClickListener(v -> {
            regPassword = etRegPassword.getText().toString();
            reRegPassword = etRegRePassword.getText().toString();

            if(regPassword.equals(reRegPassword)) {
                data.putString(SHARED_PASSWORD, regPassword);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.register_fragment, new RegisterFragment04()).commit();
            } else {
                tvError.setText("Пароли не совпадают");
                etRegRePassword.setBackgroundResource(R.drawable.intro_input_phone_error_background);
            }
        });

        return fv;
    }

    DataFromFragment03 dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromFragment03) context;
    }
}