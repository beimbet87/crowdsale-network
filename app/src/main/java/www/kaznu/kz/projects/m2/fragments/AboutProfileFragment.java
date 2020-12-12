package www.kaznu.kz.projects.m2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;

public class AboutProfileFragment extends Fragment implements Constants {
    Button btnComplete;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    CurrentUser user;

    public AboutProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_about_profile, container, false);
        btnComplete = fv.findViewById(R.id.btn_complete);

        user = new CurrentUser(requireContext());

        btnComplete.setOnClickListener(v -> {

            requireActivity().finish();
        });

        return fv;
    }
}