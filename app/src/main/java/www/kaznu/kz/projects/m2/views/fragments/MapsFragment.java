package www.kaznu.kz.projects.m2.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.Polygons;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener {

    private GoogleMap map;
    public LocationManager locationManager;
    double longitude = 0, latitude = 0;
    boolean isEdit = false, markerClicked = false;
    Location location;

    ArrayList<Polygons> searchArea = new ArrayList<>();
    PolygonOptions rectOptions = new PolygonOptions();
    Polygon polygon;

    Polygons polygons;

    public MapsFragment() {
        // Required empty public constructor
    }

    public interface DataFromSearchArea {
        void SearchArea(ArrayList<Polygons> area);
    }

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_maps, container, false);

        polygons = new Polygons();

        SupportMapFragment mapFragment = null;

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        if(getArguments() != null)
        isEdit = getArguments().getBoolean("isEdit");

        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

        if (isEdit) {
            MarkerOptions markerOptions = new MarkerOptions();
            map.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                    .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.round_button_background_blue))
                    .draggable(false).anchor(0.5f,0.5f));

            if(markerClicked) {
                if (polygon != null) {
                    polygon.remove();
                    polygon = null;
                }
                polygons.setLatitude(latLng.latitude);
                polygons.setLongitude(latLng.longitude);
                searchArea.add(polygons);
                rectOptions.add(new LatLng(latLng.latitude, latLng.longitude));
                rectOptions.strokeColor(getResources().getColor(R.color.color_primary));
                rectOptions.fillColor(getResources().getColor(R.color.color_primary_blue_transparent));
                rectOptions.strokeWidth(6);
                rectOptions.strokeJointType(JointType.ROUND);

                polygon = map.addPolygon(rectOptions);

                dataPasser.SearchArea(searchArea);
            }
            else {
                if(polygon != null){
                    polygon.remove();
                    polygon = null;
                }

                rectOptions = new PolygonOptions().add(new LatLng(latLng.latitude, latLng.longitude));
                markerClicked = true;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapClickListener(this);

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng currentLocation = new LatLng(latitude, longitude);
        com.google.android.gms.maps.model.CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation)
                .zoom(16)
                .build();

        map.addMarker(new MarkerOptions().position(currentLocation)
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_current))
                .draggable(false)
                .title("Текущее положение"));
        map.setMinZoomPreference(6.0f);
        map.setMaxZoomPreference(20.0f);
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    DataFromSearchArea dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromSearchArea) context;
    }
}
