package www.kaznu.kz.projects.m2.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.Countries;
import www.kaznu.kz.projects.m2.api.Currencies;
import www.kaznu.kz.projects.m2.api.DealType;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.api.RealtyType;
import www.kaznu.kz.projects.m2.api.RentPeriod;
import www.kaznu.kz.projects.m2.api.RequestOffers;
import www.kaznu.kz.projects.m2.api.RequestOffersSections;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class SplashActivity extends IntroActivity implements Constants {

    String socketId;
    ProgressBar progressBar;
    Intent intent;
    TinyDB tinyDB;

    public static ArrayList<ArrayList<ArrayList<Directory>>> realtyType= new ArrayList<>();
    public static ArrayList<ArrayList<Directory>> sections = new ArrayList<>();
    public static ArrayList<Directory> properties = new ArrayList<>();

    Context context;

    private final Handler waitHandler = new Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {

            new RealtyType(context).setOnLoadListener(realtyTypeData -> {
                tinyDB.putListDirectory(SHARED_REALTY_TYPE, realtyTypeData);
                Logger.d("TinyDB ---> Stored realty type data! " + realtyTypeData.size());
                for (int i = 0; i < realtyTypeData.size(); i++) {
                    new RequestOffersSections(context, String.valueOf(realtyTypeData.get(i).getCodeId())).setOnLoadListener(new RequestOffersSections.CustomOnLoadListener() {
                        @Override
                        public void onComplete(ArrayList<Directory> sectionData) {
                            for(int i = 0; i < sectionData.size(); i++) {
                                new RealtyProperties(context, String.valueOf(sectionData.get(i).getCodeId())).setOnLoadListener(data -> {
                                    tinyDB.putListDirectory(SHARED_REALTY_PROPERTIES, data);
                                    Logger.d("TinyDB ---> Stored realty properties data! ");
                                    properties.addAll(data);
                                });

                                sections.add(i, properties);

//                                Logger.d("RT: " + properties.get(0).getValue());
                            }
                        }
                    });
                    realtyType.add(sections);


                }
            });

            new RequestOffers(context).setOnLoadListener(data -> {
                tinyDB.putListDirectory(SHARED_REQUEST_OFFERS, data);
                Logger.d("TinyDB ---> Stored request offers data! " + data.toArray().toString());
            });



            new DealType(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_DEAL_TYPE, data));
            Logger.d("TinyDB ---> Stored deal type data!");

            new RentPeriod(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_RENT_PERIOD, data));
            Logger.d("TinyDB ---> Stored rent period data!");

            new Countries(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_COUNTRIES, data));
            Logger.d("TinyDB ---> Stored countries data!");

            new Currencies(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_CURRENCIES, data));
            Logger.d("TinyDB ---> Stored currency data!");

            boolean isIntro = tinyDB.getBoolean(SHARED_IS_INTRO);

            if (isIntro) {
                intent = new Intent(SplashActivity.this, IntroScreenActivity.class);
                Logger.d("Intent ---> IntroScreenActivity");
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                Logger.d("Intent ---> LoginActivity");
            }

            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        hideSystemBars();

        context = this;

        tinyDB = new TinyDB(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        waitHandler.postDelayed(waitCallback, 4000);

    }

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
    }
}