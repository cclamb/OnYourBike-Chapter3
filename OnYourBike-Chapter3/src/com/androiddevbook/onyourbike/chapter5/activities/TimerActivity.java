package com.androiddevbook.onyourbike.chapter5.activities;

import com.androiddevbook.onyourbike.chapter4.BuildConfig;
import com.androiddevbook.onyourbike.chapter4.R;
import com.androiddevbook.onyourbike.chapter5.model.TimerState;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public final class TimerActivity extends Activity {
	
	public static final String CLASS_NAME = TimerActivity.class.getSimpleName();
	private static final long UPDATE_INTERVAL = 200;
	
	private final TimerState timer = new TimerState();
	
	private class UpdateTimer implements Runnable {
		@Override
		public void run() {
			Log.d(UpdateTimer.class.getSimpleName(), "running timer.");
			counter.setText(timer.display());
			if (timer.isRunning()) {
				vibrateCheck();
			}
			
			if (handler != null) {
				handler.postDelayed(this, UPDATE_INTERVAL);
			}
		}
	}
	
	public TextView counter;
	private Button start;
	private Button stop;
	
	private Handler handler;
	private UpdateTimer updateTimer;
	
	private Vibrator vibrate;
	private long lastSeconds;
	
	private Intent settingsIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(CLASS_NAME, "onCreate");
        setContentView(R.layout.activity_timer);
        
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);
        
        enableButtons();
        
        if (BuildConfig.DEBUG) {
        	StrictMode.setThreadPolicy(
        			new StrictMode
        				.ThreadPolicy
        				.Builder()
        				.detectAll()
        				.penaltyLog()
        				.build());
        	StrictMode.setVmPolicy(
        			new StrictMode
	        			.VmPolicy
	        			.Builder()
	        			.detectAll()
	        			.penaltyLog()
	        			.build());
        }
        
    }
    
    
    @Override
    public void onStart() {
    	super.onStart();
    	Log.d(CLASS_NAME, "onStart");
    	if (timer.isRunning()) {
    		handler = new Handler();
    		updateTimer = new UpdateTimer();
    		handler.postDelayed(updateTimer, UPDATE_INTERVAL);
    	}
    	
    	vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.d(CLASS_NAME, "onPause");
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Log.d(CLASS_NAME, "onResume");
    	enableButtons();
    	counter.setText(timer.display());
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	Log.d(CLASS_NAME, "onStop");
    	if (timer.isRunning()) {
    		handler.removeCallbacks(updateTimer);
    		updateTimer = null;
    		handler = null;
    	}
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.d(CLASS_NAME, "onDestroy");
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    	Log.d(CLASS_NAME, "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void clickedSettings(View view) {
    	Log.d(CLASS_NAME, "clicked settings");
		settingsIntent = new Intent(
			getApplicationContext(),
			SettingsActivity.class);
    	startActivity(settingsIntent);
    }
    
    public void clickedStart(View view) {
    	Log.d(CLASS_NAME, "Start clicked.");
    	timer.start();
    	enableButtons();
    	counter.setText(timer.display());
    	handler = new Handler();
    	updateTimer = new UpdateTimer();
    	handler.postDelayed(updateTimer, UPDATE_INTERVAL);
    }
    
    public void clickedStop(View view) {
    	Log.d(CLASS_NAME, "Stop clicked.");
    	timer.stop();
    	enableButtons();
    	counter.setText(timer.display());
    	handler.removeCallbacks(updateTimer);
    	handler = null;
    }
    
    protected void enableButtons() {
    	Log.d(CLASS_NAME, "Setting buttons to enabled.");
    	start.setEnabled(!timer.isRunning());
    	stop.setEnabled(timer.isRunning());
    }
    
    protected void vibrateCheck() {
    	long diff = timer.elapsedTime();
    	long seconds = diff / 1000;
    	long minutes = seconds / 60;
    	
    	Log.d(CLASS_NAME, "vibrateCheck");
    	
    	seconds = seconds % 60;
    	minutes = minutes % 60;
    	
    	if (vibrate != null 
    			&& seconds == 0 
    			&& seconds != lastSeconds) {
    		
    		long[] once = {0, 100};
    		long[] twice = {0, 100, 400, 100};
    		long[] thrice = {0, 100, 400, 100, 400, 100};
    		
    		// every hour
    		if (minutes == 0) {
    			Log.i(CLASS_NAME, "on the hour");
    			vibrate.vibrate(thrice, -1);
    		} else if (minutes % 15 == 0) { // every 15 minutes
    			Log.i(CLASS_NAME, "on the quarter hour"); 
    			vibrate.vibrate(twice, -1);
    		} else if (minutes % 5 == 0) { // every 5 minutes
    			Log.i(CLASS_NAME, "on the five");
    			vibrate.vibrate(once, -1);
    		} else if (minutes % 1 == 0) {
    			Log.i(CLASS_NAME, "on the one");
    			vibrate.vibrate(once, -1);
    		}
    		
    	}
    	
    	lastSeconds = seconds;
    	
    }
    
}
