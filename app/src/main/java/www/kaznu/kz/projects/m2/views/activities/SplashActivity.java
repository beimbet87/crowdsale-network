package www.kaznu.kz.projects.m2.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

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

    SharedPreferences introPreferences;

    Logger Log;
    String socketId;
    ProgressBar progressBar;
    Intent intent;
    TinyDB tinyDB;

    Context context;

    private final Handler waitHandler = new Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {
            introPreferences = getSharedPreferences("M2_INTRO", 0);

            new RealtyType(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_REALTY_TYPE, data));
            Log.d("TinyDB ---> Stored realty type data!");

            new RequestOffers(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_REQUEST_OFFERS, data));
            Log.d("TinyDB ---> Stored request offers data!");

            new RealtyProperties(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_REALTY_PROPERTIES, data));
            Log.d("TinyDB ---> Stored realty properties data!");

            new DealType(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_DEAL_TYPE, data));
            Log.d("TinyDB ---> Stored deal type data!");

            new RentPeriod(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_RENT_PERIOD, data));
            Log.d("TinyDB ---> Stored rent period data!");

            new Countries(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_COUNTRIES, data));
            Log.d("TinyDB ---> Stored countries data!");

            new Currencies(context).setOnLoadListener(data ->
                    tinyDB.putListDirectory(SHARED_CURRENCIES, data));
            Log.d("TinyDB ---> Stored currency data!");

            boolean isIntro = introPreferences.getBoolean("isIntro", true);
            if (isIntro) {
                intent = new Intent(SplashActivity.this, IntroScreenActivity.class);
                Log.d("Intent ---> IntroScreenActivity");
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                Log.d("Intent ---> LoginActivity");
            }

            PusherOptions options = new PusherOptions();
            options.setCluster("ap2");
            options.buildUrl("8640a8f0befcab114411");
            Pusher pusher = new Pusher("0a0d35afd76319bf0b45", options);

            pusher.connect(new ConnectionEventListener() {
                @Override
                public void onConnectionStateChange(ConnectionStateChange change) {
                    Log.d("Pusher ---> State changed to " + change.getCurrentState() +
                            " from " + change.getPreviousState());

                    socketId = pusher.getConnection().getSocketId();

                    SharedPreferences pusherPreferences = getSharedPreferences(SHARED_PUSHER, 0);
                    SharedPreferences.Editor editor = pusherPreferences.edit();
                    editor.putString("socket_id", socketId);
                    editor.apply();

                    Log.d("Pusher ---> Socket ID: " + socketId);

                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String message, String code, Exception e) {
                    Log.d("Pusher ---> Error message: " + message);
                    Log.d("Pusher ---> Error code: " + code);
                }
            }, ConnectionState.ALL);
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

        Log = new Logger(this, "M2TAG");

        waitHandler.postDelayed(waitCallback, 4000);

    }

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }
}
