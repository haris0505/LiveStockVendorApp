package com.example.livestockvendorapp.BottomNavigationFragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.livestockvendorapp.Activity.MainActivity;
import com.example.livestockvendorapp.Activity.MapsActivity;
import com.example.livestockvendorapp.Activity.Personpackage.Persondetail;
import com.example.livestockvendorapp.Activity.Personpackage.Personreview;
import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {


    ListView listView;
    String[] listitem;
    TextView textView;

//    FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//
//    String address, City;
   // double longitude, latitude;

    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        listView = view.findViewById(R.id.personal_list);
        listitem = getResources().getStringArray(R.array.User_Option);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_2, android.R.id.text1, listitem);
        listView.setAdapter(adapter);
        Paper.init(getActivity());

        textView = view.findViewById(R.id.textview1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //String value=adapter.getItem(position);
                long index = adapter.getItemId(position);
                //Toast.makeText(getActivity(),index+"",Toast.LENGTH_SHORT).show();
                load_screen(index);

            }
        });

       // init();
        return view;
    }


    public void load_screen(long value) {

        Intent intent;
        if (value == 0) {
            intent = new Intent(getActivity(), Persondetail.class);
            startActivity(intent);
        } else if (value == 1) {
            intent = new Intent(getActivity(), Personreview.class);
            startActivity(intent);
        } else if (value == 2) {
            Toast.makeText(getActivity(),"Under development",Toast.LENGTH_SHORT).show();
        } else if (value == 3) {
            remove_values();
            getActivity().finish();
        } else {

        }

    }


    public void remove_values() {

        Paper.book().delete(Common.checklogin);
        Paper.book().delete(Common.login);

    }


//    public void init() {
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
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
//                           Common. longitude = location.getLongitude();
//                            Common.latitude = location.getLatitude();
//                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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





//    public void getlocation() {
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//
//                Location location = task.getResult();
//                if (location != null) {
//
//                    try {
//                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                        List<Address> list = geocoder.getFromLocation(
//                                location.getLatitude(), location.getLongitude(), 1
//                        );
//
//                        address = list.get(0).getAddressLine(0);
//                        Common.latitude = list.get(0).getLatitude();
//                        Common.longitude = list.get(0).getLongitude();
//                        City = list.get(0).getCountryName();
//
//                        Common.currentuser.setLocation(address);
//                        //  textView.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude:</b><br></font>" + list.get(0).getAddressLine(0)));
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//
//    }





}
