package com.example.android.skeletonapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TestService extends IntentService {

  /** 
   * A constructor is required, and must call the super IntentService(String)
   * constructor with a name for the worker thread.
   */
  public TestService() {
      super("HelloIntentService");
  }
  private static Context context;

  /**
   * The IntentService calls this method from the default worker thread with
   * the intent that started the service. When this method returns, IntentService
   * stops the service, as appropriate.
   */
  @Override
  protected void onHandleIntent(Intent intent) {
      // Normally we would do some work here, like download a file.
      // For our sample, we just sleep for 5 seconds.
      long endTime = System.currentTimeMillis() + 5*1000;
      while (System.currentTimeMillis() < endTime) {
          synchronized (this) {
              try {
                  wait(endTime - System.currentTimeMillis());
              } catch (Exception e) {
              }
          }
      }
  }
  
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
	  context = getApplicationContext();
	  Log.e("Service Example", "======================== Service Started.. ");
	  Toast.makeText(context, "Nileema: service started", Toast.LENGTH_SHORT).show();

	  // Start the AsyncTask
	  new ShowImage().execute("Hello");
	  
      return super.onStartCommand(intent,flags,startId);
  }
  
  void doServiceWork() {
	  Log.d("nileema", "========= in do service work");
	  Context context = getApplicationContext();
	  CharSequence text = "Nileema : service started";
	  int duration = Toast.LENGTH_SHORT;

	  Toast toast = Toast.makeText(context, text, duration);
	  toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
	  toast.show();

  }
  
  /**
   * Async task 
   */
  private class ShowImage extends AsyncTask<String, Integer, String> {

      @Override
      protected String doInBackground(String... _url) {
          try {
        	  while (true) {
        		  Thread.sleep(5000);
        		  publishProgress(1);
        	  }
          } catch (Exception e) {
          }
          return null;
      }

      @Override
      protected void onProgressUpdate(Integer... progress) {
    	  // ------------ Display toast according to custom layout --------
    	  Toast toast = new Toast(getApplicationContext());    	  
    	  ImageView image = (ImageView) SkeletonActivity.customToastlayout.findViewById(R.id.tvImageToast);
    	  image.setImageResource(R.drawable.lt);
    	  TextView title = (TextView) SkeletonActivity.customToastlayout.findViewById(R.id.tvTitleToast);
    	  title.setText("You are being tracked!!!");
    	  
    	  toast.setGravity(Gravity.CENTER, 12, 40);
    	  toast.setDuration(Toast.LENGTH_SHORT);
    	  toast.setView(SkeletonActivity.customToastlayout);    	  
    	  toast.show();
    	  // ---------------------

    	  // -------- play sound --------
//    	  SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//    	  int soundID = soundPool.load(context, R.raw.ping, 1);
//    	  AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//    	  float actualVolume = (float) audioManager
//					.getStreamVolume(AudioManager.STREAM_MUSIC);
//    	  soundPool.play(soundID, actualVolume, actualVolume, 1, 0, 1f);
    	  //-----------------------------
          Intent i = new Intent();
          i.setAction(CUSTOM_INTENT);
          i.setFlags(progress[0]);
          context.sendBroadcast(i);
      }
  }
  public static final String CUSTOM_INTENT = "test.android.TestService.ProgressReceiver";
}
