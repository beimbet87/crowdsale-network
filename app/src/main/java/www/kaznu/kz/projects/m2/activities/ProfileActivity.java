package www.kaznu.kz.projects.m2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;

public class ProfileActivity extends IntroActivity {

    TextView tvUserName, tvUserSurname;
    TextView tvUserSex, tvUserBirthday;
    TextView tvUserPhone, tvUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUserName = findViewById(R.id.profile_name);
        tvUserSurname = findViewById(R.id.profile_surname);
        tvUserSex = findViewById(R.id.profile_sex);
        tvUserBirthday = findViewById(R.id.profile_birthday);
        tvUserPhone = findViewById(R.id.profile_phone);
        tvUserEmail = findViewById(R.id.profile_email);

        SharedPreferences sharedPreferences = getSharedPreferences("M2_USER_INFO", 0);
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

    }
}