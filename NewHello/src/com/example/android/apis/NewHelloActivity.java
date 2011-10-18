package com.example.android.apis;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class NewHelloActivity extends Activity {
    /** Called when the activity is first created. */
	private static final String TAG = "HeloWorldActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Acquire a reference to the system Location Manager
        final LocationManager locationManager = (LocationManager) this.getSystemService(
        								   Context.LOCATION_SERVICE);
        
       // Location new_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       // Log.d(TAG, "+++++++++++++ New location is : " + new_loc.toString());
        
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
	        public void onLocationChanged(Location location) {
	          Log.d(TAG, "^^^^^^^^^^^^^ Location changed in new app: " + location.toString());
	        }
          public void onStatusChanged(String provider, int status, Bundle extras) {
        	  Log.d(TAG, "^^^^^^^^ Status changed of " + provider + " changed. New status is : " + status);
          }

          public void onProviderEnabled(String provider) {Log.d(TAG, "^^^^^^^^^ " + provider + " is enabled.");}

          public void onProviderDisabled(String provider) { Log.d(TAG, "^^^^^^^^ " + provider + " is disabled.");}
        };
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }
}