package e.iot.betbuddy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.User;

public class GroupAdapter extends BaseAdapter {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Group> groups = new ArrayList<>();
    private Context context;
    public GroupAdapter(Context context) {
        super();
        this.context = context;
    }
//
//    private void getGroups() {
//        final String uid = mAuth.getCurrentUser().getUid();
//        final String name = mAuth.getCurrentUser().getDisplayName();
//        db.collection("").document(uid).get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                        User user = documentSnapshot.toObject(User.class);
//
//                        if(user!=null) Log.d("FIREBASE","already existing doc : "+user.getUid());
//                        else{
//                            Map<Object,Object> newUser = new HashMap<>();
//                            newUser.put("uid",uid);
//                            newUser.put("name",name);
//                            db.collection("users").document(uid).set(newUser);
//                        }
//
//                    }})
//
//        ;
//    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
