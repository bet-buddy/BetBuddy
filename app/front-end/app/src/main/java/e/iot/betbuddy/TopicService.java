package e.iot.betbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import e.iot.betbuddy.services.UserService;

public class TopicService {
    private static final TopicService service = new TopicService();
    public static TopicService getInstance() {return service;}
    private static String TAG = "Topic Service";
    public void subscribe(final String topic, final Context context) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "successufully subscribe to "+topic;
                        if (!task.isSuccessful()) {
                            msg = "cannot subscribe to :"+topic;
                        }
                        Log.d(TAG, msg);
//                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
