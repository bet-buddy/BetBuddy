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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FavoriteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        //ArrayList<Sport> leagueList = (ArrayList<Sport>) (DataHolder.getInstance().retrieve("leagues"));

        //get the spinner from the xml.
        //Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        //String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
       // dropdown.setAdapter(adapter);

//        listView.setAdapter(leagueAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Sports leagueList = (Sports) (DataHolder.getInstance().retrieve("sports"));
//                Sport sport = leagueList.getData().get(position);
//                DataHolder.getInstance().save("sport", sport);
//                startActivity(new Intent(getActivity(), LeagueActivity.class));
//            }
//        });

        return view;
    }
}
