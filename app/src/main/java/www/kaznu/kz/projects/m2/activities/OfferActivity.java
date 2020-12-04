package www.kaznu.kz.projects.m2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.OfferAdapter;
import www.kaznu.kz.projects.m2.api.realty.FilterOffers;
import www.kaznu.kz.projects.m2.callbacks.SwipeToDeleteCallback;
import www.kaznu.kz.projects.m2.fragments.OfferFragment;
import www.kaznu.kz.projects.m2.fragments.SearchIntroFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.OfferDialog;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.views.FlowLayout;

public class OfferActivity extends AppCompatActivity {

    Button btnBack;
    LinearLayout containter;
    SharedPreferences token;

    FlowLayout flowLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Logger Log = new Logger(this, Constants.TAG);

        SearchIntroFragment introFragment = new SearchIntroFragment();

        addFragment(introFragment);
        containter = findViewById(R.id.offers_buttons);
        flowLayout = findViewById(R.id.properties);
        containter.setVisibility(View.GONE);

        Intent intent = getIntent();

        if (intent.getStringExtra("lo_price") != null && intent.getStringExtra("up_price") == null)
            addText("От " + intent.getStringExtra("lo_price") + " ₸");

        if (intent.getStringExtra("lo_price") == null && intent.getStringExtra("up_price") != null)
            addText("До " + intent.getStringExtra("up_price") + " ₸");

        if (intent.getStringExtra("lo_price") != null && intent.getStringExtra("up_price") != null)
            addText("От " + intent.getStringExtra("lo_price") + " ₸ до " + intent.getStringExtra("up_price") + " ₸");

        if (intent.getStringExtra("is_rent") != null)
            addText(intent.getStringExtra("is_rent"));

        if (intent.getStringExtra("rent_period") != null)
            addText(intent.getStringExtra("rent_period"));

        if (intent.getStringExtra("realty_type") != null)
            addText(intent.getStringExtra("realty_type"));

        if (intent.getStringExtra("rooms") != null)
            addText(intent.getStringExtra("rooms"));

        Log.d(intent.getStringExtra("lo_price"));
        Log.d(intent.getStringExtra("up_price"));


        Filter filter = new Filter();
//        filter.setRealtyType(6);
//        ArrayList<Integer> roomCount = new ArrayList<>();
//        roomCount.add(1);
//        roomCount.add(2);
//        roomCount.add(3);
//        roomCount.add(4);
//        roomCount.add(5);
//        filter.setRoomCount(roomCount);
//        filter.setCostLowerLimit(0.0);
//        filter.setCostUpperLimit(250000.0);
        filter.setOffset(0);
        filter.setLimit(10);

        token = getSharedPreferences("M2_TOKEN", 0);

        FilterOffers filterOffers = new FilterOffers(this, filter, token.getString("access_token", ""));

        filterOffers.setOnLoadListener(offers -> {
            if (offers.size() > 0) {
                OfferFragment fragment = new OfferFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("offers", offers);
                fragment.setArguments(bundle);

                loadFragment(fragment);
                containter.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Поиск не дал результатов!", Toast.LENGTH_SHORT).show();
                finish();
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

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.offer_container, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
            return true;
        }
        return false;
    }

    public void addText(String data) {
        int padding = getResources().getDimensionPixelSize(R.dimen.profile_rating_margin);
        TextView textView = new TextView(this);
        textView.setTextSize(13);
        textView.setBackground(getDrawable(R.drawable.view_profile_button_background));
        textView.setTextColor(getResources().getColor(R.color.color_primary_dark));
        textView.setMaxLines(1);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(data);
        flowLayout.addView(textView);
    }

    private boolean addFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.offer_container, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
            return true;
        }
        return false;
    }
}