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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.utils.TinyDB;
import www.kaznu.kz.projects.m2.views.activities.LoginActivity;
import www.kaznu.kz.projects.m2.api.user.RegistrationForm;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.User;

public class RegisterFragment05 extends Fragment implements Constants {

    Button btnRegister;
    EditText etFirstName, etLastName, etDateOfBirth;

    String token;

    private ProgressBar mProgressBar;

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

        mProgressBar = root.findViewById(R.id.pb_loading);

        mProgressBar.setIndeterminate(false);
        mProgressBar.setVisibility(View.GONE);

        TinyDB data = new TinyDB(requireContext());

        dataPasser.FromFragment05("Основная информация", 4);

        btnRegister.setOnClickListener(v -> {

            mProgressBar.setIndeterminate(true);
            mProgressBar.setVisibility(View.VISIBLE);

            User user = new User();
            token = data.getString(SHARED_ACCESS_TOKEN);

            user.setPhone(data.getString(SHARED_REG_IDENTITY));
            user.setName(etFirstName.getText().toString());
            user.setSurname(etLastName.getText().toString());
            user.setBirth(etDateOfBirth.getText().toString());
            user.setPassword(data.getString(SHARED_PASSWORD));
            user.setProfileType(data.getInt(SHARED_PROFILE_TYPE));
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
                else {
                    Toast.makeText(requireActivity().getApplicationContext(),
                            "Ошибка при регистраций!", Toast.LENGTH_SHORT).show();
                }

                mProgressBar.setIndeterminate(false);
                mProgressBar.setVisibility(View.GONE);
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