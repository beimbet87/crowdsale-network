package www.kaznu.kz.projects.m2.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;

import androidx.core.app.NotificationCompat;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.pusher.PusherChannel;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.AuthData;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.views.activities.LoginActivity;

public class ShowNotificationService extends Service {
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients

    NotificationManager nm;

    public ShowNotificationService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        SharedPreferences socketIdPreferences = getSharedPreferences(Constants.SHARED_PUSHER, 0);
        String params = "?socket_id=" + socketIdPreferences.getString("socket_id", "");
        final HttpAuthorizer authorizer = new HttpAuthorizer(
                Constants.URL_PUSHER_AUTH);

        Logger.d("Auth URL --> " + Constants.URL_PUSHER_AUTH);

        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        options.setAuthorizer(authorizer);
        options.buildUrl(getString(R.string.pusher_app_key));
        Pusher pusher = new Pusher(getString(R.string.pusher_app_id), options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                if(change.getCurrentState().equals(ConnectionState.CONNECTED)) {
                    new PusherChannel(getApplicationContext(), pusher.getConnection().getSocketId(), new Tokens(getApplicationContext()).getAccessToken()).setOnLoadListener(new PusherChannel.CustomOnLoadListener() {
                        @Override
                        public void onComplete(int data, String message, String channelName, AuthData auth) {
                            Logger.d(channelName + " and " + auth.getAuth());

                            PrivateChannel channel = pusher.subscribePrivate(channelName);

                            Logger.d("Channel name ---> " + channelName + " and " + socketIdPreferences.getString("socket_id", ""));

                            channel.bind("new_message", new PrivateChannelEventListener() {
                                @Override
                                public void onAuthenticationFailure(String message, Exception e) {
                                    Logger.d("Authentication Failure ---> " + message);
                                }

                                @Override
                                public void onSubscriptionSucceeded(String channelName) {
                                    Logger.d("Channel name ---> " + channelName);
                                }

                                @Override
                                public void onEvent(PusherEvent event) {
                                    showNotifications(event.getData());
                                }
                            });

                            if (channel.isSubscribed()) {
                                Logger.d("Subscribed");
                            } else {
                                Logger.d("Unsubscribed");
                            }

                        }
                    });
                } else {
                    Logger.d("Connecting");
                }
            }

            @Override
            public void onError(String message, String code, Exception e) {
                Logger.d("Pusher ---> Error message: " + message);
                Logger.d("Pusher ---> Error code: " + code);
            }
        }, ConnectionState.ALL);


        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startNotification();
        return mBinder;
    }

    private void showNotifications(String message) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notif", "notify");
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "www.kaznu.kz.projects.m2")
                .setSmallIcon(R.drawable.m2)
                .setContentTitle("Сообщение")
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        ;

        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("www.kaznu.kz.projects.m2");

            NotificationChannel channel = new NotificationChannel(
                    "www.kaznu.kz.projects.m2",
                    "Приложение " + getText(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
            );

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        notificationManager.notify(0, builder.build());

    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public ShowNotificationService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return ShowNotificationService.this;
        }
    }

    public void sendMessage(String message) {
        String SERVER_METHOD_SEND = "sendDirectMessageStr";
    }

    public void sendMessage_To(String message) {

    }

    private void startNotification() {


    }

}
