package www.kaznu.kz.projects.m2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.api.searches.MySearches;
import www.kaznu.kz.projects.m2.api.upload.DeleteImage;
import www.kaznu.kz.projects.m2.api.user.UserInfo;
import www.kaznu.kz.projects.m2.fragments.AccountAdminFragment;
import www.kaznu.kz.projects.m2.fragments.AccountFragment;
import www.kaznu.kz.projects.m2.fragments.BookingFragment;
import www.kaznu.kz.projects.m2.fragments.ListAdsAdminFragment;
import www.kaznu.kz.projects.m2.fragments.MessagesAdminFragment;
import www.kaznu.kz.projects.m2.fragments.MessagesFragment;
import www.kaznu.kz.projects.m2.fragments.ScheduleAdminFragment;
import www.kaznu.kz.projects.m2.fragments.SearchFlatsFragment;
import www.kaznu.kz.projects.m2.fragments.SearchFragment;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.models.User;
import www.kaznu.kz.projects.m2.utils.Logger;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AccountFragment.DataFromAccountFragment, AccountAdminFragment.DataFromAccountAdminFragment {

    public Location gpsNetworkLocation;
    private LocationManager locationManager;
    BottomNavigationView bottomNavigationView, bottomNavigationViewAdmin;
    SharedPreferences token;
    MySearches mySearches;

    boolean isEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger Log = new Logger(this, "M2TAG");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationViewAdmin = findViewById(R.id.bottom_navigation_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationViewAdmin.setOnNavigationItemSelectedListener(this);
        intDrawerLayout(bottomNavigationView);
        intDrawerLayoutAdmin(bottomNavigationViewAdmin);
        bottomNavigationViewAdmin.setVisibility(View.INVISIBLE);

        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.action_messages);
        badge.setVisible(true);
        badge.setNumber(99);
        badge.setBackgroundColor(getResources().getColor(R.color.color_primary_error));
        badge.setBadgeTextColor(getResources().getColor(android.R.color.white));

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        token = getSharedPreferences("M2_TOKEN", 0);

        mySearches = new MySearches(this, token.getString("access_token", ""));

        mySearches.setOnLoadListener(new MySearches.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Search> searches) {
                if (searches.size() > 0) {
                    loadFragment(new SearchFlatsFragment());
                } else {
                    loadFragment(new SearchFragment());
                }
            }
        });

        SharedPreferences token = getSharedPreferences("M2_TOKEN", 0);

        UserInfo userInfo = new UserInfo(this, token.getString("access_token", ""));

        DeleteImage deleteImage = new DeleteImage(this, "~/Images/192.jpg", token.getString("access_token", ""));

        deleteImage.setOnLoadListener(new DeleteImage.CustomOnLoadListener() {
            @Override
            public void onComplete(int data, String message) {
//                if (data == 1) {
//                    Toast.makeText(getApplicationContext(), "Image is deleted", Toast.LENGTH_LONG).show();
//                }
            }
        });

        userInfo.setOnLoadListener(new UserInfo.CustomOnLoadListener() {
            @Override
            public void onComplete(User data) {
                SharedPreferences userPreferences = getSharedPreferences("M2_USER_INFO", 0);
                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putInt("id", data.getId());
                editor.putInt("sex", data.getSex());
                editor.putString("name", data.getName());
                editor.putString("surname", data.getSurname());
                editor.putString("birth", data.getBirth());
                editor.putString("email", data.getEmail());
                editor.putString("phone", data.getPhone());
                editor.putString("image", data.getImageLink());
                editor.apply();
                Log.d("User info: " + data.getName() + " " + data.getSurname());
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_search:
                mySearches.setOnLoadListener(new MySearches.CustomOnLoadListener() {
                    @Override
                    public void onComplete(ArrayList<Search> searches) {
                        Log.d("M2TAG", "Search ID: " + searches.size());
                        if (searches.size() > 0) {
                            isEmpty = false;
                        } else {
                            isEmpty = true;
                        }
                    }
                });

                if (!isEmpty) {
                    fragment = new SearchFlatsFragment();
                } else {
                    fragment = new SearchFragment();
                }

                break;
            case R.id.action_messages:

//                Bundle bundle = new Bundle();
//                if(gpsNetworkLocation != null) {
//                    bundle.putDouble(Constants.LATITUDE, gpsNetworkLocation.getLatitude());
//                    bundle.putDouble(Constants.LONGITUDE, gpsNetworkLocation.getLongitude());
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Ошибка получения текущего положения",
//                            Toast.LENGTH_SHORT).show();
//                }
//                fragment = new MapsFragment();
//                fragment.setArguments(bundle);
                fragment = new MessagesFragment();
                break;
            case R.id.action_booking:
                fragment = new BookingFragment();
                break;
            case R.id.action_account:
                fragment = new AccountFragment();
                break;
            case R.id.action_ads_admin:
                fragment = new ListAdsAdminFragment();
                break;
            case R.id.action_messages_admin:
                fragment = new MessagesAdminFragment();
                break;
            case R.id.action_schedules_admin:
                fragment = new ScheduleAdminFragment();
                break;
            case R.id.action_account_admin:
                fragment = new AccountAdminFragment();
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

                Toast.makeText(getApplicationContext(), "Please, provide all necessary permissions!", Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();

    }

    @Override
    public void DataFromAccountFragment(boolean isMode) {
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
    public void DataFromAccountAdminFragment(boolean isMode) {
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
}
