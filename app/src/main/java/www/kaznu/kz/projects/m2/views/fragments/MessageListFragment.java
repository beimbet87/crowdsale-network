package www.kaznu.kz.projects.m2.views.fragments;

import static www.kaznu.kz.projects.m2.interfaces.Constants.SHARED_USER_MESSAGE_LIST;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.pusher.MessageListData;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.TinyDB;
import www.kaznu.kz.projects.m2.viewmodels.MessageListFragmentViewModel;
import www.kaznu.kz.projects.m2.adapters.MessageListAdapter;
import www.kaznu.kz.projects.m2.views.activities.DiscussionActivity;

public class MessageListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MessageListAdapter mAdapter;
    private MessageListFragmentViewModel mMessageListFragmentViewModel;
    private CurrentUser user;

    public MessageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root;

        user = new CurrentUser(requireContext());

        if (user.getClientMessageList().size() > 0) {
            root = (ViewGroup) inflater.inflate(R.layout.fragment_message_list, container, false);
            mRecyclerView = root.findViewById(R.id.rv_message_list);
            mProgressBar = root.findViewById(R.id.pb_message_list);

            MessageListData clientMessageList = new MessageListData(requireContext(), 0, new Tokens(requireContext()).getAccessToken());
            clientMessageList.setOnLoadListener(new MessageListData.CustomOnLoadListener() {
                @Override
                public void onComplete(int code, String message, ArrayList<Chat> chats) {
                    Log.d(Constants.TAG, chats.get(0).getCount() + " messages");
                    TinyDB data = new TinyDB(requireContext());
                    data.putListMessageModel(SHARED_USER_MESSAGE_LIST, chats);

                    user = new CurrentUser(requireContext());

                    Log.d(Constants.TAG, user.getClientMessageList().get(0).getCount() + " messages");

                    if (user.getClientMessageList().size() > 0) {
                        mMessageListFragmentViewModel = new ViewModelProvider(requireActivity()).get(MessageListFragmentViewModel.class);
                        mMessageListFragmentViewModel.init(user);

                        mMessageListFragmentViewModel.getMessageList().observe(getViewLifecycleOwner(), new Observer<ArrayList<MessageList>>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onChanged(ArrayList<MessageList> messageLists) {
                                Log.d(Constants.TAG, messageLists.get(0).getMessageCount() + " messages");
                                initRecyclerView(messageLists);
                            }

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

        MessageListData clientMessageList = new MessageListData(requireContext(), 0, new Tokens(requireContext()).getAccessToken());
        clientMessageList.setOnLoadListener(new MessageListData.CustomOnLoadListener() {
            @Override
            public void onComplete(int code, String message, ArrayList<Chat> chats) {
                Log.d(Constants.TAG, chats.get(0).getCount() + " messages");
                TinyDB data = new TinyDB(requireContext());
                data.putListMessageModel(SHARED_USER_MESSAGE_LIST, chats);

                user = new CurrentUser(requireContext());

                Log.d(Constants.TAG, user.getClientMessageList().get(0).getCount() + " messages");

                if (user.getClientMessageList().size() > 0) {
                    mMessageListFragmentViewModel = new ViewModelProvider(requireActivity()).get(MessageListFragmentViewModel.class);
                    mMessageListFragmentViewModel.init(user);

                    mMessageListFragmentViewModel.getMessageList().observe(getViewLifecycleOwner(), new Observer<ArrayList<MessageList>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(ArrayList<MessageList> messageLists) {
                            Log.d(Constants.TAG, messageLists.get(0).getMessageCount() + " messages");
                            initRecyclerView(messageLists);
                        }

                    });
                }
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

        mAdapter = null;
        mAdapter = new MessageListAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.setAdapter(mAdapter);

        for(int i = 0; i < mAdapter.getItemCount(); i++) {
            mAdapter.notifyItemChanged(i);
            mRecyclerView.refreshDrawableState();
        }
        mRecyclerView.invalidate();



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
