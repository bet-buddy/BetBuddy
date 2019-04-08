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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import e.iot.betbuddy.adapters.LeagueAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.*;
import e.iot.betbuddy.adapters.MatchupAdapter;
import e.iot.betbuddy.services.UserService;

public class BetSubmitActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private DrawerLayout drawerLayout;
    private Sports sports;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String receiverName;
    private Bet bet;
    private Odds odds;
    private ArrayList<String> teams;
    private RadioButton awayButton;
    private RadioButton homeButton;
    ArrayList<Site> sites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_submit);

        bet = (Bet)DataHolder.getInstance().retrieve("bet");

        TextView oddText = findViewById(R.id.oddsText);
        float awayOdd = bet.getSites().get(0).getOdds().geth2h().get(1);
        float homeOdd = bet.getSites().get(0).getOdds().geth2h().get(0);
        oddText.setText("Home: "+homeOdd+" Away: "+awayOdd);
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
                            startActivity(new Intent(BetSubmitActivity.this, GroupActivity.class));
                        }
                        return true;
                    }
                });

        final Bet bet = (Bet) (DataHolder.getInstance().retrieve("bet"));
        teams = bet.getTeams();
        sites = bet.getSites();

        odds = sites.get(0).getOdds();
        String oddsText;
        if(odds.geth2h().size()<3) {
            oddsText = (String) teams.get(0) + ": " + odds.geth2h().get(0) + ", "
                    + teams.get(1) + ": " + odds.geth2h().get(1);
            TextView oddsDisplay = (TextView)findViewById(R.id.oddsText);
        }
        else {
            oddsText = (String) teams.get(0) + ": " + odds.geth2h().get(0) + ", "
                    + teams.get(1) + ": " + odds.geth2h().get(1) + ", " + "Draw: "
                    + odds.geth2h().get(2);
        }

        TextView oddsDisplay = (TextView)findViewById(R.id.oddsText);
        oddsDisplay.setText(oddsText);

        homeButton = (RadioButton)findViewById(R.id.radioHome);
        awayButton = (RadioButton)findViewById(R.id.radioAway);
        homeButton.setText(teams.get(0));
        awayButton.setText(teams.get(1));

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
                intent = new Intent(BetSubmitActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void submit(View view) {
        UserService.getInstance().retrieveUser();
        User user = (User) DataHolder.getInstance().retrieve("user");
        Log.d("USER BET SUB",""+user);
        if(user == null){
            startActivity(new Intent(this,LoginActivity.class));
            Log.e("USER NAME ERR","");
        }
        EditText opponentText = findViewById(R.id.opponentText);
        receiverName = opponentText.getText().toString();
        mAuth = FirebaseAuth.getInstance();

        if(!(receiverName.isEmpty())) {


            RadioGroup groupButton = findViewById(R.id.radioTeam);
            final Notification notification = new Notification();
            if(groupButton.getCheckedRadioButtonId() == homeButton.getId()) notification.setSenderbet(homeButton.getText().toString());
            else notification.setSenderbet(awayButton.getText().toString());
            notification.setSender(mAuth.getCurrentUser().getDisplayName());
            notification.setSenderid(mAuth.getUid());
            notification.setAway_team(teams.get(1));
            notification.setHome_team(teams.get(0));

            notification.setTopic(UUID.randomUUID().toString().replace("-",""));

            EditText pointText = findViewById(R.id.pointText);
            int senderpoints = Integer.parseInt(pointText.getText().toString());

            notification.setSenderpoints(senderpoints);
            List<Float> listOdds = new ArrayList<Float>();
            if(odds.geth2h().size()>2) {
                listOdds.add(odds.geth2h().get(0));
                listOdds.add(odds.geth2h().get(2));

            }else {

                listOdds.add(odds.geth2h().get(0));
                listOdds.add(odds.geth2h().get(1));

            }
            notification.setOdds(listOdds);
            notification.setReceiver(receiverName);
            db.collection("users").whereEqualTo("name",receiverName).get().addOnSuccessListener(
                    new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            DocumentSnapshot d = queryDocumentSnapshots.getDocuments().get(0);
                            User receiver = d.toObject(User.class);
                            assert receiver != null;
                            notification.setReceivertoken(receiver.getToken());
                            notification.setReceiverid(receiver.getUid());
                            db.collection("Notifications").add(notification);
                            Intent intent = new Intent(BetSubmitActivity.this, LeagueActivity.class);
                            startActivity(intent);
                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Opponent not found","can't find "+receiverName+" in firestore db");
                }
            });
        }



    }

}
