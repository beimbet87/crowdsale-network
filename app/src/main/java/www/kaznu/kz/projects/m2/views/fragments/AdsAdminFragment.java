package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.activities.RealtyAddActivity;

public class AdsAdminFragment extends Fragment {
    Button searchButton;
    public AdsAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_advert_empty, container, false);

        searchButton = rootView.findViewById(R.id.btn_create_advert);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RealtyAddActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
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
