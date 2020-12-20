package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.activities.LoginActivity;
import www.kaznu.kz.projects.m2.api.user.Token;

public class RegisterFragment02 extends Fragment {

    String code;

    Button btnNext;
    TextView btnLogin;
    EditText confirmNum01, confirmNum02, confirmNum03, confirmNum04;
    TextView tvPhoneNumber;
    Token token;

    public RegisterFragment02() {
    }

    public interface DataFromFragment02 {
        void FromFragment02(String data, int number );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_register_02, container, false);

        btnNext = fv.findViewById(R.id.btn_next);
        btnLogin = fv.findViewById(R.id.tv_login);

        confirmNum01 = fv.findViewById(R.id.et_confirm_01);
        confirmNum02 = fv.findViewById(R.id.et_confirm_02);
        confirmNum03 = fv.findViewById(R.id.et_confirm_03);
        confirmNum04 = fv.findViewById(R.id.et_confirm_04);

        dataPasser.FromFragment02("Регистрация", 1);

        btnLogin.setOnClickListener(v -> {
            requireActivity().startActivity(new Intent(requireActivity().getApplicationContext(), LoginActivity.class));
            requireActivity().finish();
        });

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
                    InputMethodManager imm = (InputMethodManager) confirmNum04.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(confirmNum04.getWindowToken(), 0);
                    }
                }
            }
        });

        tvPhoneNumber = fv.findViewById(R.id.phone_tv);

        SharedPreferences temp;
        temp = requireActivity().getSharedPreferences("M2_REG_INFO", 0);

        final String phone = temp.getString("regIdentity", "");

        tvPhoneNumber.setText(phone);

        btnNext.setOnClickListener(v -> {
            code = confirmNum01.getText().toString() +
                    confirmNum02.getText().toString() +
                    confirmNum03.getText().toString() +
                    confirmNum04.getText().toString();

            token = new Token(requireContext(), requireActivity(), phone, code);

            token.setOnLoadListener(token -> requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.register_fragment, new RegisterFragment03()).commit());
        });

        return fv;
    }

    DataFromFragment02 dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromFragment02) context;
    }
}