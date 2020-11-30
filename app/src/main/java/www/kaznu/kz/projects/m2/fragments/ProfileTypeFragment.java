package www.kaznu.kz.projects.m2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;

public class ProfileTypeFragment extends Fragment implements View.OnClickListener {

    Button btnNext;
    RadioButton rbGuest, rbMaster;
    int profileType = 0;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rb_guest) {
            rbGuest.setChecked(true);
            rbMaster.setChecked(false);
        } else {
            rbGuest.setChecked(false);
            rbMaster.setChecked(true);
        }
    }

    public interface DataFromProfileTypeFragment {
        void FromProfileTypeFragment(String data, int number);
    }

    public ProfileTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_profile_type, container, false);

        btnNext = fv.findViewById(R.id.btn_next);
        rbGuest = fv.findViewById(R.id.rb_guest);
        rbMaster = fv.findViewById(R.id.rb_master);

        rbGuest.setOnClickListener(this);
        rbMaster.setOnClickListener(this);

        dataPasser.FromProfileTypeFragment("Приветствуем!", 3);

        rbGuest.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                profileType = 0;
            }
        });

        rbMaster.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                profileType = 1;
            }
        });

        btnNext.setOnClickListener(v -> {
            SharedPreferences userPreferences = requireActivity().getSharedPreferences("M2_REG_INFO", 0);
            SharedPreferences.Editor editor = userPreferences.edit();
            editor.putInt("profileType", profileType);
            editor.apply();
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return fv;
    }

    DataFromProfileTypeFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromProfileTypeFragment) context;
    }

}