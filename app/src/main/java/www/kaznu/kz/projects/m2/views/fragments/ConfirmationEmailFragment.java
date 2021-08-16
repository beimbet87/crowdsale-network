package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.user.IdentityConfirmation;
import www.kaznu.kz.projects.m2.api.user.RegisterToken;
import www.kaznu.kz.projects.m2.models.RegistrationStep2;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class ConfirmationEmailFragment extends Fragment {

    String code;

    Button btnConfirm;
    EditText confirmNum01, confirmNum02, confirmNum03, confirmNum04;
    TextView tvConfirmationNumber;
    RegisterToken registerToken;

    public ConfirmationEmailFragment() {
    }

    public interface DataFromConfirmationFragment {
        void FromConfirmationFragment(String data, int number);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_confirmation_email, container, false);

        btnConfirm = fv.findViewById(R.id.btn_next);

        confirmNum01 = fv.findViewById(R.id.et_confirm_01);
        confirmNum02 = fv.findViewById(R.id.et_confirm_02);
        confirmNum03 = fv.findViewById(R.id.et_confirm_03);
        confirmNum04 = fv.findViewById(R.id.et_confirm_04);

        dataPasser.FromConfirmationFragment("Подтверждение", 3);

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

        tvConfirmationNumber = fv.findViewById(R.id.confirmation_tv);

        final String confirmationNumber = new TinyDB(requireContext()).getString("regIdentity");

        tvConfirmationNumber.setText(confirmationNumber);

        btnConfirm.setOnClickListener(v -> {
            code = confirmNum01.getText().toString() +
                    confirmNum02.getText().toString() +
                    confirmNum03.getText().toString() +
                    confirmNum04.getText().toString();

            RegistrationStep2 data2 = new RegistrationStep2();
            data2.setUserIdentity(confirmationNumber);
            data2.setAffirmationCode(Integer.parseInt(code));

            IdentityConfirmation confirm = new IdentityConfirmation(requireContext(), requireActivity(), data2, new Tokens(requireContext()).getAccessToken());
            confirm.setOnLoadListener(new IdentityConfirmation.CustomOnLoadListener() {
                @Override
                public void onComplete(int resultCode, String resultMessage) {
                    if(resultCode == 1) {
                        Toast.makeText(requireContext(), "Email успешно изменен!", Toast.LENGTH_SHORT).show();
                        requireActivity().finish();
                    }
                    else {
                        Toast.makeText(requireContext(), "Введите правильный код!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        return fv;
    }

    DataFromConfirmationFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromConfirmationFragment) context;
    }
}