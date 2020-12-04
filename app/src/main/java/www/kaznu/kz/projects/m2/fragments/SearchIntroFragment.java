package www.kaznu.kz.projects.m2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;

public class SearchIntroFragment extends Fragment {

    private Button btnChange, btnCancel;

    public SearchIntroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_search_intro, container, false);

        btnChange = root.findViewById(R.id.btn_change_search);
        btnCancel = root.findViewById(R.id.btn_cancel_search);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
