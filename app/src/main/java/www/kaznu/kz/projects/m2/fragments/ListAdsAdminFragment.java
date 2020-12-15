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

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.OfferAdminActivity;
import www.kaznu.kz.projects.m2.activities.RealtyAddActivity;
import www.kaznu.kz.projects.m2.activities.RealtyEditActivity;
import www.kaznu.kz.projects.m2.adapters.PublishedAdsAdapter;
import www.kaznu.kz.projects.m2.adapters.UnpublishedAdsAdapter;
import www.kaznu.kz.projects.m2.api.realty.UserApplications;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;

public class ListAdsAdminFragment extends Fragment {

    Button btnAddRealty;

    public ListAdsAdminFragment() {
        // Required empty public constructor
    }

    RecyclerView lvPublished, lvUnpublished;

    UserApplications published, unpublished;

    PublishedAdsAdapter laPublished;
    UnpublishedAdsAdapter laUnpublished;

    ProgressBar progressBar;

    Logger Log;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_post_ads_admin, container, false);

        btnAddRealty = rootView.findViewById(R.id.btn_create_post);

        lvPublished = rootView.findViewById(R.id.lv_published_ads);
        lvUnpublished = rootView.findViewById(R.id.lv_unpublished_ads);

        lvPublished.setNestedScrollingEnabled(false);
        lvUnpublished.setNestedScrollingEnabled(false);

        progressBar = rootView.findViewById(R.id.post_progress);
        progressBar.setIndeterminate(true);
        published = new UserApplications(requireContext(), 1, new Tokens(requireContext()).getAccessToken());

        published.setOnLoadListener(new UserApplications.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Offers> offers) {

                laPublished = new PublishedAdsAdapter(requireContext(), offers);

                lvPublished.setLayoutManager(new LinearLayoutManager(requireContext()));
                lvPublished.setAdapter(laPublished);
                laPublished.notifyDataSetChanged();
                lvPublished.setItemViewCacheSize(16);

                laPublished.setOnItemClickListener(new PublishedAdsAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Intent intent = new Intent(requireActivity(), OfferAdminActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(int position, View v) {

                    }
                });

                unpublished = new UserApplications(requireContext(), 0, new Tokens(requireContext()).getAccessToken());

                unpublished.setOnLoadListener(new UserApplications.CustomOnLoadListener() {
                    @Override
                    public void onComplete(ArrayList<Offers> offers) {

                        laUnpublished = new UnpublishedAdsAdapter(requireContext(), offers);

                        lvUnpublished.setLayoutManager(new LinearLayoutManager(requireContext()));
                        lvUnpublished.setAdapter(laUnpublished);
                        laUnpublished.notifyDataSetChanged();
                        lvUnpublished.setItemViewCacheSize(16);
                        progressBar.setIndeterminate(false);
                        laUnpublished.setOnItemClickListener(new UnpublishedAdsAdapter.ClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Intent intent = new Intent(requireActivity(), RealtyEditActivity.class);
                                Realty realty = offers.get(position).getRealty();
                                intent.putExtra("realty", realty);
                                intent.putExtra("images", offers.get(position).getImagesLink());
                                intent.putExtra("property", offers.get(position).getProperties());
                                intent.putExtra("offers", offers.get(position).getOffersOptionsId());
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(int position, View v) {

                            }
                        });
                    }
                });

            }
        });


        btnAddRealty.setOnClickListener(new View.OnClickListener() {
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
