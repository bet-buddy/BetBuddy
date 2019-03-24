package e.iot.betbuddy;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.League;
import e.iot.betbuddy.model.Leagues;
import e.iot.betbuddy.model.Sports;
import e.iot.betbuddy.model.User;
import e.iot.betbuddy.services.UserService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Sports sports;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth;
    private User user;
    private TextView mStatusView;
    private TextView mDetailView;

    private void retrieveData() {

        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.the-odds-api.com/v3/sports/?apiKey=a59ac2c7edc94c6c8ffd8814724e0658";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JsonParser parser = new JsonParser();
                        JsonElement mJson = parser.parse(response.toString());
                        Gson gson = new Gson();

                        // TODO: Use this data to implement a League Adapter for the Bet Activity
                        sports =  gson.fromJson(mJson, Sports.class);
                        Log.d("HTTP","leagues : "+sports);
                        Log.d("HTTP","Response: " + response.toString());
                        DataHolder.getInstance().save("sports",sports);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BetFragment()).commit();
                        queue.stop();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HTTP","HTTP request fails");

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);
        DataHolder.getInstance().save("queue",queue);
    }
    private void checkForUserInDb() {
        mAuth = FirebaseAuth.getInstance();
        final String UID = mAuth.getCurrentUser().getUid();
        if(UID == null) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        final String name = mAuth.getCurrentUser().getDisplayName();
        db.collection("users").document(UID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        user = documentSnapshot.toObject(User.class);

                        if(user!=null) {
                            DataHolder.getInstance().save("user",user);
                            Log.d("FIREBASE","already existing doc : "+user.toString());
                        }
                        else{
                            Map<Object,Object> newUser = new HashMap<>();
                            newUser.put("uid",UID);
                            newUser.put("name",name);
                            db.collection("users").document(UID).set(newUser);
                            DataHolder.getInstance().save("user",newUser);
                        }

                    }})

        ;
    }

    public void retrieveUser() {
        final String token = UserService.getInstance().retrieveUserToken();
        mAuth = FirebaseAuth.getInstance();
        final String UID = mAuth.getCurrentUser().getUid();
        final String name = mAuth.getCurrentUser().getDisplayName();
        db.collection("users").document(UID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        User user = documentSnapshot.toObject(User.class);
                        if(user!=null) {
                            user.setToken(token);
                            DataHolder.getInstance().save("user",user);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new GroupFragment()).commit();
                            Log.d("FIREBASE","already existing doc : "+user.toString());
//                            Map<Object,Object> updatedUser = new HashMap<>();
//                            updatedUser.put("uid",UID);
//                            updatedUser.put("name",user.getName());
//                            updatedUser.put("groupList",user.getGroupList());
//                            updatedUser.put("token",user.getToken());
//                            db.collection("users").document(UID).
                            db.collection("users").document(UID).update("token",user.getToken());

                        }
                        else{
                            Map<Object,Object> newUser = new HashMap<>();
                            newUser.put("uid",UID);
                            newUser.put("name",name);
                            newUser.put("token",token);
                            newUser.put("groupList",new ArrayList<Group>());
                            db.collection("users").document(UID).set(newUser);

                            User newUser2 = new User();
                            newUser2.setToken(token);
                            newUser2.setUid(UID);
                            newUser2.setName((String)newUser.get("name"));
                            newUser2.setGroupList(new ArrayList<Group>());
                            DataHolder.getInstance().save("user",newUser2);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new GroupFragment()).commit();
                            Log.d("USERSERVICE","added new user :"+newUser2.toString());
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("USER FAILURE","Cannot retrieve user");
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //UserService.getInstance().retrieveUser();
        retrieveData();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MainFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.chat_item:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //        new GroupFragment()).commit();
                //startActivity(new Intent(MainActivity.this, GroupActivity.class));
                retrieveUser();
                break;
            case R.id.feat_item:
                retrieveData();
                //startActivity(new Intent(MainActivity.this, BetActivity.class));
                break;
            case R.id.exit:
                signOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
