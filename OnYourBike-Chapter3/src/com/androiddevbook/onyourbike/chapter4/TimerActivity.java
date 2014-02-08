package com.androiddevbook.onyourbike.chapter4;

import com.androiddevbook.onyourbike.chapter4.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity {
	
	private static final String CLASS_NAME = TimerActivity.class.getSimpleName();
	private static final long UPDATE_INTERVAL = 200;
	
	protected class UpdateTimer implements Runnable {
		@Override
		public void run() {
			Log.d(UpdateTimer.class.getSimpleName(), "running timer.");
			setTimeDisplay();
			
			if (timerRunning) {
				vibrateCheck();
			}
			
			if (handler != null) {
				handler.postDelayed(this, UPDATE_INTERVAL);
			}
		}
	}
	
	protected TextView counter;
	protected Button start;
	protected Button stop;
	
	protected boolean timerRunning;
	protected long startedAt;
	protected long lastStopped;
	
	protected Handler handler;
	protected UpdateTimer updateTimer;
	
	protected Vibrator vibrate;
	protected long lastSeconds;
	
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
	        			.penaltyDeath()
	        			.build());
        }
        
    }
    
    
    @Override
    public void onStart() {
    	super.onStart();
    	Log.d(CLASS_NAME, "onStart");
    	if (timerRunning) {
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
    	setTimeDisplay();
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	Log.d(CLASS_NAME, "onStop");
    	if (timerRunning) {
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
    
    public void clickedStart(View view) {
    	Log.d(CLASS_NAME, "Start clicked.");
    	timerRunning = true;
    	enableButtons();
    	startedAt = System.currentTimeMillis();
    	setTimeDisplay();
    	handler = new Handler();
    	updateTimer = new UpdateTimer();
    	handler.postDelayed(updateTimer, UPDATE_INTERVAL);
    }
    
    public void clickedStop(View view) {
    	Log.d(CLASS_NAME, "Stop clicked.");
    	timerRunning = false;
    	enableButtons();
    	lastStopped = System.currentTimeMillis();
    	setTimeDisplay();
    	handler.removeCallbacks(updateTimer);
    	handler = null;
    }
    
    protected void enableButtons() {
    	Log.d(CLASS_NAME, "Setting buttons to enabled.");
    	start.setEnabled(!timerRunning);
    	stop.setEnabled(timerRunning);
    }
    
    protected void setTimeDisplay() {
    	String display;
    	long timeNow;
    	long diff;
    	long seconds;
    	long minutes;
    	long hours;
    	
    	Log.d(CLASS_NAME, "Setting the time display.");
    	
    	if (timerRunning) {
    		timeNow = System.currentTimeMillis();
    	} else {
    		timeNow = lastStopped;
    	}
    	
    	diff = timeNow - startedAt;
    	
    	if (diff < 0) {
    		diff = 0;
    	}
    	
    	seconds = diff /1000;
    	minutes = seconds / 60;
    	hours = minutes / 60;
    	seconds = seconds % 60;
    	minutes = minutes % 60;
    	
    	display = String.format("Timez, if you were a yeti: %d", hours) + ":"
    			+ String.format("%02d", minutes) + ":"
    			+ String.format("%02d", seconds);
    	
    	counter.setText(display);
    			
    	
    }
    
    protected void vibrateCheck() {
    	long timeNow = System.currentTimeMillis();
    	long diff = timeNow - startedAt;
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
