package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.views.activities.OfferAdminActivity;
import www.kaznu.kz.projects.m2.views.activities.RealtyAddActivity;
import www.kaznu.kz.projects.m2.views.activities.RealtyEditActivity;
import www.kaznu.kz.projects.m2.adapters.PublishedAdsAdapter;
import www.kaznu.kz.projects.m2.adapters.UnpublishedAdsAdapter;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;

import static android.app.Activity.RESULT_OK;

public class AdvertListFragment extends Fragment {

    Button btnCreateAdvert;

    public AdvertListFragment() {
        // Required empty public constructor
    }

    RecyclerView lvPublished, lvUnpublished;

    PublishedAdsAdapter laPublished;
    UnpublishedAdsAdapter laUnpublished;

    ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root;
        ArrayList<Offers> published;
        ArrayList<Offers> unpublished;

        CurrentUser user = new CurrentUser(requireContext());

        if (user.getPublishedAdvertList().size() > 0 || user.getUnpublishedAdvertList().size() > 0) {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_advert_list, container, false);

            published = user.getPublishedAdvertList();
            unpublished = user.getUnpublishedAdvertList();

            lvPublished = root.findViewById(R.id.lv_published_ads);
            lvUnpublished = root.findViewById(R.id.lv_unpublished_ads);

            lvPublished.setNestedScrollingEnabled(false);
            lvUnpublished.setNestedScrollingEnabled(false);

            progressBar = root.findViewById(R.id.post_progress);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
            laPublished = new PublishedAdsAdapter(requireContext(), published);

            lvPublished.setLayoutManager(new LinearLayoutManager(requireContext()));
            lvPublished.setAdapter(laPublished);
            laPublished.notifyDataSetChanged();
            lvPublished.setItemViewCacheSize(16);

            laPublished.setOnItemClickListener(new PublishedAdsAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent intent = new Intent(requireActivity(), OfferAdminActivity.class);
                    intent.putExtra("offers", (Parcelable) published.get(position));
                    intent.putExtra("realty", (Serializable) published.get(position).getRealty());
                    intent.putExtra("owner", (Serializable) published.get(position).getOwner());
                    intent.putExtra("properties", published.get(position).getProperties());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position, View v) {

                }
            });

            laUnpublished = new UnpublishedAdsAdapter(requireContext(), unpublished);

            lvUnpublished.setLayoutManager(new LinearLayoutManager(requireContext()));
            lvUnpublished.setAdapter(laUnpublished);
            laUnpublished.notifyDataSetChanged();
            lvUnpublished.setItemViewCacheSize(16);
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
            laUnpublished.setOnItemClickListener(new UnpublishedAdsAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent intent = new Intent(requireActivity(), RealtyEditActivity.class);
                    Realty realty = unpublished.get(position).getRealty();
                    intent.putExtra("realty", realty);
                    intent.putExtra("images", unpublished.get(position).getImagesLink());
                    intent.putExtra("property", unpublished.get(position).getProperties());
                    intent.putExtra("offers", unpublished.get(position).getOffersOptionsId());
                    startActivityForResult(intent, 1);
                }

                @Override
                public void onItemLongClick(int position, View v) {

                }
            });

        } else {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_advert_empty, container, false);
        }

        btnCreateAdvert = root.findViewById(R.id.btn_create_advert);

        btnCreateAdvert.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RealtyAddActivity.class);
            startActivity(intent);
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                laPublished.notifyDataSetChanged();
                laUnpublished.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        laPublished.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
