package com.example.userr.remindme.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.userr.remindme.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnCameraIdleListener {

    public static final int Request_Permission_code = 123;
    public static int REQUEST_CODE_ACTIVITY_B = 222;
    double MyLatituide, MyLongituide;
    SupportMapFragment mapFragment;
    @BindView(R.id.done_map_btn)
    Button done_btn;
    String address = null;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        init_map_after_permission();
    }

    public void init_map() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMyLocationEnabled(true);

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onMyLocationChange(Location location) {

                        MyLatituide = location.getLatitude();
                        MyLongituide = location.getLongitude();
                        mMap.setMyLocationEnabled(false);
                        LatLng myCurrentLocation = new LatLng(MyLatituide, MyLongituide);
                        // mMap.addMarker(new MarkerOptions().position(myCurrentLocation).title(""+MyLatituide+","+MyLongituide));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation, 16));


                        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                            @Override
                            public void onCameraMoveStarted(int i) {
                                mMap.clear();
                            }
                        });
                        googleMap.setOnMyLocationClickListener(null);
                    }
                });

                mMap.setOnMapClickListener(MapActivity.this);
                mMap.setOnCameraIdleListener(MapActivity.this);


            }
        });
    }

    public void init_map_after_permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Request_Permission_code);
            } else {
                //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
                init_map();
            }
        } else {
            init_map();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {


    }

    @Override
    public void onCameraIdle() {
        mMap.clear();


        LatLng midLatLng = mMap.getCameraPosition().target;
        MyLatituide = midLatLng.latitude;
        MyLongituide = midLatLng.longitude;
    }

    public void done_btn_click(View view) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(MyLatituide, MyLongituide, 1);
        address = addresses.get(0).getAddressLine(0);
        Intent intent = new Intent();
        ArrayList arrayList = new ArrayList();
        arrayList.add(MyLatituide);
        arrayList.add(MyLongituide);
        arrayList.add(address);
        intent.putStringArrayListExtra("map_list",arrayList);
        setResult(111, intent);
        finish();
    }

    @Override
    protected void onResume() {
        mapFragment.onResume();
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        mapFragment.onLowMemory();
        super.onLowMemory();
    }


    @Override
    protected void onPause() {
        mapFragment.onPause();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        init_map();

        switch (requestCode) {
            case Request_Permission_code: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }


    }

}

