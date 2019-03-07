package e.iot.betbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import e.iot.betbuddy.adapters.MessageAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;

public class MessageActivity extends AppCompatActivity {

    MessageAdapter messageAdapter;
    Group lightweightGroup;
    Group group;
    private DrawerLayout drawerLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void retrieveGroupFromDb() {
        Log.d("retriving","Retrieving group from firestore");
        db.collection("groups").document(lightweightGroup.gid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("FFFFFF","SUCCESSS!!!!!");
                        group = documentSnapshot.toObject(Group.class);

                        Log.d("FIREBASE",""+group.messages.get(0));

                        messageAdapter  = new MessageAdapter(group,MessageActivity.this);



                        ListView messageListView = findViewById(R.id.messages_ListView);
                        messageListView.setAdapter(messageAdapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore","REQUEST TO FIRESTORE FAILED!!!!!");
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        lightweightGroup = (Group)(DataHolder.getInstance().retrieve("group"));

        retrieveGroupFromDb();






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
                            startActivity(new Intent(MessageActivity.this,MainActivity.class));
                        }
                        if(menuItem.getItemId() == R.id.chat_item) {
                            startActivity(new Intent(MessageActivity.this,GroupActivity.class));
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
                intent = new Intent(MessageActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
