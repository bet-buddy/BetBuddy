package e.iot.betbuddy.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import e.iot.betbuddy.BetSubmitActivity;
import e.iot.betbuddy.LoginActivity;
import e.iot.betbuddy.MainActivity;
import e.iot.betbuddy.R;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.User;


public class UserService {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
private String token;
private String TAG ="MESSAGES";
    private static final UserService service = new UserService();
    public static UserService getInstance() {return service;}
    public void retrieveUser() {
        this.retrieveUserToken();
        mAuth = FirebaseAuth.getInstance();
        final String UID = mAuth.getCurrentUser().getUid();
        final String name = mAuth.getCurrentUser().getDisplayName();
        db.collection("users").document(UID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        User user = documentSnapshot.toObject(User.class);
if(user!=null) {
                            user.setToken(token);
                            DataHolder.getInstance().save("user",user);
                            Log.d("FIREBASE","already existing doc : "+user.toString());
//                            Map<Object,Object> updatedUser = new HashMap<>();
//                            updatedUser.put("uid",UID);
//                            updatedUser.put("name",user.getName());
//                            updatedUser.put("groupList",user.getGroupList());
//                            updatedUser.put("token",user.getToken());
//                            db.collection("users").document(UID).
                            db.collection("users").document(UID).update("token",user.getToken());

                        }
                        else{
                            Map<Object,Object> newUser = new HashMap<>();
                            newUser.put("uid",UID);
                            newUser.put("name",name);
                            newUser.put("token",token);
                            newUser.put("groupList",new ArrayList<Group>());
                            db.collection("users").document(UID).set(newUser);

                            User newUser2 = new User();
                            newUser2.setToken(token);
                            newUser2.setUid(UID);
                            newUser2.setName((String)newUser.get("name"));
                            newUser2.setGroupList(new ArrayList<Group>());
                            DataHolder.getInstance().save("user",newUser2);
                            Log.d("USERSERVICE","added new user :"+newUser2.toString());
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("USER FAILURE","Cannot retrieve user");
            }
        });


    }


    public String retrieveUserToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        Log.d(TAG, token);
                        // Log and toast
                        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String msg = "token :"+ token;
                        Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        return token;
    }
}
