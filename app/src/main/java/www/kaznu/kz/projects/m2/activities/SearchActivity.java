package www.kaznu.kz.projects.m2.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.SearchDialog;
import www.kaznu.kz.projects.m2.services.GPSTracker;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SearchActivity extends IntroActivity {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;

    double longitude;
    double latitude;
    GPSTracker gps;

    Button btnSearch;
    ImageView btnFilter;
    LinearLayout linearLayout;
    EditText etSearch;

    Button btnRoom01, btnRoom02, btnRoom03, btnRoom04, btnRoom05, btnRoom06;
    ToggleButton isRoom01, isRoom02, isRoom03, isRoom04, isRoom05, isRoom06;
    ToggleButton isFacility01, isFacility02, isFacility03, isFacility04, isFacility05;
    ToggleButton isFacility06, isFacility07;

    ToggleButton isAdditional01, isAdditional02;

    Button btnFacility01, btnFacility02, btnFacility03, btnFacility04, btnFacility05;
    Button btnFacility06, btnFacility07;

    Button btnAdditional01, btnAdditional02;

    EditText etCostLowerLimit, etCostUpperLimit;

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

        Offers offers = new Offers();

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

        isRoom01 = new ToggleButton(false);
        isRoom02 = new ToggleButton(false);
        isRoom03 = new ToggleButton(false);
        isRoom04 = new ToggleButton(false);
        isRoom05 = new ToggleButton(false);
        isRoom06 = new ToggleButton(false);

        isFacility01 = new ToggleButton(false);
        isFacility02 = new ToggleButton(false);
        isFacility03 = new ToggleButton(false);
        isFacility04 = new ToggleButton(false);
        isFacility05 = new ToggleButton(false);
        isFacility06 = new ToggleButton(false);
        isFacility07 = new ToggleButton(false);

        isAdditional01 = new ToggleButton(false);
        isAdditional02 = new ToggleButton(false);

        int disableDrawable = R.drawable.search_button_disable_background;
        int enableDrawable = R.drawable.button_background_light_blue;

        disableDrawable = android.R.color.transparent;

        toToggle(btnFacility02, isFacility02, disableDrawable, enableDrawable);
        toToggle(btnFacility03, isFacility03, disableDrawable, enableDrawable);
        toToggle(btnFacility04, isFacility04, disableDrawable, enableDrawable);
        toToggle(btnFacility05, isFacility05, disableDrawable, enableDrawable);
        toToggle(btnFacility06, isFacility06, disableDrawable, enableDrawable);
        toToggle(btnFacility07, isFacility07, disableDrawable, enableDrawable);

        toToggle(btnAdditional01, isAdditional01, disableDrawable, enableDrawable);
        toToggle(btnAdditional02, isAdditional02, disableDrawable, enableDrawable);

        RealtyType realtyType = new RealtyType(this);

        Spinner realtyTypeSpinner = findViewById(R.id.realty_type);

        realtyType.setOnLoadListener(new RealtyType.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Directory> data) {
                realtyTypeSpinner.setAdapter(new RealtyTypeAdapter(getApplicationContext(), data));
                realtyTypeSpinner.setSelection(0);
                realtyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        RequestOffers requestOffers = new RequestOffers(this);

        requestOffers.setOnLoadListener(new RequestOffers.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Directory> data) {
                for(int i = 0; i < data.size(); i++) {
                    Log.d("M2TAG", "Request offers: " + data.get(i).getValue());
                }
            }
        });

        RealtyProperties realtyProperties = new RealtyProperties(this);
        realtyProperties.setOnLoadListener(new RealtyProperties.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Directory> data) {
                for(int i = 0; i < data.size(); i++) {
                    Log.d("M2TAG", "Realty properties: " + data.get(i).getValue());
                }
            }
        });

        DealType dealType = new DealType(this);
        dealType.setOnLoadListener(new DealType.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Directory> data) {
                for(int i = 0; i < data.size(); i++) {
                    Log.d("M2TAG", "Deal type: " + data.get(i).getValue());
                }
            }
        });

        SearchDialog searchDialog = new SearchDialog(this);
        searchDialog.show();

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(1);
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) etSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchIntroActivity.class);
                startActivity(intent);
            }
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

        Spinner rentPeriodSpinner = findViewById(R.id.rent_period);
        RentPeriod rentPeriod = new RentPeriod(this);

        rentPeriod.setOnLoadListener(new RentPeriod.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Directory> data) {

                Collections.sort(data, Directory.StringComparator);

                rentPeriodSpinner.setAdapter(new RentPeriodAdapter(getApplicationContext(), data));
                rentPeriodSpinner.setSelection(0);

                rentPeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0:
                            case 2:
                                llMonthly.setVisibility(View.INVISIBLE);

                                ViewGroup.LayoutParams paramsMonths = llMonthly.getLayoutParams();
                                paramsMonths.height = 0;
                                llMonthly.setLayoutParams(paramsMonths);
                                llRangedDate.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams paramsRangedDate = llRangedDate.getLayoutParams();
                                paramsRangedDate.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                llRangedDate.setLayoutParams(paramsRangedDate);
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
            }
        });

        RadioButton rbSoon = findViewById(R.id.rb_soon_date);
        RadioButton rbDate = findViewById(R.id.rb_selected_date);
        LinearLayout llCheckedDate = findViewById(R.id.ll_checked_date);
        TextView tvCheckedDate = findViewById(R.id.tv_checked_date);
        ViewGroup.LayoutParams params = llCheckedDate.getLayoutParams();
        params.height = 0;
        llCheckedDate.setLayoutParams(params);

        rbSoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbDate.setChecked(false);
                    llCheckedDate.setVisibility(View.INVISIBLE);
                    params.height = 0;
                    llCheckedDate.setLayoutParams(params);
                    tvCheckedDate.setBackground(getResources().getDrawable(R.drawable.date_not_selected_background));
                }
            }
        });

        rbDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbSoon.setChecked(false);
                    llCheckedDate.setVisibility(View.VISIBLE);
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    llCheckedDate.setLayoutParams(params);
                    tvCheckedDate.setBackground(getResources().getDrawable(R.drawable.button_background_light_blue));
                }
            }
        });

        RadioButton rbMonths = findViewById(R.id.rb_months);
        RadioButton rbLongTime = findViewById(R.id.rb_long_time);

        EditText etMonths = findViewById(R.id.et_months);
        etMonths.setEnabled(false);

        rbMonths.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbLongTime.setChecked(false);
                    etMonths.setEnabled(true);
                }
            }
        });

        rbLongTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbMonths.setChecked(false);
                    etMonths.setEnabled(false);
                }
            }
        });

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
        }


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
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(permissionsRejected.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
                                    }
                                });
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

    private void toToggle(Button view, ToggleButton isSet, int disableDrawable, int enableDrawable) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSet.isButton()) {
                    isSet.setButton(false);
                    view.setTextColor(getResources().getColor(R.color.color_primary_dark));
                    view.setBackground(getResources().getDrawable(disableDrawable));
                } else {
                    isSet.setButton(true);
                    view.setTextColor(getResources().getColor(android.R.color.white));
                    view.setBackground(getResources().getDrawable(enableDrawable));
                }
            }
        });
    }
}
