package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.DiscussionActivity;
import www.kaznu.kz.projects.m2.activities.DiscussionAdminActivity;
import www.kaznu.kz.projects.m2.adapters.MessagesAdapter;
import www.kaznu.kz.projects.m2.adapters.MessagesAdminAdapter;

public class MessagesAdminFragment extends Fragment {

    int[] images = {
            R.drawable.message_icon0,
            R.drawable.message_icon1,
            R.drawable.message_icon2,
            R.drawable.message_icon3,
            R.drawable.message_icon4,
            R.drawable.message_icon0,
            R.drawable.message_icon1,
            R.drawable.message_icon2,
            R.drawable.message_icon3,
            R.drawable.message_icon4};

    int[] messageCounts = {
            12,
            0,
            0,
            12,
            0,
            0,
            12,
            12,
            12,
            12};

    String[] userNames = {"Александра", "Андрей Власов", "Екатерина", "Лейсан", "Николай", "Александра", "Андрей Власов", "Андрей Власов", "Андрей Власов", "Андрей Власов"};

    String[] messages = {"Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…",
            "Здравствуйте! Если снимаете больше, чем на неделю, сделаю в…"};

    ListView lView;

    ListAdapter lAdapter;

    public MessagesAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_messages_admin, container, false);

        lView = rootView.findViewById(R.id.lv_messages);

        lAdapter = new MessagesAdminAdapter(getContext(), userNames, messages, images, messageCounts);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DiscussionAdminActivity.class);
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
