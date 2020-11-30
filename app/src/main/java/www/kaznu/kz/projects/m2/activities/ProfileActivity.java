package www.kaznu.kz.projects.m2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.fragments.ProfileFragment;
import www.kaznu.kz.projects.m2.fragments.ProfileTypeFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.DataFromProfileFragment {

    TextView tvUserName, tvUserSurname;
    TextView tvUserSex, tvUserBirthday;
    TextView tvUserPhone, tvUserEmail;

    public TextView title;
    Button backButton;
    int fragmentNumber = 0;
    Logger Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        backButton = toolbar.findViewById(R.id.toolbar_back);

        backButton.setOnClickListener(v -> finish());

        Log = new Logger(this, Constants.TAG);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        ft.add(R.id.profile, profileFragment);
        ft.commit();

        Log.d(String.valueOf(fragmentNumber));

    }

    @Override
    public void FromProfileFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }
}