package e.iot.betbuddy.adapters;

import e.iot.betbuddy.R;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.League;

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

    private ArrayList<League> leagues;
    private Context context;

    public LeagueAdapter(Context context) {
        super();

        this.context = context;
        this.leagues = (ArrayList<League>) (DataHolder.getInstance().retrieve("leagues"));
    }

    @Override
    public int getCount() {
        return leagues.size();
    }

    @Override
    public Object getItem(int position) {
        return leagues.get(position);
    }

    @Override
    public long getItemId(int position) {
        League leaguePos = leagues.get(position);
        return Long.parseLong(leaguePos.getIdLeague());
    }

    @Override
    @SuppressLint({"ViewHolder","SetTextI18n"})
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowMain = layoutInflater.inflate(R.layout.league_adapter,parent,false);

        TextView groupNameTextView = rowMain.findViewById(R.id.leaguename_textview);

        groupNameTextView.setText(leagues.get(position).getStrSport());

        TextView contentTextView = rowMain.findViewById(R.id.league_content_TextView);

        contentTextView.setText(leagues.get(position).getStrLeague());

        return rowMain;
    }
}
