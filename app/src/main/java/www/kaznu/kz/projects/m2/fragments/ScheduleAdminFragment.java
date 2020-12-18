package www.kaznu.kz.projects.m2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import okhttp3.internal.Util;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.BookingAdapter;
import www.kaznu.kz.projects.m2.adapters.BookingAdapterAdmin;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.BookingApplication;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.ScheduleSection;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;

public class ScheduleAdminFragment extends Fragment {

    RecyclerView lView, acceptedLView;

    CurrentUser user;
    Logger Log;

    TextView tvCurrentDate;

    BookingAdapterAdmin lAdapter;

    public ScheduleAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_schedule_admin, container, false);

        user = new CurrentUser(requireContext());

        tvCurrentDate = rootView.findViewById(R.id.tv_date_now);

        tvCurrentDate.setText("СЕГОДНЯ - ".concat(Utils.getCurrentFullDate()));

        Log = new Logger(requireContext(), Constants.TAG);

        ArrayList<ScheduleSection> result = user.getScheduleSection();

//        ArrayList<BookingApplication> sectionData = new ArrayList<>();
//
//        for (int i = 1; i < user.getOwnersBooksHistory().size(); i++) {
//
//            String preDate = Utils.parseDateWithDot(user.getOwnersBooksHistory().get(i - 1).getTimeStart());
//            String curDate = Utils.parseDateWithDot(user.getOwnersBooksHistory().get(i).getTimeStart());
//
//            if(preDate.equals(curDate)) {
//                sectionData.add(user.getOwnersBooksHistory().get(i-1));
//                Log.d("Compare -----> " + preDate + " and " + curDate);
//
//                if(i == user.getOwnersBooksHistory().size()-1) {
//                    String sectionTitle = Utils.parseDateText(user.getOwnersBooksHistory().get(i-1).getTimeStart());
//                    sectionData.add(user.getOwnersBooksHistory().get(i));
//                    ScheduleSection data = new ScheduleSection(sectionTitle, sectionData);
//                    result.add(data);
//
//                    Log.d("Section title: " + sectionTitle);
//
//                    for (int j = 0; j < sectionData.size(); j++) {
//                        Log.d("Section data: " + Utils.parseDateWithoutYear(sectionData.get(j).getTimeStart()));
//                    }
//                    Log.d("Section date size -----> " + sectionData.size());
//                    sectionData.clear();
//                }
//
//            } else {
//                String sectionTitle = Utils.parseDateText(user.getOwnersBooksHistory().get(i-1).getTimeStart());
//                sectionData.add(user.getOwnersBooksHistory().get(i-1));
//                ScheduleSection data = new ScheduleSection(sectionTitle, sectionData);
//                result.add(data);
//
//                Log.d("Section title: " + sectionTitle);
//
//                for (int j = 0; j < sectionData.size(); j++) {
//                    Log.d("Section data: " + Utils.parseDateWithoutYear(sectionData.get(j).getTimeStart()));
//                }
//                Log.d("Section date size -----> " + sectionData.size());
//                sectionData.clear();
//            }
//        }

        lView = rootView.findViewById(R.id.lv_accepted_booking_admin);
        lView.setLayoutManager(new LinearLayoutManager(requireContext()));

        lAdapter = new BookingAdapterAdmin(result);

        lView.setAdapter(lAdapter);

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
