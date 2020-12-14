package www.kaznu.kz.projects.m2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.ToggleButton;
import www.kaznu.kz.projects.m2.adapters.RealtyTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RentPeriodAdapter;
import www.kaznu.kz.projects.m2.adapters.RentTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RoomsAdapter;
import www.kaznu.kz.projects.m2.api.realty.RealtyUpdate;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.Properties;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.FlowLayout;

public class RealtyAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button btnBack;
    public TextView title;

    LinearLayout add3d;

    Button btnCreateRealty;
    Button btnPublishRealty;
    Realty realty;
    RealtyUpdate realtyUpdate;
    SharedPreferences token;
    Properties properties;
    CheckBox isAgree;

    private final ArrayList<Integer> selectedProperties = new ArrayList<>();

    FlowLayout propertiesLayout;

    Spinner spRentType, spRealtyType, spRentPeriod, spRooms;

    RealtyTypeAdapter realtyTypeAdapter;
    RentPeriodAdapter rentPeriodAdapter;
    RentTypeAdapter rentTypeAdapter;
    RoomsAdapter roomsAdapter;

    EditText etPrice;

    SwitchCompat scBargain;
    EditText etTitle, etDescription;

    Button btnAddress;

    ImageView ivImage;
    EditText etTotalArea, etLivingArea, etTotalFloors, etFloor;
    CheckBox chPrivacy;
    String totalArea = "0.0", livingArea = "0.0", price = "0.0", floor = "0", totalFloor = "0";

    Logger Log;

    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_add);
        user = new CurrentUser(this);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        btnBack = toolbar.findViewById(R.id.toolbar_back);

        btnBack.setOnClickListener(v -> finish());

        Log = new Logger(this, Constants.TAG);

        add3d = findViewById(R.id.add_3d);

        btnCreateRealty = findViewById(R.id.btn_create_post);
        btnPublishRealty = findViewById(R.id.btn_publish_ads);

        spRentType = findViewById(R.id.sp_rent_type);
        spRealtyType = findViewById(R.id.sp_realty_type);
        spRentPeriod = findViewById(R.id.sp_rent_period);
        spRooms = findViewById(R.id.sp_rooms);

        spRentType.setOnItemSelectedListener(this);
        spRealtyType.setOnItemSelectedListener(this);
        spRentPeriod.setOnItemSelectedListener(this);
        spRooms.setOnItemSelectedListener(this);

        etFloor = findViewById(R.id.et_floor);
        etTotalFloors = findViewById(R.id.et_total_floors);
        etDescription = findViewById(R.id.et_description);
        etTotalArea = findViewById(R.id.et_total_area);
        etLivingArea = findViewById(R.id.et_living_area);
        etTitle = findViewById(R.id.et_title);
        etPrice = findViewById(R.id.et_price);

        btnAddress = findViewById(R.id.btn_add_address);

        scBargain = findViewById(R.id.sw_is_bargain);
        isAgree = findViewById(R.id.cb_privacy);

        isAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                btnPublishRealty.setEnabled(true);
            }
            else {
                btnPublishRealty.setEnabled(false);
            }
        });

        propertiesLayout = findViewById(R.id.realty_properties);

        properties = new Properties(this);

        rentPeriodAdapter = new RentPeriodAdapter(this, properties.getRentPeriod());
        realtyTypeAdapter = new RealtyTypeAdapter(this, properties.getRealtyType());
        rentTypeAdapter = new RentTypeAdapter(this, properties.getDealType());
        roomsAdapter = new RoomsAdapter(this, properties.getRooms());

        spRentPeriod.setAdapter(rentPeriodAdapter);
        spRealtyType.setAdapter(realtyTypeAdapter);
        spRentType.setAdapter(rentTypeAdapter);
        spRooms.setAdapter(roomsAdapter);

        for (int i = 0; i < properties.getRealtyProperties().size(); i++) {
            addProperties(
                    Utils.toUpper(properties.getRealtyProperties().get(i).getValue()),
                    android.R.color.transparent,
                    R.drawable.button_background_light_blue,
                    properties.getRealtyProperties().get(i).getCodeId()
            );
        }

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(RealtyAddActivity.this, SearchAddressActivity.class);
                startActivityForResult(realtyIntent, 1);
            }
        });

        add3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(RealtyAddActivity.this, Add3DActivity.class);
                startActivity(realtyIntent);
            }
        });

        btnCreateRealty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Rent type: " + properties.getDealType().get(spRentType.getSelectedItemPosition()).getCodeId());
                for (int i = 0; i < selectedProperties.size(); i++) {
                    Log.d("Properties: " + selectedProperties.get(i));
                }

                totalArea = (!etTotalArea.getText().toString().equals("")) ? etTotalArea.getText().toString() : "0.0";
                price = (!etPrice.getText().toString().equals("")) ? etPrice.getText().toString() : "0.0";
                livingArea = (!etLivingArea.getText().toString().equals("")) ? etLivingArea.getText().toString() : "0.0";
                floor  = (!etFloor.getText().toString().equals("")) ? etFloor.getText().toString() : "0";
                totalFloor = (!etTotalFloors.getText().toString().equals("")) ? etTotalFloors.getText().toString() : "0";

                realty = new Realty();
                realty.setAddress(btnAddress.getText().toString());
                realty.setAge(Utils.getCurrentDateToDatabase());
                realty.setArea(Double.parseDouble(totalArea));
                realty.setCost(Double.parseDouble(price));
                realty.setDescription(etDescription.getText().toString());
                realty.setHeader(etTitle.getText().toString());
                realty.setFloor(Integer.parseInt(floor));
                realty.setFloorBuild(Integer.parseInt(totalFloor));
                realty.setLivingSpace(Double.parseDouble(livingArea));
                realty.setTransactionType(properties.getRentPeriod().get(spRentType.getSelectedItemPosition()).getCodeId());
                realty.setRefCity(9);
                realty.setRoomCount(properties.getRooms().get(spRooms.getSelectedItemPosition()).getCodeId());
                realty.setRealtyType(properties.getRealtyType().get(spRealtyType.getSelectedItemPosition()).getCodeId());
                realty.setRentPeriod(properties.getRentPeriod().get(spRentPeriod.getSelectedItemPosition()).getCodeId());
                realty.setStatus(0);

                realtyUpdate = new RealtyUpdate(RealtyAddActivity.this, realty, new Tokens(getApplicationContext()).getAccessToken());
                realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int data, String message) {
                        if(data == 1)
                            Toast.makeText(getApplicationContext(), "Realty is created", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnPublishRealty.setOnClickListener(v -> {

            totalArea = (!etTotalArea.getText().toString().equals("")) ? etTotalArea.getText().toString() : "0.0";
            price = (!etPrice.getText().toString().equals("")) ? etPrice.getText().toString() : "0.0";
            livingArea = (!etLivingArea.getText().toString().equals("")) ? etLivingArea.getText().toString() : "0.0";
            floor  = (!etFloor.getText().toString().equals("")) ? etFloor.getText().toString() : "0";
            totalFloor = (!etTotalFloors.getText().toString().equals("")) ? etTotalFloors.getText().toString() : "0";

            realty = new Realty();
            realty.setAddress(btnAddress.getText().toString());
            realty.setAge(Utils.getCurrentDateToDatabase());
            realty.setArea(Double.parseDouble(totalArea));
            realty.setCost(Double.parseDouble(price));
            realty.setDescription(etDescription.getText().toString());
            realty.setHeader(etTitle.getText().toString());
            realty.setFloor(Integer.parseInt(floor));
            realty.setFloorBuild(Integer.parseInt(totalFloor));
            realty.setLivingSpace(Double.parseDouble(livingArea));
            realty.setTransactionType(properties.getRentPeriod().get(spRentType.getSelectedItemPosition()).getCodeId());
            realty.setRefCity(9);
            realty.setRoomCount(properties.getRooms().get(spRooms.getSelectedItemPosition()).getCodeId());
            realty.setRealtyType(properties.getRealtyType().get(spRealtyType.getSelectedItemPosition()).getCodeId());
            realty.setRentPeriod(properties.getRentPeriod().get(spRentPeriod.getSelectedItemPosition()).getCodeId());
            realty.setStatus(1);

            realtyUpdate = new RealtyUpdate(this, realty, new Tokens(this).getAccessToken());
            realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                @Override
                public void onComplete(int data, String message) {
                    if(data == 1)
                        Toast.makeText(getApplicationContext(), "Realty is published", Toast.LENGTH_LONG).show();
                }
            });
        });

        title.setText("Создать объявление");

    }

    public void addProperties(String data, int unselected,
                              int selected, int props) {
        int p0 = getResources().getDimensionPixelSize(R.dimen.padding_top_bottom);
        int p1 = getResources().getDimensionPixelSize(R.dimen.padding_left_right);
        Button btnResult = new Button(this);
        btnResult.setTextSize(16);
        btnResult.setAllCaps(false);
        btnResult.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
        btnResult.setTextColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        btnResult.setPadding(p1, p0, p1, p0);
        btnResult.setText(data);
        btnResult.setStateListAnimator(null);

        toToggleProperties(btnResult, new ToggleButton(false), unselected, selected, props);

        propertiesLayout.addView(btnResult);
    }

    private void toToggleProperties(Button view, ToggleButton isSet, int unselected,
                                    int selected, int props) {

        view.setOnClickListener(v -> {
            if (isSet.isButton()) {
                isSet.setButton(false);
                view.setTextColor(RealtyAddActivity.this.getColor(R.color.color_primary_dark));
                view.setBackground(ContextCompat.getDrawable(this, unselected));
                view.setStateListAnimator(null);
                selectedProperties.remove(selectedProperties.indexOf(props));
            } else {
                isSet.setButton(true);
                view.setTextColor(RealtyAddActivity.this.getColor(android.R.color.white));
                view.setBackground(ContextCompat.getDrawable(this, selected));
                view.setStateListAnimator(null);
                selectedProperties.add(props);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            btnAddress.setText(data.getStringExtra("address"));
            Log.d(data.getStringExtra("address"));
        } else {
            btnAddress.setText("data");
            Log.d("data");
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_realty_type:
                Log.d("Realty type: " + properties.getRealtyType().get(position).getCodeId());
                break;
            case R.id.sp_rent_type:
                Log.d("Rent type: " + properties.getDealType().get(position).getCodeId());
                break;
            case R.id.sp_rent_period:
                Log.d("Rent period: " + properties.getRentPeriod().get(position).getCodeId());
                break;
            case R.id.sp_rooms:
                Log.d("Rooms: " + properties.getRooms().get(position).getCodeId());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        
    }
}