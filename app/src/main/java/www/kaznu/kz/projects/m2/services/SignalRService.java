package www.kaznu.kz.projects.m2.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler2;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;
import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.LoginActivity;

public class SignalRService extends Service {
    private HubConnection mHubConnection;
    private HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients

    NotificationManager nm;

    public SignalRService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }

    @Override
    public void onDestroy() {
        mHubConnection.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
    }

    private void showNotifications(String message){

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notif","notify");
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext(), "www.kaznu.kz.projects.m2")
                .setSmallIcon(R.drawable.m2)
                .setContentTitle("Сообщение")
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);;

        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }

    /**
     * method for clients (activities)
     */
    public void sendMessage(String message) {
        String SERVER_METHOD_SEND = "sendDirectMessageStr";
        mHubProxy.invoke(SERVER_METHOD_SEND, message);
    }

    /**
     * method for clients (activities)
     */
    public void sendMessage_To(String message) {
        String SERVER_METHOD_SEND_TO = "Connect";
        mHubProxy.invoke(SERVER_METHOD_SEND_TO, message);

    }

    private void startSignalR() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());

        String serverUrl = "http://someproject-001-site1.itempurl.com/";
        mHubConnection = new HubConnection(serverUrl);
        String SERVER_HUB_CHAT = "PushHub";
        mHubProxy = mHubConnection.createHubProxy(SERVER_HUB_CHAT);

        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);

        try {
            signalRFuture.get();
            Log.d("M2TAG","connect");
        } catch (InterruptedException | ExecutionException e) {
            Log.d("M2TAG", e.toString());
            Log.d("M2TAG",serverUrl);
            return;
        }

        sendMessage_To("13");

        String CLIENT_METHOD_addMessage = "chatMessage";

        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("mine", false);
            jsonMessage.put("refSender", 13);
            jsonMessage.put("body", "Message from M2");
            jsonMessage.put("refReciever", 32);
            jsonMessage.put("type", 1);
            jsonMessage.put("refRealty", 1);
            jsonMessage.put("refRealty", 50000.0);
            jsonMessage.put("timeStart", "0001-01-01T00:00:00");
            jsonMessage.put("timeEnd", "0001-01-01T00:00:00");
//            sendMessage(jsonMessage.toString());
            Log.d("M2TAG", jsonMessage.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mHubProxy.on(CLIENT_METHOD_addMessage,
                new SubscriptionHandler1<String>() {
                    @Override
                    public void run(final String msg) {
                        final String finalMsg =  msg.toString();
                        int notificationId = 0;
                        // display Toast message
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject message = new JSONObject(finalMsg);
                                    String body = message.getString("body");
                                    Log.d("M2TAG", body + "");
                                    showNotifications(body);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("M2TAG", e.toString());
                                }
                            }
                        });
                    }
                }
                , String.class);

        // Subscribe to the received event
        /*mHubConnection.received(new MessageReceivedHandler() {
            @Override
            public void onMessageReceived(final JsonElement json) {
                Log.e("onMessageReceived ", json.toString());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/

    }

}
