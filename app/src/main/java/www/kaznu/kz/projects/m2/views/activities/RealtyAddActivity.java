package www.kaznu.kz.projects.m2.views.activities;

import android.Manifest;
import android.annotation.SuppressLint;

import www.kaznu.kz.projects.m2.api.realty.RealtyPublish;
import www.kaznu.kz.projects.m2.api.realty.RealtyReserve;
import www.kaznu.kz.projects.m2.api.realty.UpdateRealtyProperties;
import www.kaznu.kz.projects.m2.models.ConfigValue;
import www.kaznu.kz.projects.m2.utils.VolleyMultipartRequest.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
import www.kaznu.kz.projects.m2.utils.VolleyMultipartRequest;
import www.kaznu.kz.projects.m2.views.customviews.FlowLayout;

public class RealtyAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;

    Button btnBack;
    public TextView title;

    LinearLayout add3d;

    ArrayList<Bitmap> bitmaps;

    Button btnCreateRealty;
    Button btnPublishRealty;
    Realty realty;
    RealtyUpdate realtyUpdate;
    RealtyPublish realtyPublish;
    SharedPreferences token;
    Properties properties;
    CheckBox isAgree;
    ImageView uploadImages;
    int realtyId = -1;

    LinearLayout imageContainer;

    private final ArrayList<Integer> selectedProperties = new ArrayList<>();
    private final ArrayList<Integer> selectedOffer = new ArrayList<>();

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

    EditText etTotalArea, etLivingArea, etTotalFloors, etFloor;
    String totalArea = "0.0", livingArea = "0.0", price = "0.0", floor = "0", totalFloor = "0";

    CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realty_add);
        user = new CurrentUser(this);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.toolbar_title);

        btnBack = toolbar.findViewById(R.id.toolbar_back);

        btnBack.setOnClickListener(v -> finish());

        bitmaps = new ArrayList<>();

        add3d = findViewById(R.id.add_3d);

        btnCreateRealty = findViewById(R.id.btn_create_advert);
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
        uploadImages = findViewById(R.id.upload_images);

        btnAddress = findViewById(R.id.btn_add_address);

        imageContainer = findViewById(R.id.images_container);

        scBargain = findViewById(R.id.sw_is_bargain);
        isAgree = findViewById(R.id.cb_privacy);

        isAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnPublishRealty.setEnabled(true);
            } else {
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

        uploadImages.setOnClickListener(v -> showFileChooser());

        btnCreateRealty.setOnClickListener(v -> {

            RealtyReserve realtyReserve = new RealtyReserve(getApplicationContext(), new Tokens(getApplicationContext()).getAccessToken());

            realtyReserve.setOnLoadListener(id -> {

                Logger.d("Realty reserved with id: " + id);

                realtyId = id;
                totalArea = (!etTotalArea.getText().toString().equals("")) ? etTotalArea.getText().toString() : "0.0";
                price = (!etPrice.getText().toString().equals("")) ? etPrice.getText().toString() : "0.0";
                livingArea = (!etLivingArea.getText().toString().equals("")) ? etLivingArea.getText().toString() : "0.0";
                floor = (!etFloor.getText().toString().equals("")) ? etFloor.getText().toString() : "0";
                totalFloor = (!etTotalFloors.getText().toString().equals("")) ? etTotalFloors.getText().toString() : "0";

                realty = new Realty();
                realty.setId(id);
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

                realtyUpdate = new RealtyUpdate(getApplicationContext(), realty, id, new Tokens(getApplicationContext()).getAccessToken());
                realtyUpdate.setOnLoadListener((data, resultCode, message) -> {

                    Logger.d("Realty created");

                    if (resultCode == 1) {

                        if (bitmaps.size() > 0) {
                            Map<String, DataPart> params = new HashMap<>();

                            for (int i = 0; i < bitmaps.size(); i++) {
                                long imageName = System.currentTimeMillis();
                                params.put(String.valueOf(i + 1), new DataPart(imageName + ".png", getFileDataFromDrawable(bitmaps.get(i))));
                            }

                            uploadBitmap(params, data);
                        }

                        ArrayList<ConfigValue> propertyValues = new ArrayList<>();

                        for (int i = 0; i < selectedProperties.size(); i++) {
                            ConfigValue propertyValue = new ConfigValue();
                            propertyValue.setRefRealty(data);
                            propertyValue.setType(selectedProperties.get(i));
                            propertyValue.setSet(true);

                            propertyValues.add(propertyValue);
                        }

                        if (scBargain.isChecked()) {
                            ArrayList<ConfigValue> offerValues = new ArrayList<>();

                            for (int i = 0; i < selectedOffer.size(); i++) {
                                ConfigValue offerValue = new ConfigValue();
                                offerValue.setRefRealty(data);
                                offerValue.setType(selectedOffer.get(i));
                                offerValue.setSet(true);

                                offerValues.add(offerValue);
                            }
                        }

                        UpdateRealtyProperties realtyProperties = new UpdateRealtyProperties(getApplicationContext(), propertyValues, new Tokens(getApplicationContext()).getAccessToken());
                        realtyProperties.setOnLoadListener(new UpdateRealtyProperties.CustomOnLoadListener() {
                            @Override
                            public void onComplete(int resultCode, String resultMessage) {
                                Toast.makeText(getApplicationContext(), "Объявление создано", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Объявление не создано", Toast.LENGTH_LONG).show();
                    }
                });
            });
        });

        btnPublishRealty.setOnClickListener(v -> {

            if (realtyId != -1) {
                realtyPublish = new RealtyPublish(getApplicationContext(), realtyId, 1, new Tokens(getApplicationContext()).getAccessToken());
                realtyPublish.setOnLoadListener(new RealtyPublish.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int resultcode, String message) {
                        if (resultcode == 1)
                            Toast.makeText(getApplicationContext(), "Объявление опубликовано", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Сначал сохраните объявление", Toast.LENGTH_LONG).show();
            }
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            String filePath = getPath(picUri);
            if (filePath != null) {
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
//                    uploadBitmap(bitmap);

                    int size = getResources().getDimensionPixelSize(R.dimen.image_size);
                    int margin = getResources().getDimensionPixelSize(R.dimen.image_margin);
                    CardView cardView = new CardView(getApplicationContext());
                    CardView.LayoutParams params = new CardView.LayoutParams(size, size);
                    params.setMarginEnd(margin);
                    cardView.setLayoutParams(params);
                    cardView.setRadius(16);
                    cardView.setCardElevation(0);
                    cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.intro_input_phone_background));
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(size, size));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageBitmap(bitmap);
                    cardView.addView(imageView);
                    imageContainer.addView(cardView);

                    bitmaps.add(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                Snackbar.make(getApplicationContext(), "no image selected",
//                        Snackbar.LENGTH_LONG).show();
            }
        } else if (data != null) {
            btnAddress.setText(data.getStringExtra("address"));
            Logger.d(data.getStringExtra("address"));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_realty_type:
                Logger.d("Realty type: " + properties.getRealtyType().get(position).getCodeId());
                break;
            case R.id.sp_rent_type:
                Logger.d("Rent type: " + properties.getDealType().get(position).getCodeId());
                break;
            case R.id.sp_rent_period:
                Logger.d("Rent period: " + properties.getRentPeriod().get(position).getCodeId());
                break;
            case R.id.sp_rooms:
                Logger.d("Rooms: " + properties.getRooms().get(position).getCodeId());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showFileChooser() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if (!(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && !(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Выбрать аватар"), PICK_IMAGE_REQUEST);
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        assert cursor != null;
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(Map<String, DataPart> dataPart, int id) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.URL_POST_IMAGE,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(new String(response.data));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), Objects.requireNonNull(error.getMessage()), Toast.LENGTH_LONG).show()) {


            @Override
            protected Map<String, DataPart> getByteData() {
                return dataPart;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("alt", "Test description");
                params.put("typeDocument", "1");
                params.put("refSource", String.valueOf(id));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}