package com.drivojoy.drivohelper.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.drivojoy.drivohelper.R;
import com.drivojoy.drivohelper.common.AppCommonValues;
import com.drivojoy.drivohelper.services.GeofenceTransitionsIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 10/29/2016.
 */
public class MapActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    @BindView(R.id.txtLat) TextView txtLat;
    @BindView(R.id.txtLong) TextView txtLong;

    GoogleMap googleMap;
    MapFragment googleMapFragment;
    double latitude, longitude;
    Marker mCurrLocationMarker;

    Geofence geoFence;
    double geoLat = 0.0, geoLong = 0.0;
    final String GEOFENCE_ID = "My GeoFence";
    PendingIntent mGeofencePendingIntent = null;

    final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    boolean wasPaused = false;

    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    static int UPDATE_INTERVAL = 1000;
    static int FATEST_INTERVAL = 500;
    static int DISPLACEMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if(AppCommonValues.isNetworkAvailable(MapActivity.this)) {
            initMap();
            /* check, if play service is available or not */
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
            }
        } else {
            AppCommonValues.showCommonErrorDialog(MapActivity.this, AppCommonValues.NO_NETWORK);
        }
    }

    private void initMap(){
        if (googleMap == null) {
            googleMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            /* set callback */
            googleMapFragment.getMapAsync(this);
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * @task gets and displays location lat lang data
     */
    private void displayLocation(){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            if(geoLat == 0.0 && geoLong == 0.0) {
                /* first time call, so create a geofence now */
                geoLat = latitude;
                geoLong = longitude;
                createAGeoFence();
            }
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            updateMarker();
            txtLat.setText("Lat  " + latitude);
            txtLong.setText("Long  " + longitude);
        } else {
            AppCommonValues.showCommonErrorDialog(MapActivity.this, AppCommonValues.LOCATION_DISABLED);
        }
    }

    /**
     * @task updates the marker on map
     */
    private void updateMarker(){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.title("You are here");
        markerOptions.snippet("Dude, this is where you are right now..\n@Lat: " + latitude + "    Long: " + longitude);
        markerOptions.anchor(1.0f, 1.0f);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = googleMap.addMarker(markerOptions);
    }

    /**
     * @task creates a geofence
     */
    private void createAGeoFence() {
        Log.e("geo", "inside geofence " + geoLat + "   " + geoLong);
        geoFence = new Geofence.Builder()
                .setRequestId(GEOFENCE_ID)
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT )
                .setCircularRegion(geoLat, geoLong, AppCommonValues.GEO_FENCE_RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
        addCircleOnGeoFence();
    }

    /**
     * @task adds a circle around the geofence
     */
    private void addCircleOnGeoFence(){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(geoLat, geoLong))
                .fillColor(Color.argb(0x55, 0x00, 0x00, 0xff))
                .strokeColor(Color.argb(0xaa, 0x00, 0x00, 0xff))
                .strokeWidth(2.0f)
                .radius(AppCommonValues.GEO_FENCE_RADIUS);
        googleMap.addCircle(circleOptions);
    }

    private void setUpIntents(){
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        mGeofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingResult<Status> result = LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,
                getGeofencingRequest(), mGeofencePendingIntent);
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                String toastMessage;
                if (status.isSuccess()) {
                    toastMessage = "Success: geofence service running";
                } else {
                    toastMessage = "Error: geofence not being checked";
                }
                Toast.makeText(MapActivity.this, toastMessage, 400).show();
            }
        });
    }

    /**
     * @task creates a geofence request object
     * @return returns a GeofencingRequest object
     */
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(geoFence);
        return builder.build();
    }

    /**
     * @task check whtther google play services is available on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
        if(wasPaused) {
            wasPaused = false;
            if(mGoogleApiClient.isConnected()){
                startLocationUpdates();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasPaused = true;
        stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * @task creates google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * @task creates location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * @task starts the location updates
     */
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    /**
     * @task stops location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public PendingIntent getRequestPendingIntent() {
        return createRequestPendingIntent();
    }

    private PendingIntent createRequestPendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        } else {
            Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
            intent.setAction("geofence_transition_action");
            return PendingIntent.getBroadcast(this, R.id.geofence_transition_intent, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        displayLocation();
        startLocationUpdates();
        mGeofencePendingIntent = getRequestPendingIntent();
        setUpIntents();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(MapActivity.this, "Connection failed for reason: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * @task instead of 0, 0 for lat and long set a default latitude longitude for focusing
     */
    private void initialPositionOnMap(){
        Log.e("here", "here in india");
        LatLng indiaLatLng = new LatLng(AppCommonValues.INDIA_LAT, AppCommonValues.INDIA_LONG);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(indiaLatLng, AppCommonValues.ZOOM_LEVEL);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("ready", "map ready");
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setMyLocationEnabled(false);
        initialPositionOnMap();
    }
}
