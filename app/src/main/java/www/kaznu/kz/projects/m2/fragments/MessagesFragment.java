package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.DiscussionActivity;
import www.kaznu.kz.projects.m2.adapters.MessagesAdapter;
import www.kaznu.kz.projects.m2.api.pusher.MyChats;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.utils.Logger;

public class MessagesFragment extends Fragment {

    RecyclerView lView;

    MessagesAdapter adapter;

    ProgressBar progressBar;
    MyChats myChats;

    Logger Log;

    boolean isLoading = false;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_messages, container, false);

        lView = rootView.findViewById(R.id.lv_messages);

        progressBar = rootView.findViewById(R.id.offers_progress);
        progressBar.setIndeterminate(true);
        Log = new Logger(requireContext(), Constants.TAG);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        lView.setItemAnimator(itemAnimator);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences token = requireActivity().getSharedPreferences("M2_TOKEN", 0);

        myChats = new MyChats(requireContext(), 0, token.getString("access_token", ""));

        myChats.setOnLoadListener(new MyChats.CustomOnLoadListener() {
            @Override
            public void onComplete(int code, String message, ArrayList<Chat> chats) {

                adapter = new MessagesAdapter(requireContext(), chats);
                lView.setLayoutManager(new LinearLayoutManager(requireContext()));
                lView.setAdapter(adapter);

                progressBar.setIndeterminate(false);

                adapter.setOnItemClickListener(new MessagesAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Intent intent = new Intent(getContext(), DiscussionActivity.class);
                        intent.putExtra("contact", chats.get(position).getCompany());
                        intent.putExtra("ref_realty", chats.get(position).getRefRealty());
                        intent.putExtra("owner", 0);

                        startActivity(intent);

                        Log.d(chats.get(position).getCompany() + "" + chats.get(position).getRefRealty());
                    }

                    @Override
                    public void onItemLongClick(int position, View v) {

                    }
                });
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
