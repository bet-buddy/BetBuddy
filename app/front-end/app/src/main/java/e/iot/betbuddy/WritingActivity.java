package e.iot.betbuddy;

import android.annotation.SuppressLint;
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
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

import e.iot.betbuddy.adapters.MessageAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.User;

public class WritingActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

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
                            startActivity(new Intent(WritingActivity.this,MainActivity.class));
                        }
                        if(menuItem.getItemId() == R.id.chat_item) {
                            startActivity(new Intent(WritingActivity.this,GroupActivity.class));
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
                intent = new Intent(WritingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("PrivateResource")

    public void sendMessage(View view) {
        mAuth = FirebaseAuth.getInstance();
        HashMap<String,Object> message = new HashMap<>();
        User user = (User) DataHolder.getInstance().retrieve("user");
        EditText titleEditText = findViewById(R.id.title_editText);
//        EditText contentEditText = findViewById(R.id.enter_message_editText);
        message.put("author",mAuth.getCurrentUser().getDisplayName());
        message.put("title",titleEditText.getText().toString());
//        message.put("content",contentEditText.getText().toString());

        String mid = UUID.randomUUID().toString();

        message.put("mid",mid);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        message.put("timestamp",timestamp);

        Group group = (Group)(DataHolder.getInstance().retrieve("group"));
        DocumentReference documentReference = db.collection("groups").document(group.gid);
        documentReference.update("messages", FieldValue.arrayUnion(message));


        startActivity(new Intent(WritingActivity.this,MessageActivity.class));
    }

}
