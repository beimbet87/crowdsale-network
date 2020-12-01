package www.kaznu.kz.projects.m2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class ChangeEmailFragment extends Fragment implements View.OnClickListener {

    private Logger Log;

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

        Log = new Logger(requireContext(), Constants.TAG);

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