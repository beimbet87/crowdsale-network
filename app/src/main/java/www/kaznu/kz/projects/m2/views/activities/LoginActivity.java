package www.kaznu.kz.projects.m2.views.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import www.kaznu.kz.projects.m2.api.user.Token;
import www.kaznu.kz.projects.m2.interfaces.Constants;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class LoginActivity extends IntroActivity implements Constants {
    TextView tvRegister;
    Button btnLogin;
    EditText etUserName, etPassword;
    TinyDB data;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        data = new TinyDB(this);

        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        Permissions.check(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
            }
        });

        tvRegister = findViewById(R.id.tv_registration);
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.loader);

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {

            Token token = new Token(getApplicationContext(), etUserName.getText().toString(), etPassword.getText().toString());

            progressBar.setVisibility(View.VISIBLE);

            token.setOnLoadListener(token1 -> {

                Intent intent = new Intent(LoginActivity.this, ProfileTypeActivity.class);
                startActivity(intent);
            });
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        progressBar.setVisibility(View.GONE);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}