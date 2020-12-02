package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.ImageAdapter;
import www.kaznu.kz.projects.m2.services.GPSTracker;

import static www.kaznu.kz.projects.m2.interfaces.Constants.BASE_URL;

public class RealtyActivity extends IntroActivity {
    LinearLayout linearLayout;
    private MapView mapview;
    private Handler animationHandler;
    double longitude;
    double latitude;
    GPSTracker gps;
    ArrayList<ImageView> dots;
    ViewPager viewPager;
    ArrayList<String> images;
    TextView tvRealtyTitle, tvAddress, tvPrice, tvOwner, tvBody, tvLivingSpace, tvArea;
    TextView tvFloor;
    CircleImageView ivAvatar;
    String title, address, owner, avatar, body;
    double price, area, livingSpace;
    int floor, floorBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realty);
        linearLayout = findViewById(R.id.realty_info);
        tvRealtyTitle = findViewById(R.id.realty_title);
        tvAddress = findViewById(R.id.tv_message_title);
        tvPrice = findViewById(R.id.tv_price);
        tvOwner = findViewById(R.id.tv_name);
        tvBody = findViewById(R.id.tv_body);
        tvLivingSpace = findViewById(R.id.tv_houseroom);
        tvArea = findViewById(R.id.tv_totalarea);
        tvFloor = findViewById(R.id.tv_floor);
        ivAvatar = findViewById(R.id.iv_profile_image);

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        title = intent.getStringExtra("title");
        address = intent.getStringExtra("address");
        price = intent.getDoubleExtra("price", 0.0);
        owner = intent.getStringExtra("owner");

        avatar = intent.getStringExtra("avatar");

        assert avatar != null;
        if(!avatar.equals("null")) {
            String url = BASE_URL.concat(avatar);
            Glide.with(this).load(url).into(ivAvatar);
        }
        else {
            Log.d("M2TAG", "Avatar: " + intent.getStringExtra("avatar"));
        }
        body = intent.getStringExtra("body");
        floor = intent.getIntExtra("floor", 1);
        floorBuild = intent.getIntExtra("floorbuild", 5);
        area = intent.getDoubleExtra("area", 0.0);
        livingSpace = intent.getDoubleExtra("livingspace", 0.0);

        Log.d("M2TAG", images + "");

        tvRealtyTitle.setText(title);
        tvAddress.setText(address);
        tvPrice.setText(String.valueOf(price).concat(" ₸"));
        tvOwner.setText(owner);
        tvBody.setText(body);

        tvLivingSpace.setText(String.valueOf(livingSpace));
        tvArea.setText(String.valueOf(area));
        StringBuilder strFloor = new StringBuilder("");
        strFloor.append(floor).append(" и ").append(floorBuild);
        tvFloor.setText(strFloor.toString());
        MapKitFactory.setApiKey("0c19b044-7b1e-4190-8682-4118f672110d");

        MapKitFactory.initialize(this);

        mapview = findViewById(R.id.tv_default_map);

        mapview.getMap().set2DMode(true);

        animationHandler = new Handler();

        gps = new GPSTracker(RealtyActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }

        mapview.getMap().move(
                new CameraPosition(
                        new Point(
                                latitude,
                                longitude
                        ), 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null
        );

        viewPager = findViewById(R.id.vp_imageview);
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.height = (int) getResources().getDimension(R.dimen.image_view_heigh);
        viewPager.setLayoutParams(layoutParams);
        ImageAdapter imageAdapter = new ImageAdapter(this, images);

        viewPager.setAdapter(imageAdapter);

        addDots();



        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setPeekHeight(1590);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

    }

    public void addDots() {
        dots = new ArrayList<>();
        LinearLayout dotsLayout = (LinearLayout)findViewById(R.id.dots);

        for(int i = 0; i < images.size(); i++) {
            ImageView dot = new ImageView(this);
            if(i == 0) {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.ic_pager_indicator));
            } else {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.ic_pager_indicator_alpha));
            }
            dot.setPadding(8, 0, 8, 0);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            dotsLayout.addView(dot, params);

            dots.add(dot);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for(int i = 0; i < images.size(); i++) {
            int drawableId = (i==idx)?(R.drawable.ic_pager_indicator):(R.drawable.ic_pager_indicator_alpha);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RealtyActivity.this, OfferActivity.class);
        startActivity(intent);
    }
}