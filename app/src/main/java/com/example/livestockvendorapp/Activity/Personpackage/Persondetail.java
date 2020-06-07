package com.example.livestockvendorapp.Activity.Personpackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.livestockvendorapp.Activity.MapsActivity;
import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Persondetail extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    FirebaseFirestore db;
    TextView username, usernumber, userrating, userearning;
    TextView userordercount, userlocation;
    ImageButton btn;


    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    String address, City;
    private static final String TAG = "Persondetail";
    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personadetail);


        username = findViewById(R.id.user_name);
        usernumber = findViewById(R.id.user_number);
        userlocation = findViewById(R.id.user_location);
        userearning = findViewById(R.id.user_earning);
        userordercount = findViewById(R.id.user_ordercount);
        userrating = findViewById(R.id.user_rating);

        btn = findViewById(R.id.location_btn);
        db = FirebaseFirestore.getInstance();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        checkMapServices();
        set_Data();
    }


    public void set_Data() {

        usernumber.setText(Common.currentuser.getPhone());
        userlocation.setText(Common.currentuser.getLocation());
        username.setText(Common.currentuser.getName());

        userrating.setText(Double.toString(Common.totalrating));
        userearning.setText(Double.toString(Common.totalcost));
        userordercount.setText(Integer.toString(Common.totalcount));


    }


//    public void init() {
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(20 * 1000);
//
//
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//
//                if (locationResult == null) {
//                    return;
//                } else {
//
//                    for (Location location : locationResult.getLocations()) {
//
//
//                        if (location != null) {
//                            Common.longitude = location.getLongitude();
//                            Common.latitude = location.getLatitude();
//                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//                            try {
//                                List<Address> list = geocoder.getFromLocation(
//                                        location.getLatitude(), location.getLongitude(), 1
//                                );
//
//                                address = list.get(0).getAddressLine(0);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//        };
//
//
//        getlocation();
//    }


    public void getlocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> list = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        address = list.get(0).getAddressLine(0);
                        Common.latitude = list.get(0).getLatitude();
                        Common.longitude = list.get(0).getLongitude();
                        City = list.get(0).getCountryName();

                        Common.currentuser.setLocation(address);
                        //  textView.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude:</b><br></font>" + list.get(0).getAddressLine(0)));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getlocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Persondetail.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Persondetail.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    getlocation();
                } else {
                    getLocationPermission();
                }
            }
            case 100: {
                if (resultCode == RESULT_OK) {

                } else if (resultCode == RESULT_CANCELED) {

                }
            }
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
//        if (checkMapServices()) {
//            if (mLocationPermissionGranted) {
//                getlocation();
//
//            }else{
//                getLocationPermission();
//            }
//        }
    }
}
