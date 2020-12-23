package www.kaznu.kz.projects.m2.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.utils.TinyDB;
import www.kaznu.kz.projects.m2.views.activities.CommentsRealtyActivity;
import www.kaznu.kz.projects.m2.models.CurrentUser;

import static www.kaznu.kz.projects.m2.interfaces.Constants.BASE_URL;
import static www.kaznu.kz.projects.m2.interfaces.Constants.SHARED_USER_IMAGE_LINK;
import static www.kaznu.kz.projects.m2.interfaces.Constants.SHARED_USER_NAME;
import static www.kaznu.kz.projects.m2.interfaces.Constants.SHARED_USER_SURNAME;

public class ProfileInfoFragment extends Fragment implements View.OnClickListener {

    TextView tvUserName;
    ImageView ivAvatar;
    LinearLayout llComments;
    TextView tvStars, tvUserComments;

    CurrentUser currentUser;

    private TinyDB data;

    @Override
    public void onClick(View v) {

    };

    public interface DataFromProfileFragment {
        void FromProfileFragment(String data, int number);
    }

    public ProfileInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_profile_info, container, false);

        currentUser = new CurrentUser(requireContext());

        tvUserName = fv.findViewById(R.id.profile_name);
        ivAvatar = fv.findViewById(R.id.iv_profile_image);
        llComments = fv.findViewById(R.id.comments);
        tvStars = fv.findViewById(R.id.tv_stars);
        tvUserComments = fv.findViewById(R.id.tv_user_comments);

        data = new TinyDB(requireContext());

        String url = BASE_URL + data.getString(SHARED_USER_IMAGE_LINK);

        Picasso.get().load(url).into(ivAvatar);

        String fullName = data.getString(SHARED_USER_SURNAME) + " " +
                data.getString(SHARED_USER_NAME);

        tvStars.setText(String.valueOf(new CurrentUser(requireContext()).getRateAverage()));
        tvUserComments.setText(String.valueOf(new CurrentUser(requireContext()).getRateCount()) +
                " человека оставили отзыв");
        tvUserName.setText(fullName);

        llComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), CommentsRealtyActivity.class);
                intent.putExtra("user", true);
                intent.putExtra("user_id", currentUser.getId());
                intent.putExtra("as_owner", 0);
                startActivity(intent);
            }
        });

        dataPasser.FromProfileFragment("Профиль", 3);

        return fv;
    }

    DataFromProfileFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromProfileFragment) context;
    }

}