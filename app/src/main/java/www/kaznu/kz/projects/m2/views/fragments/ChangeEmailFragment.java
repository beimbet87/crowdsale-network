package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.user.RegistrationAuth;
import www.kaznu.kz.projects.m2.models.RegistrationStep1;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class ChangeEmailFragment extends Fragment implements View.OnClickListener {

    Button btnGetCode;
    EditText etEmail;
    @Override
    public void onClick(View v) {

    }

    public interface DataFromChangeEmailFragment {
        void FromChangeEmailFragment(String data, int number);
    }

    public ChangeEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_change_email, container, false);

        btnGetCode = fv.findViewById(R.id.btn_get_code);
        etEmail = fv.findViewById(R.id.profile_email);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationStep1 data = new RegistrationStep1();
                data.setRegIdentity(etEmail.getText().toString());
                data.setCountryCode("");
                RegistrationAuth auth = new RegistrationAuth(requireContext(), requireActivity(), data, new Tokens(requireContext()).getAccessToken());
                auth.setOnLoadListener(new RegistrationAuth.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int resultCode, String resultMessage, int userId) {

                        new TinyDB(requireContext()).putString("regIdentity", etEmail.getText().toString());

                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.change_data, new ConfirmationEmailFragment()).commit();
                    }
                });
            }
        });

        dataPasser.FromChangeEmailFragment("Смена email", 1);

        return fv;
    }

    DataFromChangeEmailFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromChangeEmailFragment) context;
    }

}