package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.Add3DActivity;
import www.kaznu.kz.projects.m2.activities.SearchAddressActivity;
import www.kaznu.kz.projects.m2.adapters.RealtyTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RentPeriodAdapter;
import www.kaznu.kz.projects.m2.adapters.RentTypeAdapter;
import www.kaznu.kz.projects.m2.adapters.RoomsAdapter;
import www.kaznu.kz.projects.m2.api.realty.RealtyUpdate;
import www.kaznu.kz.projects.m2.models.Properties;
import www.kaznu.kz.projects.m2.models.Realty;

public class RealtyAddFragment extends Fragment {

    LinearLayout add3d;

    Button btnCreateRealty;
    Button btnPublishRealty;
    Realty realty;
    RealtyUpdate realtyUpdate;
    SharedPreferences token;
    Properties properties;

    Spinner spRentType, spRealtyType, spRentPeriod, spRoomCount;

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
        add3d = fv.findViewById(R.id.add_3d);

        btnCreateRealty = fv.findViewById(R.id.btn_create_post);
        btnPublishRealty = fv.findViewById(R.id.btn_publish_ads);

        spRentType = fv.findViewById(R.id.sp_rent_type);
        spRealtyType = fv.findViewById(R.id.sp_realty_type);
        spRentPeriod = fv.findViewById(R.id.sp_rent_period);
        spRoomCount = fv.findViewById(R.id.sp_rooms);

        etFloor = fv.findViewById(R.id.et_floor);
        etTotalFloors = fv.findViewById(R.id.et_total_floors);
        etDescription = fv.findViewById(R.id.et_description);
        etTotalArea = fv.findViewById(R.id.et_total_area);
        etLivingArea = fv.findViewById(R.id.et_living_area);
        etTitle = fv.findViewById(R.id.et_title);
        etPrice = fv.findViewById(R.id.et_price);

        btnAddress = fv.findViewById(R.id.btn_add_address);

        scBargain = fv.findViewById(R.id.sw_is_bargain);

        properties = new Properties(requireContext());

        rentPeriodAdapter = new RentPeriodAdapter(requireContext(), properties.getRentPeriod());
        realtyTypeAdapter = new RealtyTypeAdapter(requireContext(), properties.getRealtyType());
        rentTypeAdapter = new RentTypeAdapter(requireContext(), properties.getDealType());
        roomsAdapter = new RoomsAdapter(requireContext(), properties.getRooms());

        spRentPeriod.setAdapter(rentPeriodAdapter);
        spRealtyType.setAdapter(realtyTypeAdapter);
        spRentType.setAdapter(rentTypeAdapter);
        spRoomCount.setAdapter(roomsAdapter);

        totalArea = (!etTotalArea.getText().toString().equals("")) ? etTotalArea.getText().toString() : "0.0";
        price = (!etPrice.getText().toString().equals("")) ? etPrice.getText().toString() : "0.0";
        livingArea = (!etLivingArea.getText().toString().equals("")) ? etLivingArea.getText().toString() : "0.0";
        floor  = (!etFloor.getText().toString().equals("")) ? etFloor.getText().toString() : "0";
        totalFloor = (!etTotalFloors.getText().toString().equals("")) ? etTotalFloors.getText().toString() : "0";

        realty = new Realty();
        realty.setAddress(btnAddress.getText().toString());
        realty.setAge("2020-07-23T02:34:56.8768464-07:00");
        realty.setArea(Double.parseDouble(totalArea));
        realty.setCost(Double.parseDouble(price));
        realty.setDescription(etDescription.getText().toString());
        realty.setHeader(etTitle.getText().toString());
        realty.setFloor(Integer.parseInt(floor));
        realty.setFloorBuild(Integer.parseInt(totalFloor));
        realty.setLatitude(43.2703876);
        realty.setLongitude(76.8845509);
        realty.setLivingSpace(Double.parseDouble(livingArea));
        realty.setTransactionType(1);
        realty.setRefCity(9);
        realty.setRoomCount(2);
        realty.setRealtyType(5);
        realty.setRentPeriod(9);

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(requireActivity(), SearchAddressActivity.class);
                startActivity(realtyIntent);
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
                realty.setStatus(0);
                realtyUpdate = new RealtyUpdate(requireActivity(), realty, token.getString("access_token", ""));
                realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int data, String message) {
                        if(data == 1)
                            Toast.makeText(requireContext(), "Realty is created", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnPublishRealty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realty.setStatus(1);
                realtyUpdate = new RealtyUpdate(requireContext(), realty, token.getString("access_token", ""));
                realtyUpdate.setOnLoadListener(new RealtyUpdate.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int data, String message) {
                        if(data == 1)
                            Toast.makeText(requireContext(), "Realty is published", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return fv;
    }
}