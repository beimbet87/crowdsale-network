package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.OfferActivity;
import www.kaznu.kz.projects.m2.activities.RealtyAddActivity;
import www.kaznu.kz.projects.m2.adapters.PublishedAdsAdapter;
import www.kaznu.kz.projects.m2.adapters.SearchAdapter;
import www.kaznu.kz.projects.m2.adapters.UnpublishedAdsAdapter;
import www.kaznu.kz.projects.m2.api.realty.UserApplications;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.models.Tokens;

public class ListAdsAdminFragment extends Fragment {
    Button btnAddAds;

    public ListAdsAdminFragment() {
        // Required empty public constructor
    }

    RecyclerView lvPublished, lvUnpublished;

    UserApplications published, unpublished;

    PublishedAdsAdapter laPublished;
    UnpublishedAdsAdapter laUnpublished;

    ProgressBar progressBar;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_post_ads_admin, container, false);

        btnAddAds = rootView.findViewById(R.id.btn_create_post);

        lvPublished = rootView.findViewById(R.id.lv_published_ads);
        lvUnpublished = rootView.findViewById(R.id.lv_unpublished_ads);

        progressBar = rootView.findViewById(R.id.post_progress);

        published = new UserApplications(requireContext(), 1, new Tokens(requireContext()).getAccessToken());

        published.setOnLoadListener(new UserApplications.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Offers> offers) {

                Log.d(Constants.TAG, "onComplete: " + offers.size());
                laPublished = new PublishedAdsAdapter(requireContext(), offers);

                lvPublished.setLayoutManager(new LinearLayoutManager(requireContext()));
                lvPublished.setAdapter(laPublished);
                laPublished.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                lvPublished.setItemViewCacheSize(16);

                laPublished.setOnItemClickListener(new PublishedAdsAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                    }

                    @Override
                    public void onItemLongClick(int position, View v) {

                    }
                });
            }
        });

        unpublished = new UserApplications(requireContext(), 0, new Tokens(requireContext()).getAccessToken());

        unpublished.setOnLoadListener(new UserApplications.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Offers> offers) {
                Log.d(Constants.TAG, "onComplete: " + offers.size());
                laUnpublished = new UnpublishedAdsAdapter(requireContext(), offers);

                lvUnpublished.setLayoutManager(new LinearLayoutManager(requireContext()));
                lvUnpublished.setAdapter(laUnpublished);
                laUnpublished.notifyDataSetChanged();
                lvUnpublished.setItemViewCacheSize(16);

                laUnpublished.setOnItemClickListener(new UnpublishedAdsAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                    }

                    @Override
                    public void onItemLongClick(int position, View v) {

                    }
                });
            }
        });

        btnAddAds.setOnClickListener(new View.OnClickListener() {
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
