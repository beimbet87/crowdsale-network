package www.kaznu.kz.projects.m2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.BookingAdapter;
import www.kaznu.kz.projects.m2.adapters.BookingAdapterAdmin;

public class ScheduleAdminFragment extends Fragment {

    int[] images = {
            R.drawable.message_icon0,
            R.drawable.message_icon1,
    };

    String[] address = {
            "Алматы, Некрасова 32А",
            "Астана,  ул. Лиховец 70/2"
    };

    String[] date = {
            "Выезд гостей",
            "Заезд гостей"
    };

    int[] acceptedImages = {
            R.drawable.avatar,
            R.drawable.avatar
    };

    String[] acceptedAddress = {
            "Алматы, Некрасова 32А"
    };

    String[] acceptedDate = {
            "14 дек - 31 дек"
    };

    ListView lView, acceptedLView;

    ListAdapter lAdapter, acceptedLAdapter;

    public ScheduleAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_schedule_admin, container, false);

        lView = rootView.findViewById(R.id.lv_accepted_booking_admin);

        lAdapter = new BookingAdapterAdmin(getContext(), address, date, images, acceptedImages);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(), address[i]+" "+ date[i], Toast.LENGTH_SHORT).show();

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
