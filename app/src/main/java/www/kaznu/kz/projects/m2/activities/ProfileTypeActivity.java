package www.kaznu.kz.projects.m2.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.fragments.ProfileTypeFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class ProfileTypeActivity extends AppCompatActivity implements ProfileTypeFragment.DataFromProfileTypeFragment {

    public TextView title;
    Button backButton;
    int fragmentNumber = 0;
    Logger Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_type);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        backButton = toolbar.findViewById(R.id.toolbar_back);

        backButton.setOnClickListener(v -> finish());

        Log = new Logger(this, Constants.TAG);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProfileTypeFragment profileTypeFragment = new ProfileTypeFragment();
        ft.add(R.id.profile_type, profileTypeFragment);
        ft.commit();

        Log.d(String.valueOf(fragmentNumber));

    }

    @Override
    public void FromProfileTypeFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }
}