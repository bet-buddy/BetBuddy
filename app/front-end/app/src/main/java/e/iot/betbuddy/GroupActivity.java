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
