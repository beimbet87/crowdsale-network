package www.kaznu.kz.projects.m2.services;

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

import java.util.Objects;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.activities.DiscussionActivity;

public class PushNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

//        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle() != null ? remoteMessage.getNotification().getTitle() : "Title";
//        String text = Objects.requireNonNull(remoteMessage.getNotification()).getBody() != null ? remoteMessage.getNotification().getBody() : "Body";


        final String CHANNEL_ID = "NOTIFICATION";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("First Message")
                    .setContentText("Hello World")
                    .setSmallIcon(R.drawable.m2)
                    .setAutoCancel(true);

            Integer contact = null, refRealty = null;

            try {
                JSONObject data = new JSONObject(remoteMessage.getData().toString());
                contact = data.getInt("contact");
                refRealty = data.getInt("refRealty");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(getApplicationContext(), DiscussionActivity.class);
            intent.putExtra("contact", contact);
            intent.putExtra("ref_realty", refRealty);
            intent.putExtra("owner", false);

            PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
            notification.setContentIntent(activity);

            NotificationManagerCompat.from(this).notify(1, notification.build());

            Log.d(Constants.TAG, "Data: " + remoteMessage.getData().toString());
            Log.d(Constants.TAG, "Local data: " + contact + " and " + refRealty);
        }

        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
