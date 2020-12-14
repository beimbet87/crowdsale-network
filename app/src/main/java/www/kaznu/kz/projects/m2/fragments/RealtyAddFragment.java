package www.kaznu.kz.projects.m2.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.ToggleButton;
import www.kaznu.kz.projects.m2.activities.Add3DActivity;
import www.kaznu.kz.projects.m2.activities.SearchAddressActivity;
import www.kaznu.kz.projects.m2.adapters.RealtyTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RentPeriodAdapter;
import www.kaznu.kz.projects.m2.adapters.RentTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RoomsAdapter;
import www.kaznu.kz.projects.m2.api.realty.RealtyUpdate;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Properties;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.FlowLayout;

public class RealtyAddFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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

    public RealtyAddFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fv = inflater.inflate(R.layout.fragment_add_realty, container, false);

        Log = new Logger(requireContext(), Constants.TAG);

        add3d = fv.findViewById(R.id.add_3d);

        btnCreateRealty = fv.findViewById(R.id.btn_create_post);
        btnPublishRealty = fv.findViewById(R.id.btn_publish_ads);

        spRentType = fv.findViewById(R.id.sp_rent_type);
        spRealtyType = fv.findViewById(R.id.sp_realty_type);
        spRentPeriod = fv.findViewById(R.id.sp_rent_period);
        spRooms = fv.findViewById(R.id.sp_rooms);

        spRentType.setOnItemSelectedListener(this);
        spRealtyType.setOnItemSelectedListener(this);
        spRentPeriod.setOnItemSelectedListener(this);
        spRooms.setOnItemSelectedListener(this);

        etFloor = fv.findViewById(R.id.et_floor);
        etTotalFloors = fv.findViewById(R.id.et_total_floors);
        etDescription = fv.findViewById(R.id.et_description);
        etTotalArea = fv.findViewById(R.id.et_total_area);
        etLivingArea = fv.findViewById(R.id.et_living_area);
        etTitle = fv.findViewById(R.id.et_title);
        etPrice = fv.findViewById(R.id.et_price);

        btnAddress = fv.findViewById(R.id.btn_add_address);

        scBargain = fv.findViewById(R.id.sw_is_bargain);
        isAgree = fv.findViewById(R.id.cb_privacy);

        isAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                btnPublishRealty.setEnabled(true);
            }
            else {
                btnPublishRealty.setEnabled(false);
            }
        });

        propertiesLayout = fv.findViewById(R.id.realty_properties);

        properties = new Properties(requireContext());

        rentPeriodAdapter = new RentPeriodAdapter(requireContext(), properties.getRentPeriod());
        realtyTypeAdapter = new RealtyTypeAdapter(requireContext(), properties.getRealtyType());
        rentTypeAdapter = new RentTypeAdapter(requireContext(), properties.getDealType());
        roomsAdapter = new RoomsAdapter(requireContext(), properties.getRooms());

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
                Intent realtyIntent = new Intent(requireActivity(), SearchAddressActivity.class);
                startActivityForResult(realtyIntent, 1);
            }
        });

        add3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(requireActivity(), Add3DActivity.class);
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

                realtyUpdate = new RealtyUpdate(requireActivity(), realty, new Tokens(requireContext()).getAccessToken());
                realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int data, String message) {
                        if(data == 1)
                            Toast.makeText(requireContext(), "Realty is created", Toast.LENGTH_LONG).show();
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

            realtyUpdate = new RealtyUpdate(requireContext(), realty, new Tokens(requireContext()).getAccessToken());
            realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                @Override
                public void onComplete(int data, String message) {
                    if(data == 1)
                        Toast.makeText(requireContext(), "Realty is published", Toast.LENGTH_LONG).show();
                }
            });
        });

        return fv;
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

    public void addProperties(String data, int unselected,
                              int selected, int props) {
        int p0 = getResources().getDimensionPixelSize(R.dimen.padding_top_bottom);
        int p1 = getResources().getDimensionPixelSize(R.dimen.padding_left_right);
        Button btnResult = new Button(requireContext());
        btnResult.setTextSize(16);
        btnResult.setAllCaps(false);
        btnResult.setBackground(ContextCompat.getDrawable(requireContext(), android.R.color.transparent));
        btnResult.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_primary_dark));
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
                view.setTextColor(requireActivity().getColor(R.color.color_primary_dark));
                view.setBackground(ContextCompat.getDrawable(requireContext(), unselected));
                view.setStateListAnimator(null);
                selectedProperties.remove(selectedProperties.indexOf(props));
            } else {
                isSet.setButton(true);
                view.setTextColor(requireActivity().getColor(android.R.color.white));
                view.setBackground(ContextCompat.getDrawable(requireContext(), selected));
                view.setStateListAnimator(null);
                selectedProperties.add(props);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null) {
            btnAddress.setText(data.getStringExtra("address"));
        }
    }
}