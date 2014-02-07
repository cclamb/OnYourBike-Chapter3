package com.androiddevbook.onyourbike.chapter4;

import com.androiddevbook.onyourbike.chapter4.R;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity {
	
	private static final String CLASS_NAME = TimerActivity.class.getSimpleName();
	
	protected TextView counter;
	protected Button start;
	protected Button stop;
	
	protected boolean timerRunning;
	protected long startedAt;
	protected long lastStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
    
    public void clickedStop(View view) {
    	Log.d(CLASS_NAME, "Stop clicked.");
    	timerRunning = false;
    	enableButtons();
    	lastStopped = System.currentTimeMillis();
    	setTimeDisplay();
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
    	
    	diff = timeNow = startedAt;
    	
    	if (diff < 0) {
    		diff = 0;
    	}
    	
    	seconds = diff /1000;
    	minutes = seconds / 60;
    	hours = minutes / 60;
    	seconds = seconds % 60;
    	minutes = minutes % 60;
    	
    	display = String.format("%d", hours) + ":"
    			+ String.format("%02d", minutes) + ":"
    			+ String.format("%02d", seconds);
    	
    	counter.setText(display);
    			
    	
    }
    
}
