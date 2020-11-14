package www.kaznu.kz.projects.m2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.radiobutton.MaterialRadioButton;

import www.kaznu.kz.projects.m2.R;

public class RegisterFragment04 extends Fragment {

    Button btnNext;
    RadioButton rbGuest, rbMaster;
    int profileType = 0;

    public interface DataFromFragment04 {
        void FromFragment04(String data, int number);
    }

    public RegisterFragment04() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_register_04, container, false);

        btnNext = fv.findViewById(R.id.btn_next);
        rbGuest = fv.findViewById(R.id.rb_guest);
        rbMaster = fv.findViewById(R.id.rb_master);

        dataPasser.FromFragment04("Приветствуем!", 3);
        rbGuest.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                profileType = 0;
            }
        });

        rbMaster.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                profileType = 1;
            }
        });

        btnNext.setOnClickListener(v -> {
            SharedPreferences userPreferences = requireActivity().getSharedPreferences("M2_REG_INFO", 0);
            SharedPreferences.Editor editor = userPreferences.edit();
            editor.putInt("profileType", profileType);
            editor.apply();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.register_fragment, new RegisterFragment05()).commit();
        });

        return fv;
    }

    DataFromFragment04 dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromFragment04) context;
    }

}