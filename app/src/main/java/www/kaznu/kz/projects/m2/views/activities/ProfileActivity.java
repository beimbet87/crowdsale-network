package www.kaznu.kz.projects.m2.views.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.fragments.ProfileFragment;
import www.kaznu.kz.projects.m2.utils.Logger;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.DataFromProfileFragment {

    TextView tvUserName, tvUserSurname;
    TextView tvUserSex, tvUserBirthday;
    TextView tvUserPhone, tvUserEmail;

    public TextView title;
    Button backButton;
    int fragmentNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        backButton = toolbar.findViewById(R.id.toolbar_back);

        backButton.setOnClickListener(v -> finish());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        ft.add(R.id.profile, profileFragment);
        ft.commit();

        Logger.d(String.valueOf(fragmentNumber));

    }

    @Override
    public void FromProfileFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }
}