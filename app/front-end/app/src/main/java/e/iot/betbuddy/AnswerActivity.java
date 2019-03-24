package e.iot.betbuddy;

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
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AnswerActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();

        String home_team_name = intent.getStringExtra("home_team");
        String away_team_name = intent.getStringExtra("away_team");
        String game_title = home_team_name + " : "+away_team_name;

        String home_team_odd = intent.getStringExtra("home_team_odd");
        String away_team_odd = intent.getStringExtra("away_team_odd");


        String sender = intent.getStringExtra("sender");
        String senderbet = intent.getStringExtra("senderbet");

        TextView oponenttextview = findViewById(R.id.oponentTextView);
        oponenttextview.setText(sender+" bet on "+senderbet);

        TextView homeTextView = findViewById(R.id.game_text_view);
        homeTextView.setText(game_title);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        RadioButton homeButton = findViewById(R.id.radioButtonhome);
        RadioButton awayButton = findViewById(R.id.radioButtonAway);

        homeButton.setText(home_team_name+": "+home_team_odd);
        awayButton.setText(away_team_name+ ": "+away_team_odd);



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

}
