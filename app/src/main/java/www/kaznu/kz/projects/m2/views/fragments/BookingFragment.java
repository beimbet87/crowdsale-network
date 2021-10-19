package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.BookingAdapter;
import www.kaznu.kz.projects.m2.api.realty.FilterOffers;
import www.kaznu.kz.projects.m2.api.realty.GetOffer;
import www.kaznu.kz.projects.m2.api.user.UserInfo;
import www.kaznu.kz.projects.m2.api.user.UserInfoWithID;
import www.kaznu.kz.projects.m2.interfaces.ClickListener;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.models.User;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.views.activities.DiscussionAdminActivity;
import www.kaznu.kz.projects.m2.views.activities.RealtyActivity;

public class BookingFragment extends Fragment {

    CurrentUser user;

    RecyclerView rvBookingAccepted, rvBookingHistory;
    BookingAdapter adapterBookingAccepted, adapterBookingHistory;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root;
        user = new CurrentUser(requireContext());

        if(user.getClientBooks().size() > 0 || user.getClientBooksHistory().size() > 0) {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_booking, container, false);

            rvBookingAccepted = root.findViewById(R.id.lv_accepted_booking);
            rvBookingHistory = root.findViewById(R.id.lv_booking_history);

            rvBookingAccepted.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvBookingHistory.setLayoutManager(new LinearLayoutManager(requireContext()));

            adapterBookingAccepted = new BookingAdapter(user.getClientBooks());
            adapterBookingHistory = new BookingAdapter(user.getClientBooksHistory());

            adapterBookingAccepted.setOnClickListener(new ClickListener() {
                @Override
                public void onClick(int index, int buttonId) {

                }

                @Override
                public void onItemClick(int position, View v) {

                }

                @Override
                public void onItemLongClick(int position, View v) {

                }
            });

            adapterBookingHistory.setOnClickListener(new ClickListener() {
                @Override
                public void onClick(int index, int buttonId) {

                }

                @Override
                public void onItemClick(int position, View v) {

                    new GetOffer(requireContext(), user.getClientBooksHistory().get(position).getRefRealty(), new Tokens(requireContext()).getAccessToken()).setOnLoadListener(new GetOffer.CustomOnLoadListener() {
                        @Override
                        public void onComplete(Offers offers) {

                            Intent realtyIntent = new Intent(requireContext(), RealtyActivity.class);
                            realtyIntent.putExtra("images", offers.getImagesLink());
                            realtyIntent.putExtra("title", offers.getRealty().getHeader());
                            realtyIntent.putExtra("address", offers.getRealty().getAddress());
                            realtyIntent.putExtra("price", offers.getRealty().getCost());
                            realtyIntent.putExtra("owner", offers.getOwner().getName());
                            realtyIntent.putExtra("stars", offers.getOwner().getStars());
                            realtyIntent.putExtra("avatar", offers.getOwner().getImageLink());
                            if (offers.getRealty().getDescription() != null) {
                                realtyIntent.putExtra("body", offers.getRealty().getDescription());
                            } else {
                                realtyIntent.putExtra("body", "null");
                            }
                            realtyIntent.putExtra("floor", offers.getRealty().getFloor());
                            realtyIntent.putExtra("floorbuild", offers.getRealty().getFloorBuild());
                            realtyIntent.putExtra("area", offers.getRealty().getArea());
                            realtyIntent.putExtra("livingspace", offers.getRealty().getLivingSpace());
                            realtyIntent.putExtra("ref_realty", offers.getRealty().getId());
                            realtyIntent.putExtra("contact", offers.getOwner().getId());
                            realtyIntent.putIntegerArrayListExtra("properties", offers.getProperties());

                            startActivity(realtyIntent);
                        }
                    });
                }

                @Override
                public void onItemLongClick(int position, View v) {

                }
            });

            rvBookingAccepted.setAdapter(adapterBookingAccepted);

            rvBookingHistory.setAdapter(adapterBookingHistory);

        } else {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_booking_empty, container, false);
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
