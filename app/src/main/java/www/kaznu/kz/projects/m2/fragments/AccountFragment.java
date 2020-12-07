package www.kaznu.kz.projects.m2.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.ProfileActivity;
import www.kaznu.kz.projects.m2.activities.ProfileInfoActivity;
import www.kaznu.kz.projects.m2.activities.UploadAvatarActivity;
import www.kaznu.kz.projects.m2.interfaces.Constants;

public class AccountFragment extends Fragment implements Constants {

    LinearLayout lProfile;
    public AccountFragment() {
        // Required empty public constructor
    }

    public interface DataFromAccountFragment {
        public void DataFromAccountFragment(boolean isMode);
    }

    SwitchCompat userMode;
    TextView tvUserMode;
    TextView tvUserModeHint;
    TextView tvUserName;
    Button btnViewProfile;
    ImageView ivAvatar;
    LinearLayout btnExit;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_account, container, false);

        lProfile = rootView.findViewById(R.id.lv_profile);
        userMode = rootView.findViewById(R.id.sw_user_mode);
        tvUserMode = rootView.findViewById(R.id.tv_user_mode);
        tvUserModeHint = rootView.findViewById(R.id.tv_user_mode_hint);
        tvUserName = rootView.findViewById(R.id.tv_profile_username);
        btnViewProfile = rootView.findViewById(R.id.btn_view_profile);
        ivAvatar = rootView.findViewById(R.id.iv_profile_image);
        btnExit = rootView.findViewById(R.id.btn_exit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.Dialog_Style_Alert))
                        .setMessage("Вы действительно хотите выйти?")
                        .setCancelable(false)

                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                requireActivity().finish();
                            }
                        })
                        .setNegativeButton("Нет", null)
                        .show();
            }
        });

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("M2_USER_INFO", 0);
        String userName = sharedPreferences.getString("name", "");

        String url = BASE_URL + sharedPreferences.getString("image", "");

        Glide.with(requireContext()).load(url).into(ivAvatar);

        tvUserName.setText(userName);

        tvUserMode.setText(R.string.user_mode_guest);
        tvUserModeHint.setText(R.string.user_mode_hint_owner);
        userMode.setChecked(false);

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
                dataPasser.DataFromAccountFragment(isChecked);
            }
        });

        lProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileInfoActivity.class);
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

    DataFromAccountFragment dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataFromAccountFragment) context;
    }
}
