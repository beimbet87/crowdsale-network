package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.realty.RealtyUpdate;
import www.kaznu.kz.projects.m2.models.Realty;

public class RealtyAddActivity extends AppCompatActivity {

    LinearLayout add3d;

    Button btnCreateRealty;
    Button btnPublishRealty;
    Realty realty;
    RealtyUpdate realtyUpdate;
    SharedPreferences token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_add);
        add3d = findViewById(R.id.add_3d);

        btnCreateRealty = findViewById(R.id.btn_create_post);
        btnPublishRealty = findViewById(R.id.btn_publish_ads);

        token = getSharedPreferences("M2_TOKEN", 0);

        realty = new Realty();
        realty.setAddress("мкр. Самал, 155");
        realty.setAge("2020-07-23T02:34:56.8768464-07:00");
        realty.setArea(64);
        realty.setCost(85000);
        realty.setDescription("3х комнатная квартира, лучшая и неугловая");
        realty.setHeader("2х комнатная квартира");
        realty.setFloor(3);
        realty.setFloorBuild(9);
        realty.setLatitude(43.2703876);
        realty.setLongitude(76.8845509);
        realty.setLivingSpace(60);
        realty.setTransactionType(1);
        realty.setRefCity(9);
        realty.setRoomCount(2);
        realty.setRealtyType(5);
        realty.setRentPeriod(9);

        add3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(RealtyAddActivity.this, Add3DActivity.class);
                startActivity(realtyIntent);
                finish();
            }
        });

        btnCreateRealty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realty.setStatus(0);
                realtyUpdate = new RealtyUpdate(getApplicationContext(), realty, token.getString("access_token", ""));
                realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int data, String message) {
                        if(data == 1)
                            Toast.makeText(getApplicationContext(), "Realty is created", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnPublishRealty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realty.setStatus(1);
                realtyUpdate = new RealtyUpdate(getApplicationContext(), realty, token.getString("access_token", ""));
                realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int data, String message) {
                        if(data == 1)
                            Toast.makeText(getApplicationContext(), "Realty is published", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}