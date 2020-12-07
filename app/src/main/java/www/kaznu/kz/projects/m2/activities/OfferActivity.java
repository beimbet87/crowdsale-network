package www.kaznu.kz.projects.m2.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.ToggleButton;
import www.kaznu.kz.projects.m2.api.realty.FilterOffers;
import www.kaznu.kz.projects.m2.fragments.OfferFragment;
import www.kaznu.kz.projects.m2.fragments.SearchIntroFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.FlowLayout;

public class OfferActivity extends AppCompatActivity {

    Button btnBack, btnHide;
    LinearLayout container, all_buttons;
    SharedPreferences token;
    String price = null;

    FlowLayout flowLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Logger Log = new Logger(this, Constants.TAG);

        SearchIntroFragment introFragment = new SearchIntroFragment();

        addFragment(introFragment);
        container = findViewById(R.id.offers_buttons);
        all_buttons = findViewById(R.id.properties_container);
        flowLayout = findViewById(R.id.properties);
        container.setVisibility(View.GONE);
        btnHide = findViewById(R.id.btn_hide);

        toToggle(btnHide, new ToggleButton(false),
                R.drawable.back_button_background, R.drawable.back_button_background_blue);

        Intent intent = getIntent();

        if (intent.getStringExtra("lo_price") != null && intent.getStringExtra("up_price") == null)
            price = addText("От " + Utils.parsePrice(Double.parseDouble(intent.getStringExtra("lo_price")), ""));

        if (intent.getStringExtra("lo_price") == null && intent.getStringExtra("up_price") != null)
            price = addText("До " + Utils.parsePrice(Double.parseDouble(intent.getStringExtra("up_price")), ""));

        if (intent.getStringExtra("lo_price") != null && intent.getStringExtra("up_price") != null)
            price = addText("От " + Utils.parsePrice(Double.parseDouble(intent.getStringExtra("lo_price")), "")
                    + " до " + Utils.parsePrice(Double.parseDouble(intent.getStringExtra("up_price")), ""));

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
        filter.setRealtyType(intent.getIntExtra("realty_type_int", 5));
        filter.setRentPeriod(intent.getIntExtra("rent_period_int", 8));
        if (intent.getIntegerArrayListExtra("rooms_array") != null)
            filter.setRoomCount(intent.getIntegerArrayListExtra("rooms_array"));
        if (intent.getStringExtra("lo_price") != null)
            filter.setCostLowerLimit(Double.parseDouble(intent.getStringExtra("lo_price")));
        if (intent.getStringExtra("up_price") != null)
            filter.setCostUpperLimit(Double.parseDouble(intent.getStringExtra("up_price")));
        if (intent.getIntegerArrayListExtra("properties") != null)
            filter.setPropertiesId(intent.getIntegerArrayListExtra("properties"));

        if (intent.getStringExtra("date_from") != null)
            filter.setStartDate(intent.getStringExtra("date_from"));

        if (intent.getStringExtra("date_to") != null)
            filter.setEndDate(intent.getStringExtra("date_to"));

        filter.setOffset(0);
        filter.setLimit(10);

        token = getSharedPreferences("M2_TOKEN", 0);

        FilterOffers filterOffers = new FilterOffers(this, filter, token.getString("access_token", ""));

        filterOffers.setOnLoadListener(offers -> {
            if (offers.size() > 0) {
                OfferFragment fragment = new OfferFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("offers", offers);
                bundle.putString("price", price);
                fragment.setArguments(bundle);

                loadFragment(fragment);
                container.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Поиск не дал результатов!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.offer_container, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        }
    }

    public String addText(String data) {
        int padding = getResources().getDimensionPixelSize(R.dimen.profile_rating_margin);
        TextView textView = new TextView(this);
        textView.setTextSize(13);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.view_profile_button_background));
        textView.setTextColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        textView.setMaxLines(1);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(data);
        flowLayout.addView(textView);
        return data;
    }

    private void toToggle(Button view, ToggleButton isSet, int disableDrawable, int enableDrawable) {
        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setTextColor(getResources().getColor(R.color.color_primary_dark));
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hide_inner_blue,0,0,0);
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), disableDrawable));
                all_buttons.setVisibility(View.VISIBLE);
            } else {
                isSet.setButton(true);
                view.setTextColor(getResources().getColor(android.R.color.white));
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hide_inner,0,0,0);
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), enableDrawable));
                all_buttons.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void addFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.offer_container, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        }
    }
}