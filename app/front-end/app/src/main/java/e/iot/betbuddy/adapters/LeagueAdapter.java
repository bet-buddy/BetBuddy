package e.iot.betbuddy.adapters;

import e.iot.betbuddy.model.League;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class LeagueAdapter extends BaseAdapter {

    private ArrayList<League> leagues;

    public LeagueAdapter(ArrayList<League> leagueList) {
        this.leagues = leagueList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
