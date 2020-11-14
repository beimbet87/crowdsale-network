package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.RealtyAddActivity;
import www.kaznu.kz.projects.m2.adapters.PublishedAdsAdapter;
import www.kaznu.kz.projects.m2.adapters.UnpublishedAdsAdapter;

public class ListAdsAdminFragment extends Fragment {
    Button searchButton;
    public ListAdsAdminFragment() {
        // Required empty public constructor
    }

    String[] titles = {
            "Уютная 2к квартира..",
            "Уютная 2к квартира в це.."
    };

    int[] images = {
            R.drawable.message_icon0,
            R.drawable.message_icon1
    };

    String[] address = {
            "Алматы, Некрасова 32А",
            "Астана,  ул. Лиховец 70/2"
    };

    String[] prices = {
            "7 900 000 ₸",
            "6 300 000 ₸"
    };

    String[] titlesPublished = {
            "Уютная 2к квартира в це.."
    };

    int[] imagesPublished = {
            R.drawable.message_icon1
    };

    String[] addressPublished = {
            "Астана,  ул. Лиховец 70/2"
    };

    String[] pricesPublished = {
            "6 300 000 ₸"
    };

    ListView lvPublished, lvUnpublished;

    ListAdapter laPublished, laUnpublished;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_post_ads_admin, container, false);

        searchButton = rootView.findViewById(R.id.btn_create_ads);

        lvPublished = rootView.findViewById(R.id.lv_published_ads);
        lvUnpublished = rootView.findViewById(R.id.lv_unpublished_ads);

        laPublished = new PublishedAdsAdapter(getContext(), titlesPublished, addressPublished, pricesPublished, imagesPublished);
        laUnpublished = new UnpublishedAdsAdapter(getContext(), titles, address, prices, images);

        lvUnpublished.setAdapter(laUnpublished);
        lvPublished.setAdapter(laPublished);

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
