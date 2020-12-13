package www.kaznu.kz.projects.m2.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.fragments.PaymentCardFragment;
import www.kaznu.kz.projects.m2.fragments.PaymentMethodFragment;
import www.kaznu.kz.projects.m2.fragments.UploadAvatarFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.utils.Logger;

public class PaymentMethodsActivity extends AppCompatActivity {

    Button btnBack;
    public TextView title;
    int fragments = 1;

    Logger Log;

    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        user = new CurrentUser(this);
        Log = new Logger(this, Constants.TAG);

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