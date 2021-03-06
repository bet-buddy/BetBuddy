package e.iot.betbuddy.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import e.iot.betbuddy.AnswerActivity;
import e.iot.betbuddy.GroupActivity;
import e.iot.betbuddy.MainActivity;
import e.iot.betbuddy.R;

public class NotificationService extends FirebaseMessagingService {
    private String TAG = "NOTIFICATIONS";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        Log.d("data message test", remoteMessage.getData().get("test"));
        if(remoteMessage.getData() == null) {

            showWinner(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

            Log.d("Message",remoteMessage.getMessageId());
        }
        else showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),remoteMessage.getData());
        //Log.d(TAG, remoteMessage.getNotification().getTitle() );
        //showNotification(remoteMessage.getData());
    }

    private void showWinner(String title, String body) {

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "e.iot.betbuddy.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification", NotificationManager.IMPORTANCE_DEFAULT);


            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[] {0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launch)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Notification");
        Intent resultIntent = new Intent(this, GroupActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(GroupActivity.class);
        stackBuilder.addNextIntent(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }
    private void showNotification(String title, String body,Map<String,String> data) {

        Log.d(TAG,"notif received");

        // String title = data.get("title");
        //String body = data.get("driver_id") + " needs a last minute ride to " + data.get("dropoff_location");

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "e.iot.betbuddy.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification", NotificationManager.IMPORTANCE_DEFAULT);


            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[] {0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launch)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Notification");
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, AnswerActivity.class);
        resultIntent.putExtra("away_team",data.get("away_team"));
        resultIntent.putExtra("away_team_odd",data.get("away_team_odd"));
        resultIntent.putExtra("home_team",data.get("home_team"));
        resultIntent.putExtra("home_team_odd",data.get("home_team_odd"));
        resultIntent.putExtra("senderbet",data.get("senderbet"));
        resultIntent.putExtra("sender",data.get("sender"));
        resultIntent.putExtra("senderpoints",data.get("senderpoints"));
        resultIntent.putExtra("senderid",data.get("senderid"));
        resultIntent.putExtra("topic",data.get("topic"));
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(AnswerActivity.class);
        stackBuilder.addNextIntent(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());

    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
        String UID = FirebaseAuth.getInstance().getUid();
        db.collection("users").document(UID).update("token",token);

    }

}
