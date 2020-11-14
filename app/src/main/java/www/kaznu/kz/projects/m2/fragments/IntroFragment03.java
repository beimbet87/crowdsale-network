package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.LoginActivity;

public class IntroFragment03 extends Fragment implements View.OnClickListener {

    SharedPreferences introPreferences;

    Button startButton;

    public IntroFragment03() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intro_03, container, false);

        startButton = root.findViewById(R.id.btn_start);

        startButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_start) {
            introPreferences = requireActivity().getSharedPreferences("M2_INTRO", 0);

            SharedPreferences.Editor editor = introPreferences.edit();
            editor.putBoolean("isIntro", false);
            editor.apply();

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            requireActivity().startActivity(intent);
        }
    }
}