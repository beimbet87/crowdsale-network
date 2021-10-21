package www.kaznu.kz.projects.m2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.Objects;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
//import www.kaznu.kz.projects.m2.services.ShowNotificationService;
import www.kaznu.kz.projects.m2.views.fragments.AccountAdminFragment;
import www.kaznu.kz.projects.m2.views.fragments.AccountFragment;
import www.kaznu.kz.projects.m2.views.fragments.BookingFragment;
import www.kaznu.kz.projects.m2.views.fragments.AdvertListFragment;
import www.kaznu.kz.projects.m2.views.fragments.MessageListFragment;
import www.kaznu.kz.projects.m2.views.fragments.MessageListFragmentAdmin;
import www.kaznu.kz.projects.m2.views.fragments.ScheduleAdminFragment;
import www.kaznu.kz.projects.m2.views.fragments.SearchFragment;
import www.kaznu.kz.projects.m2.views.fragments.SearchesAdminFragment;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        AccountFragment.DataFromAccountFragment,
        AccountAdminFragment.DataFromAccountAdminFragment, Constants {

    public Location gpsNetworkLocation;
    private LocationManager locationManager;
    BottomNavigationView bottomNavigationView, bottomNavigationViewAdmin;
    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        user = new CurrentUser(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationViewAdmin = findViewById(R.id.bottom_navigation_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationViewAdmin.setOnNavigationItemSelectedListener(this);
        intDrawerLayout(bottomNavigationView);
        intDrawerLayoutAdmin(bottomNavigationViewAdmin);
        bottomNavigationViewAdmin.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();

        if (user.isOwner()) {
            bottomNavigationViewAdmin.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);

            if(intent.getStringExtra("fragment") != null) {
                Log.d(Constants.TAG, Objects.requireNonNull(intent.getStringExtra("fragment")));
            }

            if (Objects.equals(intent.getStringExtra("fragment"), "message_admin_list")) {
                bottomNavigationViewAdmin.setSelectedItemId(R.id.action_messages_admin);
            } else {
                bottomNavigationViewAdmin.setSelectedItemId(R.id.action_account_admin);
            }
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationViewAdmin.setVisibility(View.INVISIBLE);

            if(Objects.equals(intent.getStringExtra("fragment"), "message_list")) {
                bottomNavigationView.setSelectedItemId(R.id.action_messages);
            } else {
                bottomNavigationView.setSelectedItemId(R.id.action_account);
            }
        }

//        showBadge(true, 2);

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_search:
                fragment = new SearchFragment();
                break;
            case R.id.action_messages:
                fragment = new MessageListFragment();
                break;
            case R.id.action_messages_admin:
                fragment = new MessageListFragmentAdmin();
                break;
            case R.id.action_booking:
                fragment = new BookingFragment();
                break;
            case R.id.action_account:
                fragment = new AccountFragment();
                break;
            case R.id.action_ads_admin:
                fragment = new AdvertListFragment();
                break;
            case R.id.action_schedules_admin:
                fragment = new ScheduleAdminFragment();
                break;
            case R.id.action_account_admin:
                fragment = new AccountAdminFragment();
                break;
            case R.id.action_search_admin:
                fragment = new SearchesAdminFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private void intDrawerLayout(BottomNavigationView bottomNavigationView) {

        IIcon[] iconsBottom = {
                CommunityMaterial.Icon2.cmd_map_marker,
                CommunityMaterial.Icon2.cmd_message_processing,
                CommunityMaterial.Icon2.cmd_view_agenda,
                CommunityMaterial.Icon.cmd_account
        };

        renderMenuIcons(bottomNavigationView.getMenu(), iconsBottom, Color.GRAY, 28);

    }

    private void intDrawerLayoutAdmin(BottomNavigationView bottomNavigationView) {

        IIcon[] iconsBottom = {
                CommunityMaterial.Icon.cmd_clipboard_text,
                CommunityMaterial.Icon2.cmd_message_processing,
                CommunityMaterial.Icon.cmd_calendar_check,
                CommunityMaterial.Icon.cmd_calendar_search,
                CommunityMaterial.Icon.cmd_account
        };

        renderMenuIcons(bottomNavigationView.getMenu(), iconsBottom, Color.WHITE, 28);

    }

    private void renderMenuIcons(Menu menu, IIcon[] icons, int color, int size) {

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (!menuItem.hasSubMenu()) {
                menuItem.setVisible(true);
                menuItem.setIcon(new IconicsDrawable(this)
                        .icon(icons[i])
                        .color(color)
                        .sizeDp(size));
            }
        }
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
            return true;
        }
        return false;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            gpsNetworkLocation = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.v("info", "GPS enabled: " + gps + " NetworkLoc enabled: " + network);
        }

        @Override
        public void onProviderDisabled(String s) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Please, provide all necessary permissions!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            gpsNetworkLocation = locationManager.getLastKnownLocation(s);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getApplicationContext(), "Пожалуйста, предоставьте все разрешения!", Toast.LENGTH_SHORT).show();
                //requestAllPermission();
                return;
            }
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000 * 10,
                10,
                locationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Dialog_Style_Alert))
                .setMessage("Вы действительно хотите выйти?")
                .setCancelable(false)

                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();

    }

    @Override
    public void FromAccountFragment(boolean isMode) {
        if (isMode) {
            bottomNavigationViewAdmin.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);
            bottomNavigationViewAdmin.setSelectedItemId(R.id.action_account_admin);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationViewAdmin.setVisibility(View.INVISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.action_account);
        }
    }

    @Override
    public void FromAccountAdminFragment(boolean isMode) {
        if (isMode) {
            bottomNavigationViewAdmin.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);
            bottomNavigationViewAdmin.setSelectedItemId(R.id.action_account_admin);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationViewAdmin.setVisibility(View.INVISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.action_account);
        }
    }

    private void showBadge(boolean isBadge, int number) {
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.action_messages);
        if (isBadge) {
            badge.setVisible(true);
            badge.setNumber(number);
            badge.setBackgroundColor(getColor(R.color.color_primary_error));
            badge.setBadgeTextColor(getColor(android.R.color.white));
        } else {
            badge.setVisible(false);
        }
    }
}