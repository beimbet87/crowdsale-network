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

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.RatingAdapter;
import www.kaznu.kz.projects.m2.api.rate.RealtyRate;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.RateModel;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;

public class CommentsRealtyFragment extends Fragment implements View.OnClickListener {

    RecyclerView lView;

    RatingAdapter adapter;

    ProgressBar progressBar;
    CurrentUser user;

    Logger Log;

    @Override
    public void onClick(View v) {

    }

    public interface DataFromCommentsRealtyFragment {
        void FromCommentsRealtyFragment(String data, int number);
    }

    public CommentsRealtyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_realty_comments, container, false);

        user = new CurrentUser(requireContext());

        dataPasser.FromCommentsRealtyFragment("Отзывы", 3);

        lView = fv.findViewById(R.id.rv_comments);

        progressBar = fv.findViewById(R.id.comment_progress);

        Log = new Logger(requireContext(), Constants.TAG);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        lView.setItemAnimator(itemAnimator);

        RealtyRate realtyRate = new RealtyRate(requireContext(), getArguments().getInt("realty_id"), new Tokens(requireContext()).getAccessToken());

        realtyRate.setOnLoadListener(new RealtyRate.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<RateModel> rates, int count, double average) {
                adapter = new RatingAdapter(requireContext(), rates);
                lView.setLayoutManager(new LinearLayoutManager(requireContext()));
                lView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);

                adapter.setOnCardClickListener((view, position) -> {

                });
            }
        });

        return fv;
    }

    DataFromCommentsRealtyFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromCommentsRealtyFragment) context;
    }

}