package www.kaznu.kz.projects.m2.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.fragments.RegisterFragment01;
import www.kaznu.kz.projects.m2.views.fragments.RegisterFragment02;
import www.kaznu.kz.projects.m2.views.fragments.RegisterFragment03;
import www.kaznu.kz.projects.m2.views.fragments.RegisterFragment04;
import www.kaznu.kz.projects.m2.views.fragments.RegisterFragment05;
import www.kaznu.kz.projects.m2.views.fragments.RegisterFragment06;
import www.kaznu.kz.projects.m2.views.fragments.RegisterFragment07;
import www.kaznu.kz.projects.m2.utils.Logger;

public class RegisterActivity extends AppCompatActivity implements
        RegisterFragment01.DataFromFragment01,
        RegisterFragment02.DataFromFragment02,
        RegisterFragment03.DataFromFragment03,
        RegisterFragment04.DataFromFragment04,
        RegisterFragment05.DataFromFragment05,
        RegisterFragment06.DataFromFragment06,
        RegisterFragment07.DataFromFragment07,
        View.OnClickListener {

    public TextView title;
    Button backButton;
    int fragmentNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        backButton = toolbar.findViewById(R.id.toolbar_back);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        RegisterFragment01 registerFragment01 = new RegisterFragment01();
        ft.add(R.id.register_fragment, registerFragment01);
        ft.commit();

        Logger.d(String.valueOf(fragmentNumber));
    }

    @Override
    public void FromFragment03(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromFragment04(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromFragment05(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void DataFromFragment06(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void DataFromFragment07(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromFragment01(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void FromFragment02(String data, int number) {
        title.setText(data);
        fragmentNumber = number;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.toolbar_back) {
            switch (fragmentNumber) {
                case 0:
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    RegisterFragment01 registerFragment01 = new RegisterFragment01();
                    ft.replace(R.id.register_fragment, registerFragment01);
                    ft.commit();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
        }
    }
}