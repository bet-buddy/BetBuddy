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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import e.iot.betbuddy.adapters.LeagueAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.*;
import e.iot.betbuddy.adapters.MatchupAdapter;

public class LeagueActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Bets bets;
    private ListView listView;
    MatchupAdapter adapter;

    private void retrieveData() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.the-odds-api.com/v3/odds?sport=soccer_epl&region=uk&mkt=h2h&apiKey=b3496429f5a38cffe315865f31719b21";
        Log.d("DATA ret","It is retrieving DATA!!!");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JsonParser parser = new JsonParser();
                        JsonElement mJson = parser.parse(response.toString());
                        Gson gson = new Gson();

                        // TODO: Use this data to implement a League Adapter for the Bet Activity
                        bets =  gson.fromJson(mJson, Bets.class);
                        Log.d("HTTP","bets : "+ bets);
                        Log.d("HTTP","Response: " + response.toString());
                        DataHolder.getInstance().save("bets", bets);
                         adapter = new MatchupAdapter(LeagueActivity.this);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                retrieveData();
                                //Bet bet = betList.get(position);
                                //DataHolder.getInstance().save("bet", bet);
                                ArrayList<Bet> betList = bets.getData();
                                Bet bet = betList.get(position);
                                DataHolder.getInstance().save("bet",bet);
                                startActivity(new Intent(LeagueActivity.this, BetSubmitActivity.class));
                            }
                        });
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HTTP","HTTP request fails");

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);

//        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
//            @Override
//            public void onRequestFinished(Request<Object> request) {
//                Log.d("HTTP","bets req finished");
//                try {
//                    Log.d("HTTP","request = "+request.getBody().toString());
//                } catch (AuthFailureError authFailureError) {
//                    authFailureError.printStackTrace();
//                }
//            }
//        });
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
         listView = findViewById(R.id.leagues_ListView);
        retrieveData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.league_nav_view );
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
                            startActivity(new Intent(LeagueActivity.this, GroupActivity.class));
                        }
                        return true;
                    }
                });

        //final ArrayList<Bet> betList = (ArrayList<Bet>) (DataHolder.getInstance().retrieve("bets"));

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
                intent = new Intent(LeagueActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
