package www.kaznu.kz.projects.m2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.List;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.Polygons;

public class MapsFragment extends Fragment implements InputListener {

    private MapView mapview;
    private Handler animationHandler;

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    private List<Polygons> polygonsList;
    Polygon polygon;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_maps, container, false);

        RequestQueue queue = Volley.newRequestQueue(rootView.getContext());

        polygon = new Polygon();

        mapview = rootView.findViewById(R.id.map_view);

        mapview.getMap().set2DMode(true);

        animationHandler = new Handler();

        if (getArguments() != null) {
            mapview.getMap().move(
                    new CameraPosition(
                            new Point(
                                    getArguments().getDouble(LATITUDE),
                                    getArguments().getDouble(LONGITUDE)
                            ), 17.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null
            );
        }

        return rootView;
    }

    public double round(double data) {
        double roundOff = Math.round(data * 1000.0) / 1000.0;
        return roundOff;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onMapTap(@NonNull Map map, @NonNull Point point) {
        Polygons polygons = new Polygons();
        polygons.setLatitude(point.getLatitude());
        polygons.setLongitude(point.getLongitude());
        polygonsList.add(polygons);
    }

    @Override
    public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

    }
}
