package com.example.runningman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_playground:
                intent = new Intent(MainActivity.this, PlayGround.class);
                startActivity(intent);
                return true;
            case R.id.nav_profile:
                intent = new Intent(MainActivity.this, UserPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.go_user_page).setOnClickListener(this);
        findViewById(R.id.go_playground).setOnClickListener(this);
        findViewById(R.id.extendTwo).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.go_user_page:{
                Intent intent = new Intent(MainActivity.this, UserPage.class);
                startActivity(intent);
                break;
            }
            case R.id.go_playground:{
                Intent intent = new Intent(MainActivity.this, PlayGround.class);
                startActivity(intent);
                break;
            }
        }
    }
}