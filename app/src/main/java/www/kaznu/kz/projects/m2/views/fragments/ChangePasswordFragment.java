package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {

    }

    public interface DataFromChangePasswordFragment {
        void FromChangePasswordFragment(String data, int number);
    }

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_change_password, container, false);

        dataPasser.FromChangePasswordFragment("Смена пароля", 2);

        return fv;
    }

    DataFromChangePasswordFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromChangePasswordFragment) context;
    }

}