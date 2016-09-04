package com.sandeep.sthapit.maps;

/**
 * Created by sandeep on 8/27/16.
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsWithRoute extends FragmentActivity implements LocationListener {

    GoogleMap googleMap;
    ArrayList<LatLng> markerPoints;
    Marker marker;
    int marker_count;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markerPoints = new ArrayList<LatLng>();

        Intent intent = getIntent();

        ArrayList<LatLng> locations = intent.getParcelableArrayListExtra("locations");
        markerPoints = locations;
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        setContentView(R.layout.activity_main);
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {
            onLocationChanged(location);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        marker_count = 0;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                                            @Override
                                            public void onMapClick(LatLng point) {
                                                CameraUpdate center =
                                                        CameraUpdateFactory.newLatLng(markerPoints.get(marker_count));
                                                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                                                googleMap.moveCamera(center);
                                                googleMap.animateCamera(zoom);
                                                int length_marker = markerPoints.size();
                                                RouteMapActivity r = new RouteMapActivity();
                                                for (marker_count = 0; marker_count < (length_marker - 2); marker_count++) {
                                                    r.drawRoute(googleMap, MapsWithRoute.this, markerPoints.get(marker_count), markerPoints.get(marker_count + 1), "en");
                                                }
                                            }

                                        }

        );

    }

    @Override
    public void onLocationChanged(Location location) {
        TextView locationName = (TextView) findViewById(R.id.tv_location_name);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        marker = googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                String _Location = listAddresses.get(0).getAddressLine(0);
                Toast.makeText(MapsWithRoute.this, _Location, Toast.LENGTH_SHORT).show();
                locationName.setText(_Location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sandeep.sthapit.maps/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sandeep.sthapit.maps/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}