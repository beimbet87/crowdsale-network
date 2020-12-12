package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.realty.RealtyUpdate;
import www.kaznu.kz.projects.m2.fragments.RealtyAddFragment;
import www.kaznu.kz.projects.m2.fragments.UploadAvatarFragment;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.Realty;

public class RealtyAddActivity extends AppCompatActivity {

    Button btnBack;
    public TextView title;

    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_add);
        user = new CurrentUser(this);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        btnBack = toolbar.findViewById(R.id.toolbar_back);

        btnBack.setOnClickListener(v -> finish());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        RealtyAddFragment realtyAddFragment = new RealtyAddFragment();
        ft.add(R.id.add_realty, realtyAddFragment);
        ft.commit();

        title.setText("Создать объявление");

    }
}