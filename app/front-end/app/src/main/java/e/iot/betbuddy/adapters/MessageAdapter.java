package e.iot.betbuddy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import e.iot.betbuddy.R;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;

public class MessageAdapter extends BaseAdapter {
    Group lightweightGroup;
    Group group;
    Context context;

    public MessageAdapter(Group lightweightGroup, Context context) {
//        super();
        this.context = context;
        this.group = lightweightGroup;
    }




    @Override
    public int getCount() {
        return group.messages.size();
    }

    @Override
    public Object getItem(int position) {
        return group.messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(group.messages.get(position).mid);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowMain = layoutInflater.inflate(R.layout.message_adapter,parent,false);

        TextView titleTextView = rowMain.findViewById(R.id.title_TextView);
        titleTextView.setText(group.messages.get(position).title);


        TextView contentView = rowMain.findViewById(R.id.message_TextView);
        contentView.setText(group.messages.get(position).content);

        TextView authorView =rowMain.findViewById(R.id.author_textView);
        authorView.setText(group.messages.get(position).author);


        return rowMain;
    }
}
