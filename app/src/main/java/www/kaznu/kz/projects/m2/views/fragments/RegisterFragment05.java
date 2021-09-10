package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.activities.LoginActivity;
import www.kaznu.kz.projects.m2.api.user.RegistrationForm;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.User;

public class RegisterFragment05 extends Fragment implements Constants {

    Button btnRegister;
    EditText etFirstName, etLastName, etDateOfBirth;

    SharedPreferences temp;
    String token;

    RegistrationForm registrationForm;

    public RegisterFragment05() {
        // Required empty public constructor
    }

    public interface DataFromFragment05 {
        void FromFragment05(String data, int number);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_05, container, false);

        btnRegister = root.findViewById(R.id.btn_next);
        etFirstName = root.findViewById(R.id.et_reg_fname);
        etLastName = root.findViewById(R.id.et_reg_lname);
        etDateOfBirth = root.findViewById(R.id.et_reg_dateofbirth);

        dataPasser.FromFragment05("Основная информация", 4);

        btnRegister.setOnClickListener(v -> {

            User user = new User();
            SharedPreferences spToken = requireActivity().getSharedPreferences("M2_TOKEN", 0);
            token = spToken.getString("access_token", "");

            temp = requireActivity().getSharedPreferences("M2_REG_INFO", 0);

            user.setPhone(temp.getString("phone", ""));
            user.setName(etFirstName.getText().toString());
            user.setSurname(etLastName.getText().toString());
            user.setBirth(etDateOfBirth.getText().toString());
            user.setPassword(temp.getString("password", ""));
            user.setProfileType(temp.getInt("profileType", 1));
            user.setSex(1);
            user.setDescription("");

            registrationForm = new RegistrationForm(requireContext(), requireActivity(), user, token);
            registrationForm.setOnLoadListener((resultCode, resultMessage) -> {
                if (resultCode == 1) {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                    requireActivity().finish();
                    Toast.makeText(requireActivity().getApplicationContext(),
                            "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();

                }
            });
        });

        return root;
    }

    DataFromFragment05 dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromFragment05) context;
    }
}