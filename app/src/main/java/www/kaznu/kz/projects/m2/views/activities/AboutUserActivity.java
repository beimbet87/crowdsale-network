package www.kaznu.kz.projects.m2.views.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.fragments.UploadAvatarFragment;
import www.kaznu.kz.projects.m2.models.CurrentUser;

public class AboutUserActivity extends AppCompatActivity {

    Button btnBack;
    public TextView title;

    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);

        user = new CurrentUser(this);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        btnBack = toolbar.findViewById(R.id.toolbar_back);

        btnBack.setOnClickListener(v -> finish());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        UploadAvatarFragment uploadAvatarFragment = new UploadAvatarFragment();
        ft.add(R.id.user_data, uploadAvatarFragment);
        ft.commit();

        title.setText("Расскажите о себе");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}