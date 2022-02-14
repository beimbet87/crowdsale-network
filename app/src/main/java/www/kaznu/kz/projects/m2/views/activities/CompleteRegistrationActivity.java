package www.kaznu.kz.projects.m2.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.CurrentUser;

public class CompleteRegistrationActivity extends AppCompatActivity {

    Button btnBack;

    LinearLayout btnPaymentMethods, btnAboutUser, btnMainInfo, btnRegistration;
    ImageView ivPMChecked, ivAUChecked, ivMIChecked, ivRChecked;

    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);

        hideSystemBars();

        btnBack = findViewById(R.id.toolbar_back);
        btnPaymentMethods = findViewById(R.id.payment_methods);
        btnAboutUser = findViewById(R.id.about_user);
        btnMainInfo = findViewById(R.id.main_info);
        btnRegistration = findViewById(R.id.registration);

        ivPMChecked = findViewById(R.id.payment_methods_checked);
        ivAUChecked = findViewById(R.id.about_user_checked);
        ivMIChecked = findViewById(R.id.main_info_checked);
        ivRChecked = findViewById(R.id.registration_checked);

        user = new CurrentUser(this);

        if(user.getImageLink() == null) {
            btnAboutUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CompleteRegistrationActivity.this, AboutUserActivity.class);
                    startActivity(intent);
                }
            });
        }

        btnPaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteRegistrationActivity.this, PaymentMethodsActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}