package e.iot.betbuddy.adapters;

import e.iot.betbuddy.R;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Sport;
import e.iot.betbuddy.model.Sports;

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

public class LeagueAdapter extends BaseAdapter {

    private Sports leagues;
    private Context context;

    public LeagueAdapter(Context context) {
        super();

        this.context = context;
        this.leagues = (Sports) DataHolder.getInstance().retrieve("sports");
    }

    @Override
    public int getCount() {
        return leagues.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return leagues.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        Sport leaguePos = leagues.getData().get(position);
        return Long.parseLong(leaguePos.getKey());
    }

    @Override
    @SuppressLint({"ViewHolder","SetTextI18n"})
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowMain = layoutInflater.inflate(R.layout.league_adapter,parent,false);

        TextView groupNameTextView = rowMain.findViewById(R.id.leaguename_textview);

        groupNameTextView.setText(leagues.getData().get(position).getDetails());

        TextView contentTextView = rowMain.findViewById(R.id.league_content_TextView);

        contentTextView.setText(leagues.getData().get(position).getGroup());

        return rowMain;
    }
}
