package com.example.runningman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.runningman.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPage extends AppCompatActivity {

    private static final String TAG = "UserPage";

    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    DatabaseReference usersDb;

    User user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_login:
            case R.id.nav_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_playground:
                intent = new Intent(this, PlayGround.class);
                startActivity(intent);
                return true;
            case R.id.nav_profile:
                intent = new Intent(this, UserPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        //refers to database
        mDatabase = FirebaseDatabase.getInstance();
        usersDb = mDatabase.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        usersDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mAuth.getCurrentUser()!=null){
                    String uid = (String) mAuth.getCurrentUser().getUid();
                    user = snapshot.child(uid).getValue(User.class);
                    Log.d(TAG, "onDataChange: "+user.getEmail());
                    Log.d(TAG, "onDataChange: "+user.getUser_id());
                    Log.d(TAG, "onDataChange: "+user.getUsername());
                    Log.d(TAG, "onDataChange: "+user.getClass());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}