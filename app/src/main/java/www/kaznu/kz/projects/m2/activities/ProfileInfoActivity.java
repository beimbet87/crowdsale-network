package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.fragments.ProfileFragment;
import www.kaznu.kz.projects.m2.fragments.ProfileInfoFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class ProfileInfoActivity extends AppCompatActivity implements ProfileInfoFragment.DataFromProfileFragment {

    public TextView title;
    Button backButton, editButton;
    int fragmentNumber = 0;
    Logger Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        backButton = toolbar.findViewById(R.id.toolbar_back);
        editButton = toolbar.findViewById(R.id.toolbar_edit);

        backButton.setOnClickListener(v -> finish());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        Log = new Logger(this, Constants.TAG);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProfileInfoFragment profileFragment = new ProfileInfoFragment();
        ft.add(R.id.profile_info, profileFragment);
        ft.commit();

        Log.d(String.valueOf(fragmentNumber));

    }

    @Override
    public void FromProfileFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }
}