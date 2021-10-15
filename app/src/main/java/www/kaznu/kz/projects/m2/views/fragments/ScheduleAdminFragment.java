package www.kaznu.kz.projects.m2.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.BookingAdapterAdmin;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.ScheduleSection;
import www.kaznu.kz.projects.m2.utils.Utils;

public class ScheduleAdminFragment extends Fragment {

    RecyclerView lView, acceptedLView;

    CurrentUser user;

    TextView tvCurrentDate;

    BookingAdapterAdmin lAdapter;

    public ScheduleAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup root;

        user = new CurrentUser(requireContext());

        if (user.getScheduleSection().size() > 0) {

            root = (ViewGroup) inflater.inflate(R.layout.fragment_schedule_admin, container, false);

            ArrayList<ScheduleSection> result = user.getScheduleSection();

            lView = root.findViewById(R.id.lv_accepted_booking_admin);
            lView.setLayoutManager(new LinearLayoutManager(requireContext()));

            lAdapter = new BookingAdapterAdmin(result);

            lView.setAdapter(lAdapter);
        } else {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_schedule_admin_empty, container, false);
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
