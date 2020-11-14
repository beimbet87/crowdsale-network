package www.kaznu.kz.projects.m2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;

public class RegisterFragment06 extends Fragment {


    Button nextButton;
    EditText etRegEmail;
    String regPassword, reRegPassword;

    public RegisterFragment06() {
        // Required empty public constructor
    }
    public interface DataFromFragment06 {
        public void DataFromFragment06(String data, int number);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_register_06, container, false);

        nextButton = fv.findViewById(R.id.btn_next);
        etRegEmail = fv.findViewById(R.id.et_reg_email);

        dataPasser.DataFromFragment06("Основная информация", 5);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences userPreferences = getActivity().getSharedPreferences("M2_REG_INFO", 0);
                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putString("email", etRegEmail.getText().toString());
                editor.apply();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.register_fragment, new RegisterFragment07()).commit();
            }
        });

        return fv;
    }

    DataFromFragment06 dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataFromFragment06) context;
    }
}