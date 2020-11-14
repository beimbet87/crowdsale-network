package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;

public class SplashActivity extends IntroActivity implements Constants {

//    SharedPreferences introPreferences, countryPreferences;
    Logger Log;
    String socketId;

    private final Handler waitHandler = new Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {
//            introPreferences = getSharedPreferences("M2_INTRO", 0);
//            boolean isIntro = introPreferences.getBoolean("isIntro", true);
//            if(isIntro) {
//                intent = new Intent(SplashActivity.this, IntroScreenActivity.class);
//                Log.d("M2DEBUG", "introScreen" + isIntro);
//            }
//            else {
//                intent = new Intent(SplashActivity.this, LoginActivity.class);
//                Log.d("M2DEBUG", "loginScreen");
//            }

            PusherOptions options = new PusherOptions();
            options.setCluster("ap2");
            options.buildUrl("8640a8f0befcab114411");
            Pusher pusher = new Pusher("0a0d35afd76319bf0b45", options);

            pusher.connect(new ConnectionEventListener() {
                @Override
                public void onConnectionStateChange(ConnectionStateChange change) {
                    Log.d("State changed to " + change.getCurrentState() +
                            " from " + change.getPreviousState());

                    socketId = pusher.getConnection().getSocketId();

                    SharedPreferences pusherPreferences = getSharedPreferences(SHARED_PUSHER, 0);
                    SharedPreferences.Editor editor = pusherPreferences.edit();
                    editor.putString("socket_id", socketId);
                    editor.apply();

                    Log.d("Socket ID: " + socketId);

                    Intent intent = new Intent(SplashActivity.this, IntroScreenActivity.class);
                    Log.d("Splash --> Intro: ");

                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String message, String code, Exception e) {
                    Log.d("Error message: " + message);
                    Log.d("Error code: " + code);
                }
            }, ConnectionState.ALL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log = new Logger(this, "M2TAG");

        waitHandler.postDelayed(waitCallback, 2000);

    }

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }
}
