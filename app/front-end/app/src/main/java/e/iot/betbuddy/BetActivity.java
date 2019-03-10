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

public class BetActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        if(menuItem.getItemId() == R.id.exit) {
                            signOut();
                        }
                        if(menuItem.getItemId() == R.id.chat_item) {
                            startActivity(new Intent(BetActivity.this, GroupActivity.class));
                        }
                        return true;
                    }
                });

        ArrayList<League> leagueList = (ArrayList<League>) getIntent().getSerializableExtra("leagues");
        ListView listView = (ListView) findViewById(R.id.leagues_ListView);
        LeagueAdapter adapter = new LeagueAdapter(leagueList);
        listView.setAdapter(adapter);

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
