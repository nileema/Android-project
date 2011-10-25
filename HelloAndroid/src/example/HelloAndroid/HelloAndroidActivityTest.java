package example.HelloAndroid;

import java.util.Iterator;
import java.util.List;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class HelloAndroidActivityTest extends Activity {
	/** Called when the activity is first created. */
	private static final String TAG = "HeloWorldActivity";
	Activity a = this;
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
	          Log.d(TAG, "*************" + a.getParent() + getCallingActivity());
	          
	        }
          public void onStatusChanged(String provider, int status, Bundle extras) {Log.d(TAG, "===========Status changed.");}

          public void onProviderEnabled(String provider) {Log.d(TAG, "========Provider is enabled.");}

          public void onProviderDisabled(String provider) { Log.d(TAG, "============ Provider is disabled.");}
          
          
        };
        Log.d(TAG, "========== this classs is: " + this.getClass());
        Log.d(TAG, "************" + locationListener.getClass()+ "    " +
        		locationListener.getClass().getEnclosingClass() + "    " + locationListener.getClass().getEnclosingClass().getEnclosingClass());
        locationListener.getClass().getPackage();
        
        
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        
        PackageManager pm = getPackageManager();
        
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        Log.d(TAG, "=== list of installed apps ==== ");
        for (ApplicationInfo packageInfo : packages) {
        	Drawable d = packageInfo.loadIcon(pm);
        	Log.d(TAG, packageInfo.packageName + "_____" + packageInfo.loadLabel(pm));

        }// the getLaunchIntentForPackage returns an intent that you can use with startActivity() 
        
        Log.d(TAG, "=== list of running activities ==== ");
        ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while(i.hasNext()) {
          ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
          try {
            CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
            Log.d("nileema", c.toString());
//            Drawable d = pm.getApplicationIcon(info.processName);
//            ImageView iv = (ImageView) findViewById(R.id.footprint_image);
//            iv.setImageDrawable(d);
//            iv.setVisibility(View.VISIBLE);
            
          }catch(Exception e) {
            //Name Not FOund Exception
          }
        }
      }        
}
