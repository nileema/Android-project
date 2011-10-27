/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.skeletonapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
public class SkeletonActivity extends Activity {
	// Images declared in resources
	ImageView world, lt, violet;
	// View to hold inflated layout
	public static View customToastlayout;

	public SkeletonActivity() {
	  
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Create a full screen window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent i = new Intent();
        
        // Inflate our UI from its XML layout description.
        setContentView(R.layout.skeleton_activity);
        world = ((ImageView) findViewById(R.id.world_image));
        lt = ((ImageView) findViewById(R.id.lt_image));        
        world.setClickable(true);
        lt.setClickable(true);
        
        // Assign listeners to the image clicks
        world.setOnClickListener(mWorldListener);
        lt.setOnClickListener(mLTListener);
        lt.setVisibility(View.GONE);
        
        // Inflate the layout from the custom XML file. Used in the service. 
		LayoutInflater inflater = getLayoutInflater();
		customToastlayout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.llToast));

        /* ----------- START SERVICE ---------- */
        
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
        Log.d("nileema", "===================== after starting service");
        /* ------------------------------ */
       
     }

    /**
     * A call-backs for when the user clicks on the image.
     */
    OnClickListener mLTListener = new OnClickListener() {
        public void onClick(View v) {
        	lt.setVisibility(View.GONE);
        	world.setVisibility(View.VISIBLE);
        }
    };
    
    OnClickListener mWorldListener = new OnClickListener() {
        public void onClick(View v) {
        	world.setVisibility(View.GONE);
        	lt.setVisibility(View.VISIBLE);
        }
    };
    
    /**================================================================================================
     * Code below is not used by current implementation, but kept for reference 
     * as an example of how to receive an intent from the service. This is a working code. 
     * ================================================================================================
     */

    // Receives the specified intent and displays the toast notification. 
    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(TestService.CUSTOM_INTENT)) {
				
			  Toast toast = new Toast(getApplicationContext());
              // ======= Custom view for toast ===========  
          	  LayoutInflater inflater = getLayoutInflater();
        	  View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.llToast));
        	  ImageView image = (ImageView) layout.findViewById(R.id.tvImageToast);
        	  image.setImageResource(R.drawable.footprint);
        	  TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
        	  title.setText("You are being tracked!");
        	  
        	  toast.setGravity(Gravity.CENTER, 0, 0);
        	  toast.setDuration(Toast.LENGTH_SHORT);
        	  toast.setView(layout);
        	 // toast.show();
            }
        }
    };
    
    // Flag if receiver is registered 
    private boolean mReceiversRegistered = false;
    // Define a handler and a broadcast receiver
    private final Handler mHandler = new Handler();

    @Override
    protected void onResume() {
      super.onResume();

      // Register Sync Recievers
      IntentFilter intentToReceiveFilter = new IntentFilter();
      intentToReceiveFilter.addAction(TestService.CUSTOM_INTENT);
      this.registerReceiver(mIntentReceiver, intentToReceiveFilter, null, mHandler);
      mReceiversRegistered = true;
    }

    @Override
    public void onPause() {
      super.onPause();

      // Make sure you unregister your receivers when you pause your activity
      if(mReceiversRegistered) {
        unregisterReceiver(mIntentReceiver);
        mReceiversRegistered = false;
      }
    }

}
