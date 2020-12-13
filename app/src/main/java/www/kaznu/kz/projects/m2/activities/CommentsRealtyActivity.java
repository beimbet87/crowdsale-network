package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.rate.UserRate;
import www.kaznu.kz.projects.m2.fragments.CommentsFragment;
import www.kaznu.kz.projects.m2.fragments.CommentsRealtyFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.RateModel;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;

public class CommentsRealtyActivity extends AppCompatActivity implements CommentsFragment.DataFromCommentsFragment,
        CommentsRealtyFragment.DataFromCommentsRealtyFragment {

    TextView tvUserName, tvUserSurname;
    TextView tvUserSex, tvUserBirthday;
    TextView tvUserPhone, tvUserEmail;

    public TextView title;
    Button backButton;
    int fragmentNumber = 0;
    Logger Log;

    int realtyId;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();

        Log = new Logger(this, Constants.TAG);

        realtyId = intent.getIntExtra("ref_realty", -1);

        title = toolbar.findViewById(R.id.toolbar_title);

        if(realtyId == -1) {

            Log.d("User rate section!");
            Log.d("User ID: " + new CurrentUser(getApplicationContext()).getId());

            UserRate userRate = new UserRate(this, new CurrentUser(this).getId(),
                    intent.getIntExtra("as_owner", 0),
                    new Tokens(this).getAccessToken());

            userRate.setOnLoadListener(new UserRate.CustomOnLoadListener() {
                @Override
                public void onComplete(ArrayList<RateModel> rates, int count, double average) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    CommentsFragment profileFragment = new CommentsFragment();
                    ft.add(R.id.comments, profileFragment);
                    ft.commit();
                }
            });
        } else {
            Log.d("Realty rate section!");

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            CommentsRealtyFragment realtyFragment = new CommentsRealtyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("realty_id", realtyId);
            realtyFragment.setArguments(bundle);
            ft.add(R.id.comments, realtyFragment);
            ft.commit();
        }

        backButton = toolbar.findViewById(R.id.toolbar_back);

        backButton.setOnClickListener(v -> finish());

        Log.d(String.valueOf(fragmentNumber));

    }

    @Override
    public void FromCommentsRealtyFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromCommentsFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }
}