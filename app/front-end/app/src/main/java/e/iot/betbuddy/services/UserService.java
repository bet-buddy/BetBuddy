package e.iot.betbuddy.services;

import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.User;

public class UserService {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView mStatusView;
    private TextView mDetailView;
    private static final UserService service = new UserService();
    public static UserService getInstance() {return service;}
    public void retrieveUser() {
        mAuth = FirebaseAuth.getInstance();
        final String UID = mAuth.getCurrentUser().getUid();
        final String name = mAuth.getCurrentUser().getDisplayName();
        db.collection("users").document(UID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        User user = documentSnapshot.toObject(User.class);

                        if (user != null) {
                            DataHolder.getInstance().save("user", user);
                            Log.d("FIREBASE", "already existing doc : " + user.toString());
                        } else {
                            Map<Object, Object> newUser = new HashMap<>();
                            newUser.put("uid", UID);
                            newUser.put("name", name);
                            db.collection("users").document(UID).set(newUser);

                            User user2 = new User();
                            user2.setName((String)newUser.get("name"));
                            user2.setUid(UID);
                            DataHolder.getInstance().save("user", user2);
                        }

                    }
                });


    }
}
