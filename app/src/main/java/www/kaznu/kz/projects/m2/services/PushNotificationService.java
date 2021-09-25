package www.kaznu.kz.projects.m2.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.views.activities.DiscussionActivity;

public class PushNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        int contact = 0;
        int refRealty = 0;
        boolean isOwner;
        JSONObject data = new JSONObject(remoteMessage.getData());
        try {
            contact = data.getInt("contact");
            refRealty = data.getInt("refRealty");
            isOwner = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String CHANNEL_ID = "NOTIFICATION";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Intent notifyIntent = new Intent(this, DiscussionActivity.class);
            notifyIntent.putExtra("contact", contact);
            notifyIntent.putExtra("ref_realty", refRealty);
            notifyIntent.putExtra("owner", false);
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                    this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            );

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.m2)
                    .setAutoCancel(true)
                    .setContentIntent(notifyPendingIntent);
            NotificationManagerCompat.from(this).notify(1, notification.build());


            Log.d(Constants.TAG, "Data: " + remoteMessage.getData().toString());
        }

        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
