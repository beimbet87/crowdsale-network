package www.kaznu.kz.projects.m2.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.fragments.CommentsFragment;
import www.kaznu.kz.projects.m2.fragments.ProfileFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class CommentsActivity extends AppCompatActivity implements CommentsFragment.DataFromCommentsFragment {

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
        setContentView(R.layout.activity_comments);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        backButton = toolbar.findViewById(R.id.toolbar_back);

        backButton.setOnClickListener(v -> finish());

        Log = new Logger(this, Constants.TAG);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CommentsFragment profileFragment = new CommentsFragment();
        ft.add(R.id.comments, profileFragment);
        ft.commit();

        Log.d(String.valueOf(fragmentNumber));

    }

    @Override
    public void FromCommentsFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }
}