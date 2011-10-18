package example.HelloAndroid;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


public class HelloAndroidActivity extends Activity {
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
	          Log.d(TAG, "+++++++++++Location changed in first app: " + location.toString());
	        }
          public void onStatusChanged(String provider, int status, Bundle extras) {Log.d(TAG, "===========Status changed.");}

          public void onProviderEnabled(String provider) {Log.d(TAG, "========Provider is enabled.");}

          public void onProviderDisabled(String provider) { Log.d(TAG, "============ Provider is disabled.");}
          
          
        };
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        
        PackageManager pm = getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        Log.d(TAG, "=== list of installed apps ==== ");
        for (ApplicationInfo packageInfo : packages) {
        	Log.d(TAG, packageInfo.packageName);

        }// the getLaunchIntentForPackage returns an intent that you can use with startActivity() 
      }        
}
