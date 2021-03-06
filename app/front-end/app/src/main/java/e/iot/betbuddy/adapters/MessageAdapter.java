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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import e.iot.betbuddy.R;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.Message;

public class MessageAdapter extends BaseAdapter {
    Group lightweightGroup;
    Group group;
    Context context;

    public MessageAdapter(Group lightweightGroup, Context context) {
//        super();
        this.context = context;
        this.group = lightweightGroup;
        ArrayList<Message> newMessages = new ArrayList<>();
        Log.d("GROUP",""+group);
        if(group==null) {
            group = new Group();
        }
        if(group.messages==null) {
            group.messages = new ArrayList<>();
        }
        for(int i = group.messages.size()-1;i>=0;i--){
            newMessages.add(group.messages.get(i));
        }
        this.group.messages = newMessages;
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
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowMain = layoutInflater.inflate(R.layout.message_adapter,parent,false);

        TextView titleTextView = rowMain.findViewById(R.id.title_TextView);
        titleTextView.setText(group.messages.get(position).title);

        TextView authorView =rowMain.findViewById(R.id.author_textView);
        authorView.setText(group.messages.get(position).author);


        return rowMain;
    }
}
