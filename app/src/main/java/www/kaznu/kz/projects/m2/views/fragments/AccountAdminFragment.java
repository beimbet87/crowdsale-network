package www.kaznu.kz.projects.m2.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.activities.CompleteRegistrationActivity;
import www.kaznu.kz.projects.m2.views.activities.ProfileActivity;
import www.kaznu.kz.projects.m2.views.activities.ProfileInfoActivity;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.customviews.TextProgressBar;

public class AccountAdminFragment extends Fragment implements Constants,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    LinearLayout lProfile;

    CurrentUser currentUser;

    LinearLayout completeRegistration;

    public AccountAdminFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_exit:
                Utils.exitApp(requireContext(), requireActivity());
                break;
            case R.id.lv_profile:
                intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view_profile:
                intent = new Intent(getActivity(), ProfileInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.complete_registration:
                intent = new Intent(getActivity(), CompleteRegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            tvUserMode.setText(R.string.user_mode_owner);
            tvUserModeHint.setText(R.string.user_mode_hint_guest);
            new CurrentUser(requireContext()).setOwner(true);
        }
        else {
            tvUserMode.setText(R.string.user_mode_guest);
            tvUserModeHint.setText(R.string.user_mode_hint_owner);
            new CurrentUser(requireContext()).setOwner(false);
        }
        dataPasser.FromAccountAdminFragment(isChecked);
    }

    public interface DataFromAccountAdminFragment {
        void FromAccountAdminFragment(boolean isMode);
    }

    SwitchCompat userMode;
    TextView tvUserMode;
    TextView tvUserModeHint;
    TextView tvUserName;
    ImageView ivAvatar;

    int myProgress = 75;
    TextProgressBar pb;
    LinearLayout btnExit;
    ScaleRatingBar ratingBar;
    Button btnViewProfile;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_account_admin, container, false);

        currentUser = new CurrentUser(requireContext());

        lProfile = rootView.findViewById(R.id.lv_profile);
        userMode = rootView.findViewById(R.id.sw_user_mode);
        tvUserMode = rootView.findViewById(R.id.tv_user_mode);
        tvUserModeHint = rootView.findViewById(R.id.tv_user_mode_hint);
        tvUserName = rootView.findViewById(R.id.tv_profile_username);
        btnViewProfile = rootView.findViewById(R.id.btn_view_profile);
        ivAvatar = rootView.findViewById(R.id.iv_profile_image);
        btnExit = rootView.findViewById(R.id.btn_exit);
        pb = rootView.findViewById(R.id.mf_progress_bar);
        ratingBar = rootView.findViewById(R.id.rb_profile_rating);

        completeRegistration = rootView.findViewById(R.id.complete_registration);

        pb.setProgress(myProgress);
        pb.setText(myProgress/25+"/4");

        btnExit.setOnClickListener(this);
        lProfile.setOnClickListener(this);
        btnViewProfile.setOnClickListener(this);
        completeRegistration.setOnClickListener(this);

        tvUserName.setText(currentUser.getName());

        tvUserMode.setText(R.string.user_mode_owner);
        tvUserModeHint.setText(R.string.user_mode_hint_guest);

        Picasso.get().load(Constants.BASE_URL.concat(currentUser.getImageLink())).into(ivAvatar);

        userMode.setChecked(true);
        userMode.setOnCheckedChangeListener(this);

        ratingBar.setRating((float)currentUser.getRateAverageOwner());

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

    DataFromAccountAdminFragment dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataFromAccountAdminFragment) context;
    }
}
