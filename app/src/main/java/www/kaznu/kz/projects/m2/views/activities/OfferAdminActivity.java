package www.kaznu.kz.projects.m2.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.OfferAdminAdapter;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.User;

public class OfferAdminActivity extends AppCompatActivity {

    Button btnBack, btnRealtyHome;
    ProgressBar progressBar;
    RecyclerView rvOffers;
    OfferAdminAdapter adapter;
    ArrayList<CurrentUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_admin);
        progressBar = findViewById(R.id.pb_message_list);
        rvOffers = findViewById(R.id.rv_offers);

        users = new ArrayList<>();

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        for (int i = 0; i < 2; i++) {
            CurrentUser user = new CurrentUser(getApplicationContext());
            users.add(user);
        }

        adapter = new OfferAdminAdapter(getApplicationContext(), users, getResources().getDimensionPixelSize(R.dimen.padding_top_bottom),
                getResources().getDimensionPixelSize(R.dimen.padding_left_right));
        rvOffers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvOffers.setAdapter(adapter);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);
        adapter.setOnCardClickListner(new OfferAdminAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    LinearLayout offerPanel = Objects.requireNonNull(rvOffers.findViewHolderForAdapterPosition(i)).itemView.findViewById(R.id.offer_panel);
                    if (i == position) {
                        offerPanel.setVisibility(View.VISIBLE);
                    } else {
                        offerPanel.setVisibility(View.GONE);
                    }
                }
            }
        });

        btnBack = findViewById(R.id.btn_back);
        btnRealtyHome = findViewById(R.id.btn_home);

        btnRealtyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Offers offers = getIntent().getParcelableExtra("offers");
                Realty realty = (Realty) getIntent().getSerializableExtra("realty");
                User owner = (User) getIntent().getSerializableExtra("owner");
                ArrayList<Integer> properties = getIntent().getIntegerArrayListExtra("properties");

                Intent intent = new Intent(OfferAdminActivity.this, RealtyActivity.class);

                if (offers != null) {
                    intent.putExtra("images", offers.getImagesLink());
                }
                if (properties != null) {
                    intent.putIntegerArrayListExtra("properties", properties);
                    Log.d("M2TAG", "Properties size: " + properties.size());
                }
                if (owner != null) {
                    intent.putExtra("owner", owner.getName());
                    intent.putExtra("stars", owner.getStars());
                    intent.putExtra("avatar", owner.getImageLink());
                }
                if (realty != null) {
                    intent.putExtra("title", realty.getHeader());
                    intent.putExtra("address", realty.getAddress());
                    intent.putExtra("price", realty.getCost());
                    if (realty.getDescription() != null) {
                        intent.putExtra("body", realty.getDescription());
                    } else {
                        intent.putExtra("body", "null");
                    }
                    intent.putExtra("floor", realty.getFloor());
                    intent.putExtra("floorbuild", realty.getFloorBuild());
                    intent.putExtra("area", realty.getArea());
                    intent.putExtra("livingspace", realty.getLivingSpace());
                    intent.putExtra("ref_realty", realty.getId());
                    intent.putExtra("contact", realty.getId());
                }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}