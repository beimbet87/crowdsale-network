package www.kaznu.kz.projects.m2.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;

import static www.kaznu.kz.projects.m2.interfaces.Constants.BASE_URL;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView tvUserName, tvUserSurname;
    TextView tvUserSex, tvUserBirthday;
    TextView tvUserPhone, tvUserEmail;
    ImageView ivAvatar;

    @Override
    public void onClick(View v) {

    }

    public interface DataFromProfileFragment {
        void FromProfileFragment(String data, int number);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUserName = fv.findViewById(R.id.profile_name);
        tvUserSurname = fv.findViewById(R.id.profile_surname);
        tvUserSex = fv.findViewById(R.id.profile_sex);
        tvUserBirthday = fv.findViewById(R.id.profile_birthday);
        tvUserPhone = fv.findViewById(R.id.profile_phone);
        tvUserEmail = fv.findViewById(R.id.profile_email);
        ivAvatar = fv.findViewById(R.id.iv_profile_image);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("M2_USER_INFO", 0);

        String url = BASE_URL + sharedPreferences.getString("image", "");

        Glide.with(requireContext()).load(url).into(ivAvatar);

        tvUserName.setText(sharedPreferences.getString("name", ""));
        tvUserSurname.setText(sharedPreferences.getString("surname", ""));
        if(sharedPreferences.getBoolean("ismen", true)) {
            tvUserSex.setText("Мужской");
        }
        else {
            tvUserSex.setText("Женский");
        }
        String birthday = sharedPreferences.getString("birth", "").replaceAll("T", " ");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date mDate = null;
        try {
            mDate = sdf.parse(birthday);
            tvUserBirthday.setText(formatter.format(mDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvUserPhone.setText(sharedPreferences.getString("phone", ""));
        tvUserEmail.setText(sharedPreferences.getString("email", ""));

        dataPasser.FromProfileFragment("Личная информация", 3);

        return fv;
    }

    DataFromProfileFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromProfileFragment) context;
    }

}