package www.kaznu.kz.projects.m2.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ViewAnimator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.LatLngAdapter;
import www.kaznu.kz.projects.m2.adapters.SearchAddressAdapter;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.GeocodingResult;
import www.kaznu.kz.projects.m2.utils.Logger;

public class SearchAddressActivity extends AppCompatActivity {

    Button btnClose;
    EditText etAddress;
    RecyclerView listView;
    private final Handler handler = new Handler();
    private final SearchAddressAdapter adapter = new SearchAddressAdapter();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LatLng.class, new LatLngAdapter())
            .create();

    private RequestQueue queue;
    private PlacesClient placesClient;
    private AutocompleteSessionToken sessionToken;

    private ViewAnimator viewAnimator;
    private ProgressBar progressBar;
    String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_address);

        apiKey = getString(R.string.google_maps_key);
        Places.initialize(this, apiKey);
        placesClient = Places.createClient(this);
        queue = Volley.newRequestQueue(this);

        btnClose = findViewById(R.id.btn_close);
        etAddress = findViewById(R.id.et_address);

        progressBar = findViewById(R.id.progress_bar);
        viewAnimator = findViewById(R.id.view_animator);

        listView = findViewById(R.id.search_results);

        sessionToken = AutocompleteSessionToken.newInstance();

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setIndeterminate(true);

                // Cancel any previous place prediction requests
                handler.removeCallbacksAndMessages(null);

                // Start a new place prediction request in 300 ms
                handler.postDelayed(() -> {
                    getPlacePredictions(s.toString());
                }, 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        adapter.setPlaceClickListener(place -> {

            final String url = "https://maps.googleapis.com/maps/api/geocode/json?place_id=%s&key=%s";
            final String requestURL = String.format(url, place.getPlaceId(), apiKey);

            Logger.d(requestURL);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                    response -> {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            if (results.length() == 0) {
                                Log.w(Constants.TAG, "No results from geocoding request.");
                                return;
                            }

                            GeocodingResult result = gson.fromJson(
                                    results.getString(0), GeocodingResult.class);
                            displayDialog(place, result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> Log.e(Constants.TAG, "Request failed"));

            queue.add(request);

            Intent intent = new Intent();

            intent.putExtra("address", place.getFullText(null).toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        btnClose.setOnClickListener(v -> finish());

    }

    private void getPlacePredictions(String query) {

//        final LocationBias bias = RectangularBounds.newInstance(
//                new LatLng(22.458744, 88.208162), // SW lat, lng
//                new LatLng(22.730671, 88.524896) // NE lat, lng
//        );

        final FindAutocompletePredictionsRequest newRequest = FindAutocompletePredictionsRequest
                .builder()
                .setSessionToken(sessionToken)
//                .setLocationBias(bias)
                .setTypeFilter(TypeFilter.GEOCODE)
                .setTypeFilter(TypeFilter.ADDRESS)
                .setQuery(query)
//                .setCountries("IN")
                .build();

        placesClient.findAutocompletePredictions(newRequest).addOnSuccessListener((response) -> {
            List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
            adapter.setPredictions(predictions);

            progressBar.setIndeterminate(false);
            viewAnimator.setDisplayedChild(predictions.isEmpty() ? 0 : 1);
        }).addOnFailureListener((exception) -> {
            progressBar.setIndeterminate(false);
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.d(Constants.TAG, "Place not found: " + apiException.getStatusCode());
            }
        });
    }

    private void displayDialog(AutocompletePrediction place, GeocodingResult result) {
        new AlertDialog.Builder(this)
                .setTitle(place.getPrimaryText(null))
                .setMessage("Geocoding result:\n" + result.geometry.location)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
