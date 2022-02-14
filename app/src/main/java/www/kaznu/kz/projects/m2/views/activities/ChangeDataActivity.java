package www.kaznu.kz.projects.m2.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.fragments.ChangeEmailFragment;
import www.kaznu.kz.projects.m2.views.fragments.ChangePasswordFragment;
import www.kaznu.kz.projects.m2.views.fragments.ChangePhoneFragment;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.views.fragments.ConfirmationEmailFragment;
import www.kaznu.kz.projects.m2.views.fragments.ConfirmationPhoneFragment;

public class ChangeDataActivity extends AppCompatActivity implements ChangePhoneFragment.DataFromChangePhoneFragment,
        ChangeEmailFragment.DataFromChangeEmailFragment, ChangePasswordFragment.DataFromChangePasswordFragment,
        ConfirmationEmailFragment.DataFromConfirmationFragment, ConfirmationPhoneFragment.DataFromConfirmationPhoneFragment {

    public TextView title;
    Button backButton;
    int fragmentNumber = 0;
    int fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        hideSystemBars();

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        backButton = toolbar.findViewById(R.id.toolbar_back);

        backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();

        fragment = intent.getIntExtra("fragment", 0);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(fragment == 0) {
            ChangePhoneFragment profileFragment = new ChangePhoneFragment();
            ft.add(R.id.change_data, profileFragment);
            ft.commit();
        }
        else if(fragment == 1) {
            ChangeEmailFragment profileFragment = new ChangeEmailFragment();
            ft.add(R.id.change_data, profileFragment);
            ft.commit();
        }
        else if(fragment == 2){
            ChangePasswordFragment profileFragment = new ChangePasswordFragment();
            ft.add(R.id.change_data, profileFragment);
            ft.commit();
        }
        else if(fragment == 3){
            ConfirmationEmailFragment confirmationFragment = new ConfirmationEmailFragment();
            ft.add(R.id.change_data, confirmationFragment);
            ft.commit();
        }

        Logger.d(String.valueOf(fragmentNumber));

    }

    @Override
    public void FromChangeEmailFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromChangePasswordFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromChangePhoneFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromConfirmationFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromConfirmationPhoneFragment(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
    }
}