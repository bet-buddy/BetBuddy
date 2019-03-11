package e.iot.betbuddy.adapters;

import e.iot.betbuddy.R;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Sport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import e.iot.betbuddy.model.Site;
import e.iot.betbuddy.model.Bet;
import e.iot.betbuddy.model.*;

import java.util.ArrayList;

public class MatchupAdapter extends BaseAdapter {

    private ArrayList<Bet> bets;
    private Context context;

    public MatchupAdapter(Context context) {
        super();

        this.context = context;
        Bets betData = (Bets) (DataHolder.getInstance().retrieve("bets"));
        bets = betData.getData();
    }

    @Override
    public int getCount() {
        return bets.size();
    }

    @Override
    public Object getItem(int position) {
        return bets.get(position);
    }

    @Override
    public long getItemId(int position) {
        Bet betPos = bets.get(position);
        return betPos.getCommence_time();
    }


    @Override
    @SuppressLint({"ViewHolder","SetTextI18n"})
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowMain = layoutInflater.inflate(R.layout.matchup_adapter,parent,false);

        TextView groupNameTextView = rowMain.findViewById(R.id.matchupname_textview);

        ArrayList<String> teams = bets.get(position).getTeams();
        String teamText = (String) teams.get(0) + " vs. " +  teams.get(1);
        groupNameTextView.setText(teamText);

        TextView contentTextView = rowMain.findViewById(R.id.matchup_content_TextView);

        ArrayList<Site> sites = bets.get(position).getSites();
        ArrayList<Odds> odds = sites.get(0).getOdds();
        String oddsText = (String) teams.get(0) + ": " + odds.get(0).geth2h().get(0) + ", "
                + teams.get(1) + ": " + odds.get(0).geth2h().get(1) + ", " + "Draw: "
                + odds.get(0).geth2h().get(2);
        contentTextView.setText(oddsText);

        return rowMain;
    }
}
