package www.kaznu.kz.projects.m2.views.fragments;

import static www.kaznu.kz.projects.m2.interfaces.Constants.SHARED_OWNER_MESSAGE_LIST;
import static www.kaznu.kz.projects.m2.interfaces.Constants.SHARED_USER_ADMIN_MESSAGE_LIST;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
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
import www.kaznu.kz.projects.m2.api.pusher.MessageListData;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.TinyDB;
import www.kaznu.kz.projects.m2.viewmodels.MessageListFragmentAdminViewModel;
import www.kaznu.kz.projects.m2.views.activities.DiscussionAdminActivity;

public class MessageListFragmentAdmin extends Fragment {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MessageListFragmentAdminViewModel mMessageListFragmentAdminViewModel;
    private CurrentUser user;

    public MessageListFragmentAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root;

        user = new CurrentUser(requireContext());

        if (user.getOwnerMessageList().size() > 0) {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_message_list, container, false);

            mRecyclerView = root.findViewById(R.id.rv_message_list);
            mProgressBar = root.findViewById(R.id.pb_message_list);

            MessageListData adminMessageList = new MessageListData(requireContext(), 0, new Tokens(requireContext()).getAccessToken());
            adminMessageList.setOnLoadListener((code, message, chats) -> {
                if (chats.size() > 0) {
                    TinyDB data = new TinyDB(requireContext());
                    data.putListMessageModel(SHARED_USER_ADMIN_MESSAGE_LIST, chats);

                    user = new CurrentUser(requireContext());

                    Log.d(Constants.TAG, user.getClientMessageList().get(0).getCount() + " admin messages");

                    if (user.getClientMessageList().size() > 0) {
                        mMessageListFragmentAdminViewModel = new ViewModelProvider(requireActivity()).get(MessageListFragmentAdminViewModel.class);
                        mMessageListFragmentAdminViewModel.init(user);

                        mMessageListFragmentAdminViewModel.getMessageList().observe(getViewLifecycleOwner(), messageLists -> {
                            Log.d(Constants.TAG, messageLists.get(0).getMessageCount() + " admin messages");
                            initRecyclerView(messageLists);
                        });
                    }
                }
            });

        } else {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_message_empty, container, false);
        }

        Log.d(Constants.TAG, "onCreateView");

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        MessageListData adminMessageList = new MessageListData(requireContext(), 1, new Tokens(requireContext()).getAccessToken());
        adminMessageList.setOnLoadListener((code, message, chats) -> {
            if (chats.size() > 0) {
                Log.d(Constants.TAG, chats.get(0).getCount() + " admin messages");
                TinyDB data = new TinyDB(requireContext());
                data.putListMessageModel(SHARED_OWNER_MESSAGE_LIST, chats);

                user = new CurrentUser(requireContext());

                Log.d(Constants.TAG, user.getOwnerMessageList().get(0).getCount() + " messages");

                if (user.getOwnerMessageList().size() > 0) {
                    mMessageListFragmentAdminViewModel = new ViewModelProvider(requireActivity()).get(MessageListFragmentAdminViewModel.class);
                    mMessageListFragmentAdminViewModel.init(user);

                    mMessageListFragmentAdminViewModel.getMessageList().observe(getViewLifecycleOwner(), messageLists -> {
                        Log.d(Constants.TAG, messageLists.get(0).getMessageCount() + " messages");
                        initRecyclerView(messageLists);
                    });
                }
            }
            else {
                Log.d(Constants.TAG, "Loading ...");
            }
        });

        Log.d(Constants.TAG, "onResume");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(Constants.TAG, "onActivityCreated");
    }

    private void initRecyclerView(ArrayList<MessageList> mData) {
        showProgressBar();

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

        MessageListAdapter mAdapter = null;
        mAdapter = new MessageListAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.setAdapter(mAdapter);

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            mAdapter.notifyItemChanged(i);
            mRecyclerView.refreshDrawableState();
        }
        mRecyclerView.invalidate();

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
        Log.d(Constants.TAG, "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Constants.TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "onDestroy");
    }
}
