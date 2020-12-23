package www.kaznu.kz.projects.m2.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.RatingAdapter;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.utils.Logger;

public class CommentsFragment extends Fragment implements View.OnClickListener {

    RecyclerView lView;

    RatingAdapter adapter;

    ProgressBar progressBar;
    CurrentUser user;

    @Override
    public void onClick(View v) {

    }

    public interface DataFromCommentsFragment {
        void FromCommentsFragment(String data, int number);
    }

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_comments, container, false);

        user = new CurrentUser(requireContext());

        dataPasser.FromCommentsFragment("Отзывы", 3);

        lView = fv.findViewById(R.id.rv_comments);

        progressBar = fv.findViewById(R.id.comment_progress);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        lView.setItemAnimator(itemAnimator);

        if(user.isOwner()) {
            adapter = new RatingAdapter(requireContext(), user.getRatesOwner());
        } else {
            adapter = new RatingAdapter(requireContext(), user.getRates());
        }

        lView.setLayoutManager(new LinearLayoutManager(requireContext()));
        lView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);

        adapter.setOnCardClickListener((view, position) -> {

        });

        return fv;
    }

    DataFromCommentsFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromCommentsFragment) context;
    }

}