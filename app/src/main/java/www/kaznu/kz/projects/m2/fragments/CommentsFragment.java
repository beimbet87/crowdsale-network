package www.kaznu.kz.projects.m2.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import www.kaznu.kz.projects.m2.R;

import static www.kaznu.kz.projects.m2.interfaces.Constants.BASE_URL;

public class CommentsFragment extends Fragment implements View.OnClickListener {

    TextView tvUserName, tvUserSurname;
    TextView tvUserSex, tvUserBirthday;
    TextView tvUserPhone, tvUserEmail;
    ImageView ivAvatar;

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


        dataPasser.FromCommentsFragment("Отзывы", 3);

        return fv;
    }

    DataFromCommentsFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromCommentsFragment) context;
    }

}