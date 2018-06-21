package com.fangyini.findmeabox;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.location.Location;
import android.view.View;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


import java.util.Date;

public class BoxEditActivity extends AppCompatActivity implements LocationListener{
    private Button mSubmit;
    private EditText mEnteredText;
    private AppDatabase mDb;

    protected LocationListener locationListener;
    protected LocationManager locationManager;
    protected Context context;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    protected Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_edit);
        mDb = AppDatabase.getsInstance(getApplicationContext());
        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String savedText = mEnteredText.getText().toString();
                Date date = new Date();
                final Box box = new Box(savedText, date);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.boxDao().insertBox(box);
                        finish();
                    }
                });
            }
        });

        mEnteredText = (EditText) findViewById(R.id.textInBox);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        double a = location.getLatitude();
        double b = location.getLongitude();
        Log.d("test", Double.toString(a));
        Log.d("test", Double.toString(b));
        mEnteredText.setHint(Double.toString(a));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
