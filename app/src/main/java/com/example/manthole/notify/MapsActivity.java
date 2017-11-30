package com.example.manthole.notify;

import android.*;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.manthole.notify.R.id.map;

public class MapsActivity extends FragmentActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        ResultCallback<Status> {


    android.support.v4.app.NotificationCompat.Builder builder;

    protected static final int REQUEST_CHECK_IN_SETTIGNS = 0x1;
    GoogleApiClient googleApiClient;
    private MapFragment mapFragment;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Location lastlocation;
    Location mylocation;

    GeoLocations geoLocations;
    //    LocationRequest myLocationRequest;
    //    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
//    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView textLat, textLong;
    Circle geoFenceLimitscodeTribeTembisa, geoFenceLimitsEwcCanteen, geoFenceLimitsGermistonStadium, geoFenceLimitsGoldenWalkGermiston;
    int x = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
//
        mapFragment.getMapAsync(this);

        textLat = (TextView) findViewById(R.id.mylat);

        textLong = (TextView) findViewById(R.id.mylon);


        initGMaps();
        createGoogleApi();


    }

    private void initGMaps() {

//        mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

    }

    private void createGoogleApi() {

        Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();


        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }

            //googleApitClient.disconnect();
            Log.i(TAG, "onConnected()");
            getLastKnownLocation();
            //recoverGeofenceMarker();
//            startGeofence();


        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.w(TAG, "onConnectionFailed()");

    }

    @Override
    public void onResult(@NonNull Status status) {


        Log.i(TAG, "onResult: " + status);
        if (status.isSuccess()) {
//            saveGeofence();
            //drawGeofence();

        } else {

            //inform about fail
        }

    }

    @Override
    public void onLocationChanged(Location location) {


        Log.d(TAG, "onLocationChanged [" + location + "]");
        lastlocation = location;
        writeActualLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onStart();
        googleApiClient.connect();
        settingsrequest();

    }

    public void settingsrequest() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);//This is the key ingredient

        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {

                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {

                    case LocationSettingsStatusCodes.SUCCESS:
                        //All location settings are satisfied.The client can initialise location
                        //requests here
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //Location settings are not satisfied. But could be fixed by showing the user
                        //a dialog.
                        try {
                            //Show the dialog by calling startResolutionForResult(),
                            //and check the result in on ActivityResult().
                            status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_IN_SETTIGNS);
                        } catch (IntentSender.SendIntentException e) {

                            //Ignore the error
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Location settings are not satisfied. However, we have no way to fix the
                        //settings so we wont show the dialog.
                        break;

                }

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

//        myLocationRequest = LocationRequest.create();
//        myLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        myLocationRequest.setInterval(1000);

        mMap = googleMap;

        // Add a marker in code Tribe Class and move the camera
        final LatLng codeTribeTembisa = new LatLng(-26.0238525, 28.1848285);
        mMap.addMarker(new MarkerOptions().position(codeTribeTembisa).title("Code Tribe Class"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(codeTribeTembisa));

        // Add a marker in EWC Canteen and move the camera
        final LatLng EwcCanteen = new LatLng(-26.0244727, 28.1856729);
        mMap.addMarker(new MarkerOptions().position(EwcCanteen).title("Ewc Canteen"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(EwcCanteen));

        // Add a marker in Ewc Halland move the camera
        final LatLng EwcHall = new LatLng(-26.0243767, 28.1847553);
        mMap.addMarker(new MarkerOptions().position(EwcHall).title("Ewc Hall"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(EwcHall));


        // Add a marker in GermistonStadium move the camera
        final LatLng GermistonStadium = new LatLng(-26.2233924398, 28.1715759804);
        mMap.addMarker(new MarkerOptions().position(GermistonStadium).title("Germiston Stadium"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(GermistonStadium));

        //Shopping Malls

        //Birch Acres

        // Add a marker in BirchAcresMall and move the camera
        final LatLng BirchAcresMall = new LatLng(-26.027404, 28.189414);
        mMap.addMarker(new MarkerOptions().position(BirchAcresMall).title("Birch Acres Mall"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(BirchAcresMall));


        // Add a marker in goldenWalkGermiston and move the camera
        final LatLng goldenWalkGermiston = new LatLng(-26.2123, 28.1624);
        mMap.addMarker(new MarkerOptions().position(goldenWalkGermiston).title("Golden Walk Germiston"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(goldenWalkGermiston));


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

            }


        }


        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Marker
        // ... get a map.
        // Add a circle in codeTribeTembisa
        if (geoFenceLimitscodeTribeTembisa != null)
            geoFenceLimitscodeTribeTembisa.remove();

        geoFenceLimitscodeTribeTembisa = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-26.0238525, 28.1848285))
                .radius(40.0f)
                .strokeColor(Color.RED)
                .fillColor(Color.YELLOW));

        // Add a circle in EWCCanteen
        if (geoFenceLimitsEwcCanteen != null)
            geoFenceLimitsEwcCanteen.remove();
        geoFenceLimitsEwcCanteen = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-26.0244727, 28.1856729))
                .radius(40.0f)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
//

        // Add a circle in GermistonStadium
        if (geoFenceLimitsGermistonStadium != null)
            geoFenceLimitsGermistonStadium.remove();
        geoFenceLimitsGermistonStadium = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-26.2233924398, 28.1715759804))
                .radius(40.0f)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        // Add a circle in goldenWalkGermiston
        if (geoFenceLimitsGoldenWalkGermiston != null)
            geoFenceLimitsGoldenWalkGermiston.remove();
        geoFenceLimitsGoldenWalkGermiston = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-26.2123, 28.1624))
                .radius(40.0f)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

//        //Shopping Mallsש
//
//        //Birch Acres Mall
//
//        // Add a circle in Birch Acres Mall
//        geoFenceLimits = mMap.addCircle(new CircleOptions()
//                .center(new LatLng(-26.027404, 28.189414))
//                .radius(15.0f)
//                .strokeColor(Color.RED)
//                .fillColor(Color.YELLOW));
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                float[] distance = new float[2];


                location.distanceBetween(location.getLatitude(), location.getLongitude(),
                        geoFenceLimitscodeTribeTembisa.getCenter().latitude, geoFenceLimitscodeTribeTembisa.getCenter().longitude, distance);

                geoLocations = new GeoLocations(getApplicationContext());


                if (mylocation == null) {
                    mylocation = location;
                    if (distance[0] < geoFenceLimitscodeTribeTembisa.getRadius()) {

                        geoLocations.EnterCodetribeTembisa();

                    } else if (geoFenceLimitsEwcCanteen == null) {

                        geoLocations.EnterEwcCanteen();

                    } else {

                        goeFenceFree();
                    }
                }
            }


        });

    }

    public void goeFenceFree() {


        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notify Notification")
                        .setContentText("No Geofence Detected")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(getApplicationContext(), Chat.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent buttonIntent = new Intent(getBaseContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getBaseContext(), 0, buttonIntent, 0);

        builder.addAction(android.R.drawable.ic_menu_view, "VIEW", pendingIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void buildNotification(int NOTIFICATION_ID) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }


    private final int REQ_PERMISSION = 999;

    //Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        //Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    //Ask for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );

    }

    private void getLastKnownLocation() {

        Log.d(TAG, "getLastKnownLocation()");
        if (checkPermission()) {
            lastlocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastlocation != null) {
                Log.i(TAG, "LAsKnown location. " +
                        "Long: " + lastlocation.getLongitude() +
                        " | Lat: " + lastlocation.getLatitude());

                writeLastLocation();
//                startLocationUpdates();
            } else {

                Log.w(TAG, "No location retrieved yet");
//                startLocationUpdates();
            }

        } else askPermission();
    }

    private void writeActualLocation(Location location) {

        //text.Lat.set("Lat: " + location.getLatitude());
        //textLong.setText("Long: " + location.getLongitude());
        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));

    }

    private void writeLastLocation() {

        writeActualLocation(lastlocation);
    }

    private Marker locationMarker;

    private void markerLocation(LatLng latlong) {
        Log.i(TAG, "markerLocation(" + latlong + ")");
        String title = latlong.latitude + "," + latlong.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latlong)
                .title(title);
        if (mMap == null) {
            if (locationMarker != null)
                locationMarker.remove();
            locationMarker = mMap.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlong, zoom);
            mMap.animateCamera(cameraUpdate);

        }


    }

}

//

//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle(" WELCOME TO CODE TRIBE TEMBISA");
//                    builder.setPositiveButton("GO BACK TO MAP", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//                            startActivity(intent);
//
//                        }
//                    });
//                    builder.setNegativeButton("PIN LOCATION", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    builder.create();
//                    builder.show();

