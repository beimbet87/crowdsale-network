package www.kaznu.kz.projects.m2.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.fragments.PaymentCardFragment;
import www.kaznu.kz.projects.m2.views.fragments.PaymentMethodFragment;
import www.kaznu.kz.projects.m2.models.CurrentUser;

public class PaymentMethodsActivity extends AppCompatActivity {

    Button btnBack;
    public TextView title;
    int fragments = 1;

    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        hideSystemBars();

        user = new CurrentUser(this);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        btnBack = toolbar.findViewById(R.id.toolbar_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportFragmentManager().findFragmentByTag("PaymentCardFragment") instanceof PaymentCardFragment)
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment();
                    ft.replace(R.id.payment_methods, paymentMethodFragment, "PaymentMethodFragment");
                    ft.commit();
                } else {
                    finish();
                }
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment();
        ft.add(R.id.payment_methods, paymentMethodFragment, "PaymentMethodFragment");
        ft.commit();

        title.setText("Способы оплаты");

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
        if(getSupportFragmentManager().findFragmentByTag("PaymentCardFragment") instanceof PaymentCardFragment)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment();
            ft.replace(R.id.payment_methods, paymentMethodFragment, "PaymentMethodFragment");
            ft.commit();
        } else {
            finish();
        }
    }
}