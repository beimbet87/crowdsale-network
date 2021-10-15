package www.kaznu.kz.projects.m2.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.BookingAdapter;
import www.kaznu.kz.projects.m2.models.CurrentUser;

public class BookingFragment extends Fragment {

    CurrentUser user;

    ListView lView, acceptedLView;

    ListAdapter lAdapter, acceptedLAdapter;

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

            lView = root.findViewById(R.id.lv_accepted_booking);
            acceptedLView = root.findViewById(R.id.lv_booking_history);

            lAdapter = new BookingAdapter(requireContext(), user.getClientBooks());
            acceptedLAdapter = new BookingAdapter(requireContext(), user.getClientBooksHistory());

            lView.setAdapter(lAdapter);

            lView.setOnItemClickListener((adapterView, view, i, l) -> {
            });

            acceptedLView.setAdapter(acceptedLAdapter);

            acceptedLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });

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
