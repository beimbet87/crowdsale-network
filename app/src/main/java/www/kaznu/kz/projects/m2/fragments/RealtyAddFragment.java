package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.Add3DActivity;
import www.kaznu.kz.projects.m2.api.realty.RealtyUpdate;
import www.kaznu.kz.projects.m2.models.Realty;

public class RealtyAddFragment extends Fragment {

    LinearLayout add3d;

    Button btnCreateRealty;
    Button btnPublishRealty;
    Realty realty;
    RealtyUpdate realtyUpdate;
    SharedPreferences token;

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

        realty = new Realty();
        realty.setAddress("мкр. Самал, 155");
        realty.setAge("2020-07-23T02:34:56.8768464-07:00");
        realty.setArea(64);
        realty.setCost(85000);
        realty.setDescription("3х комнатная квартира, лучшая и неугловая");
        realty.setHeader("2х комнатная квартира");
        realty.setFloor(3);
        realty.setFloorBuild(9);
        realty.setLatitude(43.2703876);
        realty.setLongitude(76.8845509);
        realty.setLivingSpace(60);
        realty.setTransactionType(1);
        realty.setRefCity(9);
        realty.setRoomCount(2);
        realty.setRealtyType(5);
        realty.setRentPeriod(9);

        add3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtyIntent = new Intent(requireActivity(), Add3DActivity.class);
                startActivity(realtyIntent);
                requireActivity().finish();
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