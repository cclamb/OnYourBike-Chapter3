package com.androiddevbook.onyourbike.chapter4;

import com.androiddevbook.onyourbike.chapter4.R;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class TimerActivity extends Activity {
	
	private static final String CLASS_NAME = TimerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        
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
    }
    
    public void clickedStop(View view) {
    	Log.d(CLASS_NAME, "Stop clicked.");
    }
    
}
