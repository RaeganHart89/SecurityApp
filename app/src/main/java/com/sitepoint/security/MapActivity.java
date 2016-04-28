package com.sitepoint.security;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.security.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Firebase mRef;
    private String mUserId;
    private String itemsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Check Authentication
        mRef = new Firebase(Constants.FIREBASE_URL);
        if (mRef.getAuth() == null) {
            loadLoginView();
        }

        try {
            mUserId = mRef.getAuth().getUid();
        } catch (Exception e) {
            loadLoginView();
        }

        itemsUrl = Constants.FIREBASE_URL + "/users/" + mUserId + "/items";

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
//        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        CameraPosition Frome = CameraPosition.builder()
                .target(new LatLng(51.232391, -2.319834))
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();

        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.231846, -2.321810))
                .title("Security Office")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.232104, -2.321914))
                .title("The Cordero Lounge")
                .snippet("Day security only"));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.231635, -2.320533))
                .title("The Blue Boar")
                .snippet("Security from 21:00"));


        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.230633, -2.321732))
                .title("The Wheatsheaves")
                .snippet("Security from 21:00"));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.231975, -2.325498))
                .title("The Lamb and Fountain")
                .snippet("Security from 21:00"));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.228817, -2.322405))
                .title("The Cornerhouse")
                .snippet("Security on request"));

        map.setMyLocationEnabled(true);

        map.animateCamera(CameraUpdateFactory.newCameraPosition(Frome), 10000, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mRef.unauth();
            loadLoginView();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}