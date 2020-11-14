package www.kaznu.kz.projects.m2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.ProfileActivity;

public class AccountAdminFragment extends Fragment {

    LinearLayout lProfile;
    public AccountAdminFragment() {
        // Required empty public constructor
    }

    public interface DataFromAccountAdminFragment {
        public void DataFromAccountAdminFragment(boolean isMode);
    }

    SwitchCompat userMode;
    TextView tvUserMode;
    TextView tvUserModeHint;
    TextView tvUserName;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_account_admin, container, false);

        lProfile = rootView.findViewById(R.id.lv_profile);
        userMode = rootView.findViewById(R.id.sw_user_mode);
        tvUserMode = rootView.findViewById(R.id.tv_user_mode);
        tvUserModeHint = rootView.findViewById(R.id.tv_user_mode_hint);
        tvUserName = rootView.findViewById(R.id.tv_profile_username);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("M2_USER_INFO", 0);
        String userName = sharedPreferences.getString("name", "");

        tvUserName.setText(userName);

        tvUserMode.setText(R.string.user_mode_owner);
        tvUserModeHint.setText(R.string.user_mode_hint_guest);
        userMode.setChecked(true);

        userMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    tvUserMode.setText(R.string.user_mode_owner);
                    tvUserModeHint.setText(R.string.user_mode_hint_guest);
                }
                else {
                    tvUserMode.setText(R.string.user_mode_guest);
                    tvUserModeHint.setText(R.string.user_mode_hint_owner);
                }
                dataPasser.DataFromAccountAdminFragment(isChecked);
            }
        });

        lProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
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

    DataFromAccountAdminFragment dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataFromAccountAdminFragment) context;
    }
}