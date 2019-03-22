package e.iot.betbuddy;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import e.iot.betbuddy.adapters.LeagueAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.League;
import e.iot.betbuddy.model.Message;
import e.iot.betbuddy.model.User;
import e.iot.betbuddy.model.Sport;
import e.iot.betbuddy.model.Sports;

public class BetActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LeagueAdapter adapter = new LeagueAdapter(this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);



        //final ArrayList<Sport> leagueList = (ArrayList<Sport>) (DataHolder.getInstance().retrieve("leagues"));
        ListView listView = (ListView) findViewById(R.id.leagues_ListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sports leagueList = (Sports) (DataHolder.getInstance().retrieve("sports"));
                Sport sport = leagueList.getData().get(position);
                DataHolder.getInstance().save("sport", sport);

                startActivity(new Intent(BetActivity.this, LeagueActivity.class));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent;
                intent = new Intent(BetActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
