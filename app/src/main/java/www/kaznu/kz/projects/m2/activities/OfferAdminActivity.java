package www.kaznu.kz.projects.m2.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yandex.mapkit.transport.masstransit.Line;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.OfferAdminAdapter;
import www.kaznu.kz.projects.m2.models.CurrentUser;

public class OfferAdminActivity extends AppCompatActivity {

    Button btnBack;
    ProgressBar progressBar;
    RecyclerView rvOffers;
    OfferAdminAdapter adapter;
    ArrayList<CurrentUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_admin);
        progressBar = findViewById(R.id.offers_progress);
        rvOffers = findViewById(R.id.rv_offers);

        users = new ArrayList<>();

        progressBar.setIndeterminate(true);

        for (int i = 0; i < 2; i++) {
            CurrentUser user = new CurrentUser(getApplicationContext());
            users.add(user);
        }

        adapter = new OfferAdminAdapter(getApplicationContext(), users);
        rvOffers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvOffers.setAdapter(adapter);
        progressBar.setIndeterminate(false);
        adapter.setOnCardClickListner(new OfferAdminAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

            }
        });

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}