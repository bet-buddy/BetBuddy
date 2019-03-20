package e.iot.betbuddy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e.iot.betbuddy.GroupActivity;
import e.iot.betbuddy.R;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.User;
import e.iot.betbuddy.services.UserService;

public class GroupAdapter extends BaseAdapter {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Group> groups = new ArrayList<>();
    private Context context;

    public GroupAdapter(Context context) {
        super();

        this.context = context;
        getGroups();
    }

    //
    private void getGroups() {
        User user = (User)(DataHolder.getInstance().retrieve("user"));
        if(user!=null && user.getGroupList()!= null) {
            Log.d("FIREBASE",""+user);
            this.groups = user.getGroupList();
        } else
        {
            if(user==null)       {
                Log.e("USER","No user in memory");
                UserService.getInstance().retrieveUser();
                ((GroupActivity)context).recreate();
                }
            else Log.e("GROUPLST","No group");

        }
    }



    @Override
    public int getCount() {
        return this.groups.size();
    }

    @Override
    public Object getItem(int position) {
        return this.groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressLint({"ViewHolder","SetTextI18n"})
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowMain = layoutInflater.inflate(R.layout.group_adapter,parent,false);

        TextView groupNameTextView = rowMain.findViewById(R.id.groupname_textview);

        groupNameTextView.setText(groups.get(position).group);

        TextView contentTextView = rowMain.findViewById(R.id.content_TextView);

        contentTextView.setText(groups.get(position).gid);

        return rowMain;
    }
}
