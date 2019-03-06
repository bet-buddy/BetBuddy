package e.iot.betbuddy.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import e.iot.betbuddy.model.Group;

public class GroupAdapter extends BaseAdapter {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private List<Group> groups = new ArrayList<>();
    private Context context;
    public GroupAdapter(Context context) {
        super();
        this.context = context;
    }

    private void getGroups() {
        String UID = mAuth.getCurrentUser().getUid();

    }

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
