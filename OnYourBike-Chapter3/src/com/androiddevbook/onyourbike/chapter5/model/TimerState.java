package com.androiddevbook.onyourbike.chapter5.model;

import android.util.Log;

import com.androiddevbook.onyourbike.chapter5.activities.TimerActivity;

public final class TimerState {
	
	public static String CLASS_NAME;
	
	private boolean timerRunning;
	private long startedAt;
	private long lastStopped;
	
	public TimerState() {
		CLASS_NAME = getClass().getSimpleName();
	}
	
	public void start() {
		timerRunning = true;
		startedAt = System.currentTimeMillis();
	}
	
	public void stop() {
		timerRunning = false;
		lastStopped = System.currentTimeMillis();
	}
	
	public boolean isRunning() {
		return timerRunning;
	}

	public String display() {
		String display;
		long diff = elapsedTime();
		long seconds;
		long minutes;
		long hours;
		
		Log.d(TimerActivity.CLASS_NAME, "Setting the time display.");
		
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
		
		//timerActivity.counter.setText(display);
		return display;	
		
	}
	
	public long elapsedTime() {
		long timeNow = isRunning() ? System.currentTimeMillis() : lastStopped;
		return timeNow - startedAt;
	}
}
