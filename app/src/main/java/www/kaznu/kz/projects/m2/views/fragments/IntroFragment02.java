package www.kaznu.kz.projects.m2.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.utils.Utils;

public class IntroFragment02 extends Fragment implements View.OnClickListener {

    Button nextButton;

    public IntroFragment02() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intro_02, container, false);

        nextButton = root.findViewById(R.id.btn_next);

        nextButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.btn_next) {
//            Utils.replaceFragment(requireActivity(), new IntroFragment03(), R.id.intro_fragment);
//        }
    }
}