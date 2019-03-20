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

import java.util.List;

import e.iot.betbuddy.adapters.GroupAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.Message;
import e.iot.betbuddy.model.User;
import e.iot.betbuddy.services.UserService;

public class GroupActivity extends AppCompatActivity {
    private GroupAdapter groupAdapter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        UserService.getInstance().retrieveUser();
        ListView groupListView = findViewById(R.id.group_ListView);
        groupAdapter  = new GroupAdapter(this);
//        groupListView
        groupListView.setAdapter(groupAdapter);

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User)(DataHolder.getInstance().retrieve("user"));
                Group group = user.getGroupList().get(position);
                DataHolder.getInstance().save("group",group);

                startActivity(new Intent(GroupActivity.this, MessageActivity.class));


            }
        });


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
                        if(menuItem.getItemId() == R.id.feat_item) {
                            startActivity(new Intent(GroupActivity.this, BetActivity.class));
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
                intent = new Intent(GroupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
