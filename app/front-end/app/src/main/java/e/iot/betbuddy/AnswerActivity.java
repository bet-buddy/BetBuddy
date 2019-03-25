package e.iot.betbuddy;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import e.iot.betbuddy.model.PendingBet;

public class AnswerActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String home_team_name;
    private String away_team_name;
    private String home_team_odd;
    private String away_team_odd;
    private String sender;
    private String senderbet;
    private String senderid;
    private String senderpoints;
    private FirebaseAuth mAuth;
    private RadioButton awayButton;
    private RadioButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();

        home_team_name = intent.getStringExtra("home_team");
        away_team_name = intent.getStringExtra("away_team");
        String game_title = home_team_name + " : " + away_team_name;

        home_team_odd = intent.getStringExtra("home_team_odd");
        away_team_odd = intent.getStringExtra("away_team_odd");
        senderid = intent.getStringExtra("senderid");

        sender = intent.getStringExtra("sender");
        senderbet = intent.getStringExtra("senderbet");

        senderpoints = intent.getStringExtra("senderpoints");


        TextView oponenttextview = findViewById(R.id.oponentTextView);
        oponenttextview.setText(sender + " bet on " + senderbet);

        TextView homeTextView = findViewById(R.id.game_text_view);
        homeTextView.setText(game_title);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        homeButton = findViewById(R.id.radioButtonhome);
        awayButton = findViewById(R.id.radioButtonAway);

        homeButton.setText(home_team_name + ": " + home_team_odd);
        awayButton.setText(away_team_name + ": " + away_team_odd);


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

                        if (menuItem.getItemId() == R.id.exit) {
                            signOut();
                        }

                        if (menuItem.getItemId() == R.id.chat_item) {

                            startActivity(new Intent(AnswerActivity.this, GroupActivity.class));
                        }

                        if (menuItem.getItemId() == R.id.feat_item) {
                            startActivity(new Intent(AnswerActivity.this, BetActivity.class));
                        }
                        return true;
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
                intent = new Intent(AnswerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void submitBet(View view) {
        mAuth = FirebaseAuth.getInstance();
        PendingBet pendingBet = new PendingBet();
        pendingBet.setAway_team(away_team_name);
        pendingBet.setHome_team(home_team_name);
        pendingBet.setReceiver(mAuth.getCurrentUser().getDisplayName());
        pendingBet.setSender(this.sender);
        pendingBet.setSenderid(senderid);

        ArrayList<Float> odds = new ArrayList<>();
        odds.add(Float.parseFloat(this.home_team_odd));
        odds.add(Float.parseFloat(this.away_team_odd));

        pendingBet.setOdds(odds);

        pendingBet.setSenderbet(senderbet);
        pendingBet.setSenderpoints(Integer.parseInt(senderpoints));

        RadioGroup radioGroup = findViewById(R.id.radioGroupAnswer);
        if (radioGroup.getCheckedRadioButtonId() == homeButton.getId())
            pendingBet.setReceiverbet(home_team_name);
        else pendingBet.setReceiverbet(away_team_name);

        EditText pointsReceiver = findViewById(R.id.pointReceiver);

        pendingBet.setReceiverpoints(Integer.parseInt(pointsReceiver.getText().toString()));


        pendingBet.setReceiverid(mAuth.getUid());

        db.collection("Pendingbets").add(pendingBet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("NEWPENDINGBET", "successfully created a pending bet");
            }
        });

        startActivity(new Intent(AnswerActivity.this, MainActivity.class));


    }

}