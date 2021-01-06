package www.kaznu.kz.projects.m2.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import www.kaznu.kz.projects.m2.R;
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
        startNotification();
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
