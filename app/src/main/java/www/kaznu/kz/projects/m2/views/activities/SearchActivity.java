package www.kaznu.kz.projects.m2.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.ToggleButton;
import www.kaznu.kz.projects.m2.adapters.RealtyTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RentPeriodAdapter;
import www.kaznu.kz.projects.m2.views.fragments.MapsFragment;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.Polygons;
import www.kaznu.kz.projects.m2.models.Properties;
import www.kaznu.kz.projects.m2.models.SearchDialog;
import www.kaznu.kz.projects.m2.services.GPSTracker;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.customviews.DatePickerView;
import www.kaznu.kz.projects.m2.views.customviews.FlowLayout;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SearchActivity extends AppCompatActivity implements MapsFragment.DataFromSearchArea, View.OnClickListener {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    boolean isRentOrBuy = true;
    boolean isMonthly = true;

    TextView titleRentBuy, titlePrice;

    DatePickerView datePickerView;

    String rentPeriodText, realtyTypeText;

    private final static int ALL_PERMISSIONS_RESULT = 101;

    SharedPreferences shDialogs;

    double longitude;
    double latitude;
    GPSTracker gps;

    private ArrayList<Polygons> searchData;
    private ArrayList<String> permissionsToRequest;
    private final ArrayList<Integer> rooms = new ArrayList<>();
    private final ArrayList<Integer> propertiesInt = new ArrayList<>();
    private final ArrayList<Integer> requests = new ArrayList<>();
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();

    private Properties properties;

    LinearLayout llMonthly;
    LinearLayout llRangedDate;
    LinearLayout linearLayout, polygonEdit, layoutRentPeriod, layoutRealtyType;

    Button btnSearch;
    Button btnRoom01, btnRoom02, btnRoom03, btnRoom04, btnRoom05, btnRoom06;
    Button btnBack, btnFilters;
    Button btnRent, btnBuy;
    Button btnLowerClear, btnUpperClear;

    ImageView btnPolygons;
    ImageView ivEdit, ivDelete;

    EditText etSearchPlace;
    EditText etCostLowerLimit, etCostUpperLimit;

    ToggleButton isRoom01, isRoom02, isRoom03, isRoom04, isRoom05, isRoom06;
    ToggleButton isEdit;

    Spinner realtyTypeSpinner;
    Spinner rentPeriodSpinner;

    Intent offerIntent;
    FlowLayout flowLayout, requestLayout;

    int disableDrawable;
    int enableDrawable;
    int rentPeriodInt, realtyTypeInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        properties = new Properties(this);

        btnPolygons = findViewById(R.id.btn_polygons);
        btnSearch = findViewById(R.id.btn_create_search);
        btnRent = findViewById(R.id.btn_rent);
        btnBuy = findViewById(R.id.btn_buy);
        btnBack = findViewById(R.id.toolbar_back);
        btnFilters = findViewById(R.id.btn_filters);
        btnRoom01 = findViewById(R.id.btn_room_01);
        btnRoom02 = findViewById(R.id.btn_room_02);
        btnRoom03 = findViewById(R.id.btn_room_03);
        btnRoom04 = findViewById(R.id.btn_room_04);
        btnRoom05 = findViewById(R.id.btn_room_05);
        btnRoom06 = findViewById(R.id.btn_room_06);

        etSearchPlace = findViewById(R.id.et_search_place);
        etCostLowerLimit = findViewById(R.id.et_cost_lower_limit);
        etCostUpperLimit = findViewById(R.id.et_cost_upper_limit);

        linearLayout = findViewById(R.id.search_properties);

        layoutRealtyType = findViewById(R.id.layot_realty_type);
        layoutRentPeriod = findViewById(R.id.layout_rent_period);

        ToggleButton tRent = new ToggleButton(true);
        ToggleButton tBuy = new ToggleButton(false);

        flowLayout = findViewById(R.id.comfort_settings);
        requestLayout = findViewById(R.id.request_settings);

        titleRentBuy = findViewById(R.id.rent_buy_title);
        titlePrice = findViewById(R.id.price_title);

        btnLowerClear = findViewById(R.id.btn_lower_clear);
        btnUpperClear = findViewById(R.id.btn_upper_clear);

        offerIntent = new Intent(SearchActivity.this, OfferActivity.class);

        polygonEdit = findViewById(R.id.polygon_edit);
        ivEdit = findViewById(R.id.iv_polygon_edit);
        ivDelete = findViewById(R.id.iv_polygon_delete);

        isRoom01 = new ToggleButton(false);
        isRoom02 = new ToggleButton(false);
        isRoom03 = new ToggleButton(false);
        isRoom04 = new ToggleButton(false);
        isRoom05 = new ToggleButton(false);
        isRoom06 = new ToggleButton(false);
        isEdit = new ToggleButton(false);

        disableDrawable = android.R.color.transparent;
        enableDrawable = R.drawable.button_background_light_blue;

        toToggle(btnRoom01, isRoom01);
        toToggle(btnRoom02, isRoom02);
        toToggle(btnRoom03, isRoom03);
        toToggle(btnRoom04, isRoom04);
        toToggle(btnRoom05, isRoom05);
        toToggle(btnRoom06, isRoom06);

        toToggle(polygonEdit, ivEdit, isEdit);

        btnLowerClear.setOnClickListener(v -> {
            etCostLowerLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.intro_input_phone_background));
            btnLowerClear.setVisibility(View.GONE);
            etCostLowerLimit.setText("");
            etCostLowerLimit.setEnabled(true);
            etCostLowerLimit.setTextColor(getColor(R.color.color_primary_dark));
        });

        btnUpperClear.setOnClickListener(v -> {
            etCostUpperLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.intro_input_phone_background));
            btnUpperClear.setVisibility(View.GONE);
            etCostUpperLimit.setText("");
            etCostUpperLimit.setEnabled(true);
            etCostUpperLimit.setTextColor(getColor(R.color.color_primary_dark));
        });

        etCostUpperLimit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (!etCostLowerLimit.getText().toString().matches("")) {
                    etCostLowerLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.intro_input_phone_background_blue));
                    btnLowerClear.setVisibility(View.VISIBLE);
                    etCostLowerLimit.setEnabled(false);
                    etCostLowerLimit.setTextColor(getColor(android.R.color.white));
                }
            }
        });

        etCostLowerLimit.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        double lowerCost = 0.0;
                        double upperCost = 0.0;

                        if (!etCostLowerLimit.getText().toString().matches("")) {
                            lowerCost = Double.parseDouble(etCostLowerLimit.getText().toString());
                        }

                        if (!etCostUpperLimit.getText().toString().matches("")) {
                            upperCost = Double.parseDouble(etCostUpperLimit.getText().toString());
                        }

                        if (lowerCost > upperCost) {
                            Snackbar.make(v, "Вы ввели сумму больше последней", Snackbar.LENGTH_SHORT).show();
                        } else {
                            if (!etCostLowerLimit.getText().toString().matches("")) {
                                etCostLowerLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.intro_input_phone_background_blue));
                                btnLowerClear.setVisibility(View.VISIBLE);
                                etCostLowerLimit.setEnabled(false);
                                etCostLowerLimit.setTextColor(getColor(android.R.color.white));
                            }
                        }
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        etCostUpperLimit.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        double lowerCost = 0.0;
                        double upperCost = 0.0;

                        if (!etCostLowerLimit.getText().toString().matches("")) {
                            lowerCost = Double.parseDouble(etCostLowerLimit.getText().toString());
                        }

                        if (!etCostUpperLimit.getText().toString().matches("")) {
                            upperCost = Double.parseDouble(etCostUpperLimit.getText().toString());
                        }

                        if (lowerCost > upperCost) {
                            Snackbar.make(v, "Вы ввели сумму меньше начальной", Snackbar.LENGTH_SHORT).show();
                        } else {
                            if (!etCostUpperLimit.getText().toString().matches("")) {
                                etCostUpperLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.intro_input_phone_background_blue));
                                btnUpperClear.setVisibility(View.VISIBLE);
                                etCostUpperLimit.setEnabled(false);
                                etCostUpperLimit.setTextColor(getColor(android.R.color.white));
                            }
                        }
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        addVR(getString(R.string.text_vr), 0);

        realtyTypeSpinner = findViewById(R.id.realty_type);

        btnBack.setOnClickListener(v -> finish());

        realtyTypeSpinner.setAdapter(new RealtyTypeAdapter(getApplicationContext(), properties.getRealtyType()));
        realtyTypeSpinner.setSelection(0);
        realtyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                realtyTypeText = properties.getRealtyType().get(position).getValue();
                realtyTypeInt = properties.getRealtyType().get(position).getCodeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < properties.getRequestOffers().size(); i++) {
            String upperString = properties.getRequestOffers().get(i).getValue().substring(0, 1).toUpperCase() +
                                 properties.getRequestOffers().get(i).getValue().substring(1).toLowerCase();

            addRequests(upperString, properties.getRequestOffers().get(i).getCodeId());
        }

        for (int i = 0; i < properties.getRealtyProperties().size(); i++) {
            String upperString = properties.getRealtyProperties().get(i).getValue().substring(0, 1).toUpperCase() +
                                 properties.getRealtyProperties().get(i).getValue().substring(1).toLowerCase();

            addButton(upperString, properties.getRealtyProperties().get(i).getCodeId());
        }

        shDialogs = getSharedPreferences("M2DIALOGS", 0);

        if (!shDialogs.getBoolean("search_dialog", false)) {

            SearchDialog searchDialog = new SearchDialog(this);
            searchDialog.show();

            SharedPreferences.Editor editor = shDialogs.edit();

            editor.putBoolean("search_dialog", true);
            editor.apply();
        }

        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(1);

        etSearchPlace.setOnClickListener(v ->

        {
            Intent intent = new Intent(SearchActivity.this, SearchAddressActivity.class);
            startActivity(intent);
        });

        btnFilters.setOnClickListener(v ->

        {
//            InputMethodManager imm = (InputMethodManager) etSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        });

        btnPolygons.setOnClickListener(v ->

        {
            Fragment fragment;
            Bundle bundle = new Bundle();
            if (gps != null) {
                bundle.putDouble(LATITUDE, latitude);
                bundle.putDouble(LONGITUDE, longitude);
            } else {
                Toast.makeText(getApplicationContext(), "Ошибка получения текущего положения",
                        Toast.LENGTH_SHORT).show();
            }
            fragment = new MapsFragment();
            fragment.setArguments(bundle);
            loadFragment(fragment);
        });

        llMonthly = findViewById(R.id.ll_monthly);
        llRangedDate = findViewById(R.id.ll_ranged_date);

        if (isMonthly) {
            llRangedDate.setVisibility(View.GONE);
            llMonthly.setVisibility(View.VISIBLE);
        } else {
            llRangedDate.setVisibility(View.VISIBLE);
            llMonthly.setVisibility(View.GONE);
        }

        rentPeriodSpinner = findViewById(R.id.sp_rent_period);

        Collections.sort(properties.getRentPeriod(), Directory.StringComparator);

        rentPeriodSpinner.setAdapter(new RentPeriodAdapter(getApplicationContext(), properties.getRentPeriod()));
        rentPeriodSpinner.setSelection(0);

        rentPeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rentPeriodText = properties.getRentPeriod().get(position).getValue();
                rentPeriodInt = properties.getRentPeriod().get(position).getCodeId();

                switch (position) {
                    case 0:
                    case 2:
                        isMonthly = true;
                        titlePrice.setText("Предложите вашу цену за месяц:");
                        llMonthly.setVisibility(View.VISIBLE);
                        llRangedDate.setVisibility(View.GONE);
                        break;
                    case 1:
                        isMonthly = false;
                        llMonthly.setVisibility(View.GONE);
                        titlePrice.setText("Предложите вашу цену за сутки:");
                        llRangedDate.setVisibility(View.VISIBLE);

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        datePickerView = findViewById(R.id.calendar_view);

        RadioButton rbSoon = findViewById(R.id.rb_soon_date);
        RadioButton rbDate = findViewById(R.id.rb_selected_date);
        LinearLayout llCheckedDate = findViewById(R.id.ll_checked_date);
        TextView tvCheckedDate = findViewById(R.id.tv_checked_date);

        tvCheckedDate.setText(Utils.getCurrentDate());

        rbSoon.setOnClickListener(v ->

        {
            rbSoon.setChecked(true);
            rbDate.setChecked(false);
        });

        rbDate.setOnClickListener(v ->

        {
            rbDate.setChecked(true);
            rbSoon.setChecked(false);
        });

        rbSoon.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                rbDate.setChecked(false);
                buttonView.setChecked(true);
                llCheckedDate.setVisibility(View.GONE);
                tvCheckedDate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.date_not_selected_background));
            }
        });

        rbDate.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                rbSoon.setChecked(false);
                llCheckedDate.setVisibility(View.VISIBLE);
                tvCheckedDate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_background_light_blue));
            }
        });

        RadioButton rbMonths = findViewById(R.id.rb_months);
        RadioButton rbLongTime = findViewById(R.id.rb_long_time);

        EditText etMonths = findViewById(R.id.et_months);
        etMonths.setEnabled(false);

        rbMonths.setOnClickListener(v ->

        {
            rbMonths.setChecked(true);
            rbLongTime.setChecked(false);
        });

        rbLongTime.setOnClickListener(v ->

        {
            rbMonths.setChecked(false);
            rbLongTime.setChecked(true);
        });

        rbMonths.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                rbLongTime.setChecked(false);
                etMonths.setEnabled(true);
            }
        });

        rbLongTime.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                rbMonths.setChecked(false);
                etMonths.setEnabled(false);
            }
        });

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest =

                findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
        }

        btnSearch.setOnClickListener(v ->
        {

            Logger.d(propertiesInt.size() + " <---- size");
            String loPrice = etCostLowerLimit.getText().toString();
            String upPrice = etCostUpperLimit.getText().toString();

            if (!loPrice.matches(""))
                offerIntent.putExtra("lo_price", Double.parseDouble(loPrice));

            if (!upPrice.matches(""))
                offerIntent.putExtra("up_price", Double.parseDouble(upPrice));

            if (isRentOrBuy) {
                offerIntent.putExtra("is_rent", "Аренда");
            } else {
                offerIntent.putExtra("is_rent", "Покупка");
            }

            if(isRentOrBuy) {
                offerIntent.putExtra("realty_type", realtyTypeText);
                offerIntent.putExtra("realty_type_int", realtyTypeInt);
                offerIntent.putExtra("rent_period", rentPeriodText);
                offerIntent.putExtra("rent_period_int", rentPeriodInt);
            } else {
                offerIntent.putExtra("realty_type", realtyTypeText);
                offerIntent.putExtra("realty_type_int", realtyTypeInt);
            }

            offerIntent.putExtra("date_from", Utils.parseDateDefault(datePickerView.getStartDate()));
            offerIntent.putExtra("date_to", Utils.parseDateDefault(datePickerView.getEndDate()));

            if (getRooms(rooms) != null) {
                offerIntent.putExtra("rooms", getRooms(rooms));
                offerIntent.putIntegerArrayListExtra("rooms_array", rooms);
            }

            if (searchData != null && searchData.size() > 0) {
                offerIntent.putParcelableArrayListExtra("polygons", searchData);
            }

            if (propertiesInt.size() > 0) {
                offerIntent.putIntegerArrayListExtra("properties", propertiesInt);
            }

            startActivity(offerIntent);
        });

        toToggle(btnRent, btnBuy, tRent, tBuy, isMonthly);

        addFragment(new MapsFragment());
    }

    private void loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void addFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(SearchActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }

        Logger.d("GPS ---> " + latitude + " " + longitude);

        Fragment fragment;
        Bundle bundle = new Bundle();
        if (gps != null) {
            bundle.putDouble(LATITUDE, latitude);
            bundle.putDouble(LONGITUDE, longitude);
        } else {
            Toast.makeText(getApplicationContext(), "Ошибка получения текущего положения",
                    Toast.LENGTH_SHORT).show();
        }
        fragment = new MapsFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (Object perm : wanted) {
            if (hasPermission((String) perm)) {
                result.add((String) perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
            }
        }
        return false;
    }

    private boolean canMakeSmores() {
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (Object perms : permissionsToRequest) {
                if (hasPermission((String) perms)) {
                    permissionsRejected.add((String) perms);
                }
            }

            if (permissionsRejected.size() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                        showMessageOKCancel(
                                (dialog, which) -> requestPermissions(permissionsRejected.toArray(new String[0]), ALL_PERMISSIONS_RESULT));
                    }
                }

            }
        }

    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SearchActivity.this)
                .setMessage("These permissions are mandatory for the application. Please allow access.")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void toToggle(Button view, ToggleButton isSet) {

        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                if (view.getText().toString().compareTo("6+") == 0)
                    rooms.remove(rooms.indexOf(6));
                else
                    rooms.remove(rooms.indexOf(Integer.parseInt(view.getText().toString())));
            } else {
                isSet.setButton(true);
                if (view.getText().toString().compareTo("6+") == 0) {
                    rooms.add(6);
                } else {
                    rooms.add(Integer.parseInt(view.getText().toString()));
                }
            }
        });
    }

    private void toToggle(Button view, ToggleButton isSet, int disableDrawable,
                          int enableDrawable, int props) {

        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setTextColor(getResources().getColor(R.color.color_primary_dark));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), disableDrawable));
                view.setStateListAnimator(null);
                propertiesInt.remove(propertiesInt.indexOf(props));
            } else {
                isSet.setButton(true);
                view.setTextColor(getResources().getColor(android.R.color.white));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), enableDrawable));
                view.setStateListAnimator(null);
                propertiesInt.add(props);
            }
        });
    }

    private void toToggleRequest(Button view, ToggleButton isSet, int disableDrawable,
                                 int enableDrawable, int props) {

        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setTextColor(getColor(R.color.color_primary_dark));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), disableDrawable));
                view.setStateListAnimator(null);
                requests.remove(requests.indexOf(props));
            } else {
                isSet.setButton(true);
                view.setTextColor(getColor(android.R.color.white));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), enableDrawable));
                view.setStateListAnimator(null);
                requests.add(props);
            }
        });
    }

    private void toToggleVR(Button view, ToggleButton isSet, int disableDrawable,
                            int enableDrawable, int props) {

        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setTextColor(getResources().getColor(R.color.color_primary_dark));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), disableDrawable));
                view.setStateListAnimator(null);
            } else {
                isSet.setButton(true);
                view.setTextColor(getResources().getColor(android.R.color.white));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), enableDrawable));
                view.setStateListAnimator(null);
            }
        });
    }

    private void toToggle(Button view, ToggleButton isSet, int disableDrawable,
                          int enableDrawable) {
        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setTextColor(getResources().getColor(R.color.color_primary_dark));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), disableDrawable));
            } else {
                isSet.setButton(true);
                view.setTextColor(getResources().getColor(android.R.color.white));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), enableDrawable));
            }
        });
    }

    public String getRooms(ArrayList<Integer> data) {
        Collections.sort(data);

        if (data.isEmpty()) {
            return null;
        } else {
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < data.size() - 1; i++) {
                result.append(data.get(i)).append(", ");
            }

            result.append(data.get(data.size() - 1)).append(" - комнат.");

            return result.toString();
        }
    }

    public void addButton(String data, int props) {
        int p0 = getResources().getDimensionPixelSize(R.dimen.padding_top_bottom);
        int p1 = getResources().getDimensionPixelSize(R.dimen.padding_left_right);
        Button btnResult = new Button(this);
        btnResult.setTextSize(14);
        btnResult.setAllCaps(false);
        btnResult.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
        btnResult.setTextColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        btnResult.setPadding(p1, p0, p1, p0);
        btnResult.setText(data);
        btnResult.setStateListAnimator(null);

        toToggle(btnResult, new ToggleButton(false), disableDrawable, enableDrawable, props);

        flowLayout.addView(btnResult);
    }

    public void addRequests(String data, int props) {
        int p0 = getResources().getDimensionPixelSize(R.dimen.padding_top_bottom);
        int p1 = getResources().getDimensionPixelSize(R.dimen.padding_left_right);
        Button btnResult = new Button(this);
        btnResult.setTextSize(14);
        btnResult.setAllCaps(false);
        btnResult.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
        btnResult.setTextColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        btnResult.setPadding(p1, p0, p1, p0);
        btnResult.setText(data);
        btnResult.setStateListAnimator(null);

        toToggleRequest(btnResult, new ToggleButton(false), disableDrawable, enableDrawable, props);

        requestLayout.addView(btnResult);
    }

    public void addVR(String data, int props) {
        int p0 = getResources().getDimensionPixelSize(R.dimen.padding_top_bottom);
        int p1 = getResources().getDimensionPixelSize(R.dimen.padding_left_right);
        Button btnResult = new Button(this);
        btnResult.setTextSize(14);
        btnResult.setAllCaps(false);
        btnResult.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
        btnResult.setTextColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        btnResult.setPadding(p1, p0, p1, p0);
        btnResult.setText(data);
        btnResult.setStateListAnimator(null);

        toToggleVR(btnResult, new ToggleButton(false), disableDrawable, enableDrawable, props);

        requestLayout.addView(btnResult);
    }

    private void toToggle(LinearLayout view, ImageView edit, ToggleButton isSet) {

        if (isSet.isButton()) {
            isSet.setButton(false);
            view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_polygon_on));
            edit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_edit_white));
            ivDelete.setVisibility(View.VISIBLE);
        } else {
            isSet.setButton(true);
            view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_polygon_off));
            edit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_edit_blue));
            ivDelete.setVisibility(View.GONE);
        }

        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_polygon_on));
                edit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_edit_white));
                ivDelete.setVisibility(View.VISIBLE);

                Fragment fragment;

                Bundle bundle = new Bundle();
                bundle.putBoolean("isEdit", true);

                fragment = new MapsFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);

            } else {
                isSet.setButton(true);
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_polygon_off));
                edit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_edit_blue));
                ivDelete.setVisibility(View.GONE);

                Fragment fragment;

                Bundle bundle = new Bundle();
                bundle.putBoolean("isEdit", false);

                fragment = new MapsFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
            }
        });
    }

    private void toToggle(Button rent, Button buy, ToggleButton isRent, ToggleButton isBuy,
                          boolean isM) {

        if (isRent.isButton()) {
            isRentOrBuy = true;
            titleRentBuy.setText("Я хочу арендовать:");
            titlePrice.setText("Предложите вашу цену за месяц:");
            rentPeriodSpinner.setVisibility(View.VISIBLE);
            layoutRentPeriod.setVisibility(View.VISIBLE);
            isRent.setButton(false);
            isBuy.setButton(true);
            if (isM) {
                llMonthly.setVisibility(View.VISIBLE);
                llRangedDate.setVisibility(View.GONE);
            } else {
                llMonthly.setVisibility(View.GONE);
                llRangedDate.setVisibility(View.VISIBLE);
            }

            rent.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background));
            rent.setTextColor(getColor(android.R.color.white));
            buy.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_disable_background));
            buy.setTextColor(getColor(R.color.color_primary_dark));

        } else if (isBuy.isButton()) {
            isRentOrBuy = false;
            titleRentBuy.setText("Я хочу купить:");
            titlePrice.setText("Предложите вашу цену:");
            rentPeriodSpinner.setVisibility(View.GONE);
            layoutRentPeriod.setVisibility(View.GONE);
            llMonthly.setVisibility(View.GONE);
            llRangedDate.setVisibility(View.GONE);
            isRent.setButton(true);
            isBuy.setButton(false);
            rent.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background_disable_left));
            rent.setTextColor(getColor(R.color.color_primary_dark));
            buy.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background_right));
            buy.setTextColor(getColor(android.R.color.white));
        }

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRent.isButton()) {
                    isRentOrBuy = true;
                    titleRentBuy.setText("Я хочу арендовать:");
                    titlePrice.setText("Предложите вашу цену за месяц:");
                    rentPeriodSpinner.setVisibility(View.VISIBLE);
                    layoutRentPeriod.setVisibility(View.VISIBLE);
                    isRent.setButton(false);
                    isBuy.setButton(true);

                    if (isM) {
                        llMonthly.setVisibility(View.VISIBLE);
                        llRangedDate.setVisibility(View.GONE);
                    } else {
                        llMonthly.setVisibility(View.GONE);
                        llRangedDate.setVisibility(View.VISIBLE);
                    }

                    rent.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background));
                    rent.setTextColor(getColor(android.R.color.white));
                    buy.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_disable_background));
                    buy.setTextColor(getColor(R.color.color_primary_dark));

                } else if (isBuy.isButton()) {
                    isRentOrBuy = false;
                    titleRentBuy.setText("Я хочу купить:");
                    titlePrice.setText("Предложите вашу цену:");
                    rentPeriodSpinner.setVisibility(View.GONE);
                    layoutRentPeriod.setVisibility(View.GONE);
                    llMonthly.setVisibility(View.GONE);
                    llRangedDate.setVisibility(View.GONE);
                    isRent.setButton(true);
                    isBuy.setButton(false);
                    rent.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background_disable_left));
                    rent.setTextColor(getColor(R.color.color_primary_dark));
                    buy.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background_right));
                    buy.setTextColor(getColor(android.R.color.white));
                }
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRent.isButton()) {
                    isRentOrBuy = true;
                    titleRentBuy.setText("Я хочу арендовать:");
                    titlePrice.setText("Предложите вашу цену за месяц:");
                    rentPeriodSpinner.setVisibility(View.VISIBLE);
                    layoutRentPeriod.setVisibility(View.VISIBLE);
                    isRent.setButton(false);
                    isBuy.setButton(true);
                    if (isM) {
                        llMonthly.setVisibility(View.VISIBLE);
                        llRangedDate.setVisibility(View.GONE);
                    } else {
                        llMonthly.setVisibility(View.GONE);
                        llRangedDate.setVisibility(View.VISIBLE);
                    }

                    rent.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background));
                    rent.setTextColor(getColor(android.R.color.white));
                    buy.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_disable_background));
                    buy.setTextColor(getColor(R.color.color_primary_dark));

                } else if (isBuy.isButton()) {
                    isRentOrBuy = false;
                    titleRentBuy.setText("Я хочу купить:");
                    titlePrice.setText("Предложите вашу цену:");
                    rentPeriodSpinner.setVisibility(View.GONE);
                    layoutRentPeriod.setVisibility(View.GONE);
                    llMonthly.setVisibility(View.GONE);
                    llRangedDate.setVisibility(View.GONE);
                    isRent.setButton(true);
                    isBuy.setButton(false);
                    rent.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background_disable_left));
                    rent.setTextColor(getColor(R.color.color_primary_dark));
                    buy.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toggle_button_background_right));
                    buy.setTextColor(getColor(android.R.color.white));
                }
            }
        });
    }

    @Override
    public void SearchArea(ArrayList<Polygons> area) {
        searchData = area;
    }

    @Override
    public void onClick(View v) {

    }
}
