package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.MessageListAdapter;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;
import www.kaznu.kz.projects.m2.viewmodels.MessageListFragmentAdminViewModel;
import www.kaznu.kz.projects.m2.views.activities.DiscussionAdminActivity;

public class MessageListFragmentAdmin extends Fragment {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MessageListAdapter mAdapter;
    private MessageListFragmentAdminViewModel mMessageListFragmentAdminViewModel;
    private CurrentUser user;

    public MessageListFragmentAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root;

        user = new CurrentUser(requireContext());

        if (user.getOwnerMessageList().size() > 0) {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_message_list, container, false);

            mRecyclerView = root.findViewById(R.id.rv_message_list);
            mProgressBar = root.findViewById(R.id.pb_message_list);
        } else {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_message_empty, container, false);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (user.getClientMessageList().size() > 0) {
            mMessageListFragmentAdminViewModel = new ViewModelProvider(requireActivity()).get(MessageListFragmentAdminViewModel.class);
            mMessageListFragmentAdminViewModel.init();

            mMessageListFragmentAdminViewModel.getMessageList().observe(getViewLifecycleOwner(), new Observer<ArrayList<MessageList>>() {
                @Override
                public void onChanged(ArrayList<MessageList> messageLists) {
                    initRecyclerView(messageLists);
                    if(mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void initRecyclerView(ArrayList<MessageList> mData) {
        showProgressBar();

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(itemAnimator);

            mAdapter = new MessageListAdapter(mData);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            mRecyclerView.setAdapter(mAdapter);


            hideProgressBar();

            mAdapter.setOnItemClickListener(new MessageListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent intent = new Intent(getContext(), DiscussionAdminActivity.class);
                    intent.putExtra("contact", mData.get(position).getCompany());
                    intent.putExtra("ref_realty", mData.get(position).getRefRealty());
                    intent.putExtra("owner", true);

                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position, View v) {

                }
            });
        }
    }

    private void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setIndeterminate(true);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setIndeterminate(false);
            mProgressBar.setVisibility(View.GONE);
        }
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
