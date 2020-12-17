package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.ImageAdapter;
import www.kaznu.kz.projects.m2.adapters.RatingAdapter;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.api.rate.RealtyRate;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.RateModel;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.services.GPSTracker;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.FlowLayout;

import static www.kaznu.kz.projects.m2.interfaces.Constants.BASE_URL;

public class RealtyActivity extends IntroActivity {
    LinearLayout linearLayout;
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
    RatingBar ratingBar;
    LinearLayout btnComment;
    Button btnWrite, btnRent;

    TextView tvStars, tvComments;

    double price, area, livingSpace;
    int floor, floorBuild;

    FlowLayout flowLayout;

    Logger Log;

    ArrayList<Integer> properties = new ArrayList<>();

    Button btnBack;

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
        ratingBar = findViewById(R.id.rb_profile_rating);

        tvStars = findViewById(R.id.stars);
        tvComments = findViewById(R.id.comments);

        btnWrite = findViewById(R.id.btn_write_message);
        btnRent = findViewById(R.id.btn_rent_realty);
        btnComment = findViewById(R.id.btn_comments);

        flowLayout = findViewById(R.id.comforts);

        btnBack = findViewById(R.id.toolbar_back);

        Log = new Logger(this, Constants.TAG);

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        title = intent.getStringExtra("title");
        address = intent.getStringExtra("address");
        price = intent.getDoubleExtra("price", 0.0);
        owner = intent.getStringExtra("owner");

        avatar = intent.getStringExtra("avatar");

        if(intent.getIntegerArrayListExtra("properties") != null) {
            properties.addAll(Objects.requireNonNull(intent.getIntegerArrayListExtra("properties")));
        }

        ratingBar.setRating((float)intent.getIntExtra("stars", 0));

        Log.d("Stars: " + String.valueOf(intent.getIntExtra("stars", 0)));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if(properties.size() > 0) {
            for (int i = 0; i < properties.size(); i++) {
                RealtyProperties realtyProperties = new RealtyProperties(this);
                final int temp = properties.get(i);
                realtyProperties.setOnLoadListener(new RealtyProperties.CustomOnLoadListener() {
                    @Override
                    public void onComplete(ArrayList<Directory> data) {
                        for(int idx = 0; idx < data.size(); idx++) {
                            if(data.get(idx).getCodeId() == temp) {
                                String upperString = data.get(idx).getValue().substring(0, 1).toUpperCase() + data.get(idx).getValue().substring(1).toLowerCase();
                                addText(upperString);
                            }
                        }
                    }
                });
            }
        }
        else {
            addText("Нет");
        }


        assert avatar != null;
        if(!avatar.equals("null")) {
            String url = BASE_URL.concat(avatar);
            Glide.with(this).load(url).into(ivAvatar);
        }
        else {
            Log.d("Avatar: " + intent.getStringExtra("avatar"));
        }
        body = intent.getStringExtra("body");
        floor = intent.getIntExtra("floor", 1);
        floorBuild = intent.getIntExtra("floorbuild", 5);
        area = intent.getDoubleExtra("area", 0.0);
        livingSpace = intent.getDoubleExtra("livingspace", 0.0);

        Log.d(images + "");

        tvRealtyTitle.setText(title);
        tvAddress.setText(address);
        tvPrice.setText(Utils.parsePrice(price));
        tvOwner.setText(owner);
        tvBody.setText(body);

        tvLivingSpace.setText(new StringBuilder().append(Math.round(livingSpace)).append(" "));
        tvLivingSpace.append(Html.fromHtml(getString(R.string.m2)));
        tvArea.setText(new StringBuilder().append(Math.round(area)).append(" "));
        tvArea.append(Html.fromHtml(getString(R.string.m2)));
        StringBuilder strFloor = new StringBuilder("");
        strFloor.append(floor).append(" и ").append(floorBuild);
        tvFloor.setText(strFloor.toString());

        gps = new GPSTracker(RealtyActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RealtyActivity.this, DiscussionActivity.class);
                i.putExtra("contact", intent.getIntExtra("contact", 1));
                i.putExtra("ref_realty", intent.getIntExtra("ref_realty", 45));

                Logger Log = new Logger(getApplicationContext(), Constants.TAG);
                Log.d(intent.getIntExtra("contact", 1) + "" + intent.getIntExtra("ref_realty", 45));

                startActivity(i);
            }
        });

        RealtyRate realtyRate = new RealtyRate(this, intent.getIntExtra("ref_realty", 45), new Tokens(this).getAccessToken());

        realtyRate.setOnLoadListener(new RealtyRate.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<RateModel> rates, int count, double average) {
                tvStars.setText(String.valueOf(average));
                tvComments.setText(String.valueOf(count).concat(" человек(а) оставил(и) отзыв"));
            }
        });

        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RealtyActivity.this, DiscussionActivity.class);
                i.putExtra("contact", intent.getIntExtra("contact", 1));
                i.putExtra("ref_realty", intent.getIntExtra("ref_realty", 45));
                startActivity(i);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RealtyActivity.this, CommentsRealtyActivity.class);
                i.putExtra("contact", intent.getIntExtra("contact", 1));
                i.putExtra("ref_realty", intent.getIntExtra("ref_realty", 45));
                startActivity(i);
            }
        });

        viewPager = findViewById(R.id.vp_imageview);
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.height = (int) getResources().getDimension(R.dimen.image_view_heigh);
        viewPager.setLayoutParams(layoutParams);
        ImageAdapter imageAdapter = new ImageAdapter(this, images);

        viewPager.setAdapter(imageAdapter);

        addDots();



        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
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

    public void addText(String data) {
        int padding1 = getResources().getDimensionPixelSize(R.dimen.padding_comfort_tb);
        int padding2 = getResources().getDimensionPixelSize(R.dimen.padding_comfort_lr);
        TextView textView = new TextView(this);
        textView.setTextSize(14);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.view_profile_button_background));
        textView.setTextColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        textView.setMaxLines(1);
        textView.setPadding(padding1, padding2, padding1, padding2);
        textView.setText(data);
        flowLayout.addView(textView);
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