package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;
import www.kaznu.kz.projects.m2.viewmodels.MessageListFragmentViewModel;
import www.kaznu.kz.projects.m2.adapters.MessageListAdapter;
import www.kaznu.kz.projects.m2.views.activities.DiscussionActivity;

import static www.kaznu.kz.projects.m2.interfaces.Constants.TAG;

public class MessageListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MessageListAdapter mAdapter;
    private MessageListFragmentViewModel mMessageListFragmentViewModel;

    public MessageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_message_list, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_message_list);
        mProgressBar = rootView.findViewById(R.id.pb_message_list);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMessageListFragmentViewModel = new ViewModelProvider(requireActivity()).get(MessageListFragmentViewModel.class);
        mMessageListFragmentViewModel.init();

        mMessageListFragmentViewModel.getMessageList().observe(getViewLifecycleOwner(), new Observer<ArrayList<MessageList>>() {
            @Override
            public void onChanged(ArrayList<MessageList> messageLists) {
                initRecyclerView(messageLists);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView(ArrayList<MessageList> mData) {
        showProgressBar();

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

        mAdapter = new MessageListAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.setAdapter(mAdapter);

        hideProgressBar();

        mAdapter.setOnItemClickListener(new MessageListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getContext(), DiscussionActivity.class);
                intent.putExtra("contact", mData.get(position).getCompany());
                intent.putExtra("ref_realty", mData.get(position).getRefRealty());
                intent.putExtra("owner", false);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }

    private void showProgressBar() {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setIndeterminate(false);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
