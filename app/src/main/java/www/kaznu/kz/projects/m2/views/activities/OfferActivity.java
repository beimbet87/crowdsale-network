package www.kaznu.kz.projects.m2.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.ToggleButton;
import www.kaznu.kz.projects.m2.api.realty.FilterOffers;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.views.fragments.OfferFragment;
import www.kaznu.kz.projects.m2.views.fragments.SearchIntroFragment;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.customviews.FlowLayout;

public class OfferActivity extends AppCompatActivity {

    Button btnBack, btnHide;
    LinearLayout container, all_buttons;
    String price = null;

    Tokens tokens;

    FlowLayout flowLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        hideSystemBars();

        Intent intent = getIntent();

        tokens = new Tokens(this);

        if(!intent.getBooleanExtra("is_search", false)) {
            SearchIntroFragment introFragment = new SearchIntroFragment();

            addFragment(introFragment);
        }

        container = findViewById(R.id.offers_buttons);
        all_buttons = findViewById(R.id.properties_container);
        flowLayout = findViewById(R.id.properties);
        container.setVisibility(View.GONE);
        btnHide = findViewById(R.id.btn_hide);

        toToggle(btnHide, new ToggleButton(false),
                R.drawable.back_button_background, R.drawable.back_button_background_blue);

        if (intent.getDoubleExtra("lo_price", 0.0) > 0.0 && intent.getDoubleExtra("up_price", 0.0) <= 0.0)
            price = addText("От " + Utils.parsePrice(intent.getDoubleExtra("lo_price", 0.0)));

        else if (intent.getDoubleExtra("lo_price", 0.0) <= 0.0 && intent.getDoubleExtra("up_price", 0.0) > 0.0)
            price = addText("До " + Utils.parsePrice(intent.getDoubleExtra("up_price", 0.0)));

        else if (intent.getDoubleExtra("lo_price", 0.0) > 0.0 && intent.getDoubleExtra("up_price", 0.0) > 0.0)
            price = addText("От " + Utils.parsePrice(intent.getDoubleExtra("lo_price", 0.0))
                    + " до " + Utils.parsePrice(intent.getDoubleExtra("up_price", 0.0)));

        if (intent.getStringExtra("is_rent") != null)
            addText(intent.getStringExtra("is_rent"));

        if (intent.getStringExtra("rent_period") != null)
            addText(intent.getStringExtra("rent_period"));

        if (intent.getStringExtra("realty_type") != null)
            addText(intent.getStringExtra("realty_type"));

        if (intent.getStringExtra("rooms") != null)
            addText(intent.getStringExtra("rooms"));

        Logger.d(intent.getIntExtra("realty_type_int", 5) + " <--- realty type");
        Logger.d(intent.getIntExtra("rent_period_int", 8) + " <--- rent period");

        Filter filter = new Filter();
        filter.setRealtyType(intent.getIntExtra("realty_type_int", 5));
        filter.setRentPeriod(intent.getIntExtra("rent_period_int", 8));
        if (intent.getIntegerArrayListExtra("rooms_array") != null)
            filter.setRoomCount(intent.getIntegerArrayListExtra("rooms_array"));
        if (intent.getDoubleExtra("lo_price", 0.0) > 0.0)
            filter.setCostLowerLimit(intent.getDoubleExtra("lo_price", 0.0));
        if (intent.getDoubleExtra("up_price", 0.0) > 0.0)
            filter.setCostUpperLimit(intent.getDoubleExtra("up_price", 0.0));
        if (intent.getIntegerArrayListExtra("properties") != null)
            filter.setPropertiesId(intent.getIntegerArrayListExtra("properties"));

        if (intent.getStringExtra("date_from") != null)
            filter.setStartDate(intent.getStringExtra("date_from"));

        if (intent.getStringExtra("date_to") != null)
            filter.setEndDate(intent.getStringExtra("date_to"));

        if (intent.getParcelableArrayListExtra("polygons") != null)
            filter.setPolygons(intent.getParcelableArrayListExtra("polygons"));

        filter.setOffset(0);
        filter.setLimit(10);

        FilterOffers filterOffers = new FilterOffers(this, filter, tokens.getAccessToken());

        filterOffers.setOnLoadListener(offers -> {
            if (offers.size() > 0) {

                    OfferFragment fragment = new OfferFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("offers", offers);
                    bundle.putString("price", price);
                    fragment.setArguments(bundle);
                if(!intent.getBooleanExtra("is_search", false)) {
                    loadFragment(fragment);
                    container.setVisibility(View.VISIBLE);
                }
                else {
                    container.setVisibility(View.VISIBLE);
                    addFragment(fragment);
                }


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


    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
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