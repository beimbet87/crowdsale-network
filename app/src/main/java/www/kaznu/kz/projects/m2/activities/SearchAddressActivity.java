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
import androidx.appcompat.app.AppCompatActivity;
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

public class SearchAddressActivity extends AppCompatActivity {

    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_address);

        btnClose = findViewById(R.id.btn_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
