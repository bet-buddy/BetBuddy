package e.iot.betbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import e.iot.betbuddy.adapters.LeagueAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Sport;
import e.iot.betbuddy.model.Sports;

public class BetFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bet, container, false);
        //ArrayList<Sport> leagueList = (ArrayList<Sport>) (DataHolder.getInstance().retrieve("leagues"));
        ListView listView = view.findViewById(R.id.leagues_ListView);
        LeagueAdapter leagueAdapter = new LeagueAdapter(this.getActivity());

        listView.setAdapter(leagueAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sports leagueList = (Sports) (DataHolder.getInstance().retrieve("sports"));
                Sport sport = leagueList.getData().get(position);
                DataHolder.getInstance().save("sport", sport);
                startActivity(new Intent(getActivity(), BetActivity.class));
            }
        });

        return view;
    }
}
