package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
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
import www.kaznu.kz.projects.m2.api.user.IdentityConfirmation;
import www.kaznu.kz.projects.m2.api.user.RegistrationAuth;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.RegistrationStep1;
import www.kaznu.kz.projects.m2.models.RegistrationStep2;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;

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

                        Toast.makeText(requireContext(), userId + "", Toast.LENGTH_SHORT).show();

                        RegistrationStep2 data2 = new RegistrationStep2();
                        data2.setUserIdentity(etEmail.getText().toString());
                        data2.setAffirmationCode(userId);

                        IdentityConfirmation confirm = new IdentityConfirmation(requireContext(), requireActivity(), data2, new Tokens(requireContext()).getAccessToken());
                        confirm.setOnLoadListener(new IdentityConfirmation.CustomOnLoadListener() {
                            @Override
                            public void onComplete(int resultCode, String resultMessage) {
                                Toast.makeText(requireContext(), "Email успешно изменен!", Toast.LENGTH_SHORT).show();
                            }
                        });
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