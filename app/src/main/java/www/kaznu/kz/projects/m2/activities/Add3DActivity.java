package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import www.kaznu.kz.projects.m2.R;

public class Add3DActivity extends AppCompatActivity {

    Button btnAdd3D;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3d_add);

        btnAdd3D = findViewById(R.id.btn_3d_add);
        btnBack = findViewById(R.id.back_button);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(Add3DActivity.this, RealtyAddActivity.class);
                startActivity(realtyIntent);
                finish();
            }
        });

        btnAdd3D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(Add3DActivity.this, RealtyAddActivity.class);
                startActivity(realtyIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent realtyIntent = new Intent(Add3DActivity.this, RealtyAddActivity.class);
        startActivity(realtyIntent);
        finish();
    }
}