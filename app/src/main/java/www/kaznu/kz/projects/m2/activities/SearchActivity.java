package www.kaznu.kz.projects.m2.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import java.util.ArrayList;
import java.util.Collections;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.ToggleButton;
import www.kaznu.kz.projects.m2.adapters.RealtyTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RentPeriodAdapter;
import www.kaznu.kz.projects.m2.api.DealType;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.api.RealtyType;
import www.kaznu.kz.projects.m2.api.RentPeriod;
import www.kaznu.kz.projects.m2.api.RequestOffers;
import www.kaznu.kz.projects.m2.fragments.MapsFragment;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.SearchDialog;
import www.kaznu.kz.projects.m2.services.GPSTracker;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.DatePickerView;
import www.kaznu.kz.projects.m2.views.FlowLayout;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SearchActivity extends IntroActivity {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    boolean isRent = true;

    private ArrayList<String> permissionsToRequest;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();

    DatePickerView datePickerView;

    private final static int ALL_PERMISSIONS_RESULT = 101;

    SharedPreferences shDialogs;

    double longitude;
    double latitude;
    GPSTracker gps;

    ArrayList<Integer> rooms = new ArrayList<>();
    ArrayList<Integer> properties = new ArrayList<>();

    Button btnSearch;
    ImageView btnFilter;
    LinearLayout linearLayout, polygonEdit;
    ImageView ivEdit, ivDelete;
    EditText etSearch;

    String rentPeriodText, realtyTypeText;
    int rentPeriodInt, realtyTypeInt;

    Button btnRoom01, btnRoom02, btnRoom03, btnRoom04, btnRoom05, btnRoom06;
    ToggleButton isRoom01, isRoom02, isRoom03, isRoom04, isRoom05, isRoom06;
    ToggleButton isFacility01, isFacility02, isFacility03, isFacility04, isFacility05;
    ToggleButton isFacility06, isFacility07;
    ToggleButton isEdit;

    ToggleButton isAdditional01, isAdditional02;

    Button btnFacility01, btnFacility02, btnFacility03, btnFacility04, btnFacility05;
    Button btnFacility06, btnFacility07;

    Button btnAdditional01, btnAdditional02;
    Button btnBack, btnOpenSearch;

    Spinner realtyTypeSpinner;
    Spinner rentPeriodSpinner;

    EditText etCostLowerLimit, etCostUpperLimit;

    Intent offerIntent;
    FlowLayout flowLayout;

    int disableDrawable;
    int enableDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnFilter = findViewById(R.id.btn_filter);
        btnSearch = findViewById(R.id.btn_create_search);
        linearLayout = findViewById(R.id.search_properties);
        etSearch = findViewById(R.id.et_search);
        etCostLowerLimit = findViewById(R.id.et_cost_lower_limit);
        etCostUpperLimit = findViewById(R.id.et_cost_upper_limit);

        btnBack = findViewById(R.id.toolbar_back);
        btnOpenSearch = findViewById(R.id.btn_open_search);

        flowLayout = findViewById(R.id.comfort_settings);

        offerIntent = new Intent(SearchActivity.this, OfferActivity.class);

        btnRoom01 = findViewById(R.id.btn_room_01);
        btnRoom02 = findViewById(R.id.btn_room_02);
        btnRoom03 = findViewById(R.id.btn_room_03);
        btnRoom04 = findViewById(R.id.btn_room_04);
        btnRoom05 = findViewById(R.id.btn_room_05);
        btnRoom06 = findViewById(R.id.btn_room_06);

        btnFacility01 = findViewById(R.id.btn_facility_01);
        btnFacility02 = findViewById(R.id.btn_facility_02);
        btnFacility03 = findViewById(R.id.btn_facility_03);
        btnFacility04 = findViewById(R.id.btn_facility_04);
        btnFacility05 = findViewById(R.id.btn_facility_05);
        btnFacility06 = findViewById(R.id.btn_facility_06);
        btnFacility07 = findViewById(R.id.btn_facility_07);

        btnAdditional01 = findViewById(R.id.btn_additional_params01);
        btnAdditional02 = findViewById(R.id.btn_additional_params02);

        polygonEdit = findViewById(R.id.polygon_edit);
        ivEdit = findViewById(R.id.iv_polygon_edit);
        ivDelete = findViewById(R.id.iv_polygon_delete);

        isRoom01 = new ToggleButton(false);
        isRoom02 = new ToggleButton(false);
        isRoom03 = new ToggleButton(false);
        isRoom04 = new ToggleButton(false);
        isRoom05 = new ToggleButton(false);
        isRoom06 = new ToggleButton(false);


        isAdditional01 = new ToggleButton(false);
        isAdditional02 = new ToggleButton(false);

        isEdit = new ToggleButton(false);

        disableDrawable = android.R.color.transparent;
        enableDrawable = R.drawable.button_background_light_blue;

        toToggle(btnAdditional01, isAdditional01, disableDrawable, enableDrawable);
        toToggle(btnAdditional02, isAdditional02, disableDrawable, enableDrawable);

        toToggle(btnRoom01, isRoom01);
        toToggle(btnRoom02, isRoom02);
        toToggle(btnRoom03, isRoom03);
        toToggle(btnRoom04, isRoom04);
        toToggle(btnRoom05, isRoom05);
        toToggle(btnRoom06, isRoom06);

        toToggle(polygonEdit, ivEdit, isEdit);

        RealtyType realtyType = new RealtyType(this);

        realtyTypeSpinner = findViewById(R.id.realty_type);

        btnBack.setOnClickListener(v -> finish());

        realtyType.setOnLoadListener(data -> {
            realtyTypeSpinner.setAdapter(new RealtyTypeAdapter(getApplicationContext(), data));
            realtyTypeSpinner.setSelection(0);
            realtyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    realtyTypeText = data.get(position).getValue();
                    realtyTypeInt = data.get(position).getCodeId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        });

        RequestOffers requestOffers = new RequestOffers(this);

        requestOffers.setOnLoadListener(data -> {
            for (int i = 0; i < data.size(); i++) {
                Log.d("M2TAG", "Request offers: " + data.get(i).getValue());
            }
        });

        RealtyProperties realtyProperties = new RealtyProperties(this);
        realtyProperties.setOnLoadListener(data -> {
            for (int i = 0; i < data.size(); i++) {
                String upperString = data.get(i).getValue().substring(0, 1).toUpperCase() + data.get(i).getValue().substring(1).toLowerCase();
                addButton(upperString, data.get(i).getCodeId());
            }
        });

        DealType dealType = new DealType(this);
        dealType.setOnLoadListener(data -> {
            for (int i = 0; i < data.size(); i++) {
                Log.d("M2TAG", "Deal type: " + data.get(i).getValue());
            }
        });

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

        etSearch.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, SearchAddressActivity.class);
            startActivity(intent);
        });

        btnOpenSearch.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) etSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        });

        btnFilter.setOnClickListener(v -> {
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

        LinearLayout llMonthly = findViewById(R.id.ll_monthly);
        LinearLayout llRangedDate = findViewById(R.id.ll_ranged_date);
        llRangedDate.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams paramsRangedDate = llRangedDate.getLayoutParams();
        paramsRangedDate.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        llRangedDate.setLayoutParams(paramsRangedDate);
        llMonthly.setVisibility(View.INVISIBLE);

        ViewGroup.LayoutParams paramsMonths = llMonthly.getLayoutParams();
        paramsMonths.height = 0;
        llMonthly.setLayoutParams(paramsMonths);

        rentPeriodSpinner = findViewById(R.id.rent_period);
        RentPeriod rentPeriod = new RentPeriod(this);

        rentPeriod.setOnLoadListener(data -> {

            Collections.sort(data, Directory.StringComparator);

            rentPeriodSpinner.setAdapter(new RentPeriodAdapter(getApplicationContext(), data));
            rentPeriodSpinner.setSelection(0);

            rentPeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    rentPeriodText = data.get(position).getValue();
                    rentPeriodInt = data.get(position).getCodeId();

                    switch (position) {
                        case 0:
                        case 2:
                            llMonthly.setVisibility(View.INVISIBLE);

                            ViewGroup.LayoutParams paramsMonths12 = llMonthly.getLayoutParams();
                            paramsMonths12.height = 0;
                            llMonthly.setLayoutParams(paramsMonths12);
                            llRangedDate.setVisibility(View.VISIBLE);
                            ViewGroup.LayoutParams paramsRangedDate12 = llRangedDate.getLayoutParams();
                            paramsRangedDate12.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            llRangedDate.setLayoutParams(paramsRangedDate12);
                            break;
                        case 1:
                            llMonthly.setVisibility(View.VISIBLE);

                            ViewGroup.LayoutParams paramsMonths1 = llMonthly.getLayoutParams();
                            paramsMonths1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            llMonthly.setLayoutParams(paramsMonths1);

                            llRangedDate.setVisibility(View.INVISIBLE);
                            ViewGroup.LayoutParams paramsRangedDate1 = llRangedDate.getLayoutParams();
                            paramsRangedDate1.height = 0;
                            llRangedDate.setLayoutParams(paramsRangedDate1);
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        });

        datePickerView = findViewById(R.id.calendar_view);

        RadioButton rbSoon = findViewById(R.id.rb_soon_date);
        RadioButton rbDate = findViewById(R.id.rb_selected_date);
        LinearLayout llCheckedDate = findViewById(R.id.ll_checked_date);
        TextView tvCheckedDate = findViewById(R.id.tv_checked_date);
        ViewGroup.LayoutParams params = llCheckedDate.getLayoutParams();
        params.height = 0;
        llCheckedDate.setLayoutParams(params);

        rbSoon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbDate.setChecked(false);
                llCheckedDate.setVisibility(View.INVISIBLE);
                params.height = 0;
                llCheckedDate.setLayoutParams(params);
                tvCheckedDate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.date_not_selected_background));
            }
        });

        rbDate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbSoon.setChecked(false);
                llCheckedDate.setVisibility(View.VISIBLE);
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                llCheckedDate.setLayoutParams(params);
                tvCheckedDate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_background_light_blue));
            }
        });

        RadioButton rbMonths = findViewById(R.id.rb_months);
        RadioButton rbLongTime = findViewById(R.id.rb_long_time);

        EditText etMonths = findViewById(R.id.et_months);
        etMonths.setEnabled(false);

        rbMonths.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbLongTime.setChecked(false);
                etMonths.setEnabled(true);
            }
        });

        rbLongTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbMonths.setChecked(false);
                etMonths.setEnabled(false);
            }
        });

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
        }

        btnSearch.setOnClickListener(v -> {

            new Logger(this, Constants.TAG).d(properties.size() + " <---- size");
            String loPrice = etCostLowerLimit.getText().toString();
            String upPrice = etCostUpperLimit.getText().toString();

            if (!loPrice.matches(""))
                offerIntent.putExtra("lo_price", loPrice);

            if (!upPrice.matches(""))
                offerIntent.putExtra("up_price", upPrice);

            if (isRent) {
                offerIntent.putExtra("is_rent", "Аренда");
            } else {
                offerIntent.putExtra("is_rent", "Покупка");
            }

            offerIntent.putExtra("realty_type", realtyTypeText);
            offerIntent.putExtra("realty_type_int", realtyTypeInt);
            offerIntent.putExtra("rent_period", rentPeriodText);
            offerIntent.putExtra("rent_period_int", rentPeriodInt);

            offerIntent.putExtra("date_from", Utils.parseDateDefault(datePickerView.getStartDate()));
            offerIntent.putExtra("date_to", Utils.parseDateDefault(datePickerView.getEndDate()));

            if(getRooms(rooms) != null) {
                offerIntent.putExtra("rooms", getRooms(rooms));
                offerIntent.putIntegerArrayListExtra("rooms_array", rooms);
            }

            if(properties.size() > 0) {
                offerIntent.putIntegerArrayListExtra("properties", properties);
            }

            for(int i = 0; i < rooms.size(); i++) {
                Log.d("M2TAG", rooms.get(i).toString());
            }

            startActivity(offerIntent);
        });

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

        Log.d("GPS", latitude + " " + longitude);

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
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

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
                if(view.getText().toString().compareTo("6+") == 0)
                    rooms.remove(rooms.indexOf(6));
                else
                    rooms.remove(rooms.indexOf(Integer.parseInt(view.getText().toString())));
            } else {
                isSet.setButton(true);
                if(view.getText().toString().compareTo("6+") == 0) {
                    rooms.add(6);
                }
                else {
                    rooms.add(Integer.parseInt(view.getText().toString()));
                }
            }
        });
    }

    private void toToggle(Button view, ToggleButton isSet, int disableDrawable, int enableDrawable, int props) {

        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setTextColor(getResources().getColor(R.color.color_primary_dark));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), disableDrawable));
                properties.remove(properties.indexOf(props));
            } else {
                isSet.setButton(true);
                view.setTextColor(getResources().getColor(android.R.color.white));
                view.setBackground(ContextCompat.getDrawable(getApplicationContext(), enableDrawable));
                properties.add(props);
            }
        });
    }

    private void toToggle(Button view, ToggleButton isSet, int disableDrawable, int enableDrawable) {
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

        if(data.isEmpty()) {
            return null;
        }
        else {
            StringBuilder result = new StringBuilder();

            for(int i = 0; i < data.size() - 1; i++) {
                result.append(data.get(i)).append(", ");
            }

            result.append(data.get(data.size()-1)).append(" - комнат.");

            return result.toString();
        }
    }

    public void addButton(String data, int props) {
        int padding = getResources().getDimensionPixelSize(R.dimen.activity_margin);
        Button btnResult = new Button(this);
        btnResult.setTextSize(16);
        btnResult.setAllCaps(false);
        btnResult.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
        btnResult.setTextColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        btnResult.setPadding(padding, padding, padding, padding);
        btnResult.setText(data);

        toToggle(btnResult, new ToggleButton(false), disableDrawable, enableDrawable, props);

        flowLayout.addView(btnResult);
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
}
