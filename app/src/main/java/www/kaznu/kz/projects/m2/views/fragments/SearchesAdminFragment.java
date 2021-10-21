package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.BookingAdapterAdmin;
import www.kaznu.kz.projects.m2.adapters.OfferAdminAdapter;
import www.kaznu.kz.projects.m2.adapters.RealtySearchesAdapterAdmin;
import www.kaznu.kz.projects.m2.api.searches.SearchesForOwnersRealty;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.RealtySearch;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.views.activities.DiscussionAdminActivity;

public class SearchesAdminFragment extends Fragment {

    RecyclerView lView, acceptedLView;
    ProgressBar progressBar;

    CurrentUser user;

    TextView tvCurrentDate;

    RealtySearchesAdapterAdmin lAdapter;

    public SearchesAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup root;

        user = new CurrentUser(requireContext());

        if (user.getScheduleSection().size() > 0) {

            root = (ViewGroup) inflater.inflate(R.layout.fragment_searches_admin, container, false);

            progressBar = root.findViewById(R.id.pb_message_list);

            lView = root.findViewById(R.id.lv_accepted_booking_admin);
            lView.setLayoutManager(new LinearLayoutManager(requireContext()));

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            new SearchesForOwnersRealty(requireContext(), new Tokens(requireContext()).getAccessToken()).setOnLoadListener(new SearchesForOwnersRealty.CustomOnLoadListener() {
                @Override
                public void onComplete(ArrayList<RealtySearch> searches) {
                    Logger.d("Search data: " + searches.toString());
                    lAdapter = new RealtySearchesAdapterAdmin(searches);

                    lView.setAdapter(lAdapter);

                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);

                    lAdapter.setOnItemClickListener(new RealtySearchesAdapterAdmin.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Intent intent = new Intent(requireContext(), DiscussionAdminActivity.class);
                            intent.putExtra("contact", searches.get(position).getRefUser());
                            intent.putExtra("ref_realty", searches.get(position).getRefRealty());
                            intent.putExtra("owner", true);

                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(int position, View v) {

                        }
                    });

                }
            });

        } else {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_searches_admin_empty, container, false);
        }

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
