package www.kaznu.kz.projects.m2.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.Countries;
import www.kaznu.kz.projects.m2.api.Currencies;
import www.kaznu.kz.projects.m2.api.DealType;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.api.RealtyType;
import www.kaznu.kz.projects.m2.api.RentPeriod;
import www.kaznu.kz.projects.m2.api.RequestOffers;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class SplashActivity extends IntroActivity implements Constants {

    String socketId;
    ProgressBar progressBar;
    Intent intent;
    TinyDB tinyDB;

    Context context;

    private final Handler waitHandler = new Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {

            new RealtyType(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_REALTY_TYPE, data));
            Logger.d("TinyDB ---> Stored realty type data!");

            new RequestOffers(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_REQUEST_OFFERS, data));
            Logger.d("TinyDB ---> Stored request offers data!");

            new RealtyProperties(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_REALTY_PROPERTIES, data));
            Logger.d("TinyDB ---> Stored realty properties data!");

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
}