package com.example.runningman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runningman.models.RunInfo;
import com.example.runningman.models.User;
import com.example.runningman.models.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RunningActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "RunningActivity";
    private FirebaseAuth mAuth;
    private User user;
    private String startDate;
    private RunInfo runInfo;
    private Date startTime;
    private LatLng startlatLng;
    private boolean startCount;

    GoogleMap map;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;

    FirebaseDatabase mDatabase;
    DatabaseReference locationDb,runRecordDb;

    Runnable runnable;

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
        setContentView(R.layout.activity_running);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //init
        startCount = false;
        //Database reference
        mDatabase = FirebaseDatabase.getInstance();
        locationDb = mDatabase.getReference("locations");
        runRecordDb = mDatabase.getReference("run_records");
        //Initial user information
        mAuth = FirebaseAuth.getInstance();

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(RunningActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //When Permission granted
            getCurrentLocation();
        } else {
            //Request permission
            ActivityCompat.requestPermissions(RunningActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        findViewById(R.id.startRun).setOnClickListener(this);
        findViewById(R.id.stopRun).setOnClickListener(this);

        //Run updateLocation every x minutes
        final int delay = 5000; //milliseconds
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: running");
                handler.postDelayed(runnable, delay);
                updateLocation();
            }
        },delay);

    }

    private void updateLocation(){
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location!=null){
                    Log.d(TAG, "onSuccess: updating");
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            // current location
//                            LatLng currentlatLng = new LatLng(location.getLatitude(),
//                                    location.getLongitude());
                            LatLng currentlatLng = new LatLng(startlatLng.latitude+0.01,
                                    startlatLng.longitude+0.01);

                            Log.d(TAG, "onMapReady: "+startlatLng);
                            Log.d(TAG, "onMapReady: "+currentlatLng);

                            Polyline line = googleMap.addPolyline(new PolylineOptions()
                                    .add(startlatLng, currentlatLng)
                                    .width(5)
                                    .color(Color.RED));

                            startlatLng = currentlatLng;
                            Log.d(TAG, "onMapReady: updating startlatLng"+startlatLng);
                        }
                    });
                }
            }
        });
    }

    private void getCurrentLocation(){
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location!=null){
                    Log.d(TAG, "onSuccess: ");
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Store current location in database
                            UserLocation currentlocation = new UserLocation(location.getLatitude(),location.getLongitude());

                            //locate in the map
                            LatLng latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            startlatLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            //Create marker options
                            MarkerOptions options = new MarkerOptions()
                                    .position(latLng)
                                    .title("You are here");
                            //Zoom map
                            float zoom = 15;
                            // zoom level: // 1:world // 5:landmass/continent //10: City // 15: Street // 20: Building
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                            //Add marker
                            googleMap.addMarker(options);

                        }
                    });
                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //When permission granted
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startRun:{
                Button Start_stop =
                        (Button)findViewById(R.id.startRun);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy hh:mm:ss a");
                startDate = simpleDateFormat.format(calendar.getTime());
                startTime = calendar.getTime();
                startCount = true;
                Log.d(TAG, "onClick: "+startDate);
                Log.d(TAG, "onClick2: "+startTime);

                break;
            }
            case R.id.stopRun:{
                if (startCount){
                    Calendar calendar = Calendar.getInstance();
                    Date finishTime = calendar.getTime();
                    Log.d(TAG, "onClick: "+finishTime);
                    long diff = finishTime.getTime() - startTime.getTime();
                    Log.d(TAG, "onClick: "+diff);

                    long diffSeconds = diff / 1000;
                    long diffMinutes = diff / (60*1000);
                    long diffHours = diff / (60*60*1000);

                    if (mAuth.getCurrentUser()==null){
                        Toast.makeText(RunningActivity.this,"Please login first!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Store info in database
                    RunInfo runInfo = new RunInfo(diff,0,0,startDate);
                    String uid = mAuth.getCurrentUser().getUid();
                    runRecordDb.child(uid).setValue(runInfo);
                    startCount = false;
                }else{
                    Toast.makeText(RunningActivity.this,"Please start first", Toast.LENGTH_SHORT).show();
                }

                break;
            }
        }
    }
}