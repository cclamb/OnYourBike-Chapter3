package com.androiddevbook.onyourbike.chapter5.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public final class Settings {
	private static final String CLASS_NAME = Settings.class.getSimpleName();
	private static final String VIBRATE = "vibrate";
	
	private boolean vibrateOn;
	
	public boolean isVibrateOn(Activity activity) {
		Log.d(CLASS_NAME, "returning vibrate");
		SharedPreferences preferences 
			= activity.getPreferences(Activity.MODE_PRIVATE);
		if (preferences.contains(VIBRATE)) {
			vibrateOn = preferences.getBoolean(VIBRATE, false);
		}
		return vibrateOn;
	}
	
	public void setVibrateOn(Activity activity, boolean vibrateOn) {
		Log.d(CLASS_NAME, "setting vibrate");
		activity.getPreferences(Activity.MODE_PRIVATE)
			.edit()
			.putBoolean(VIBRATE, vibrateOn)
			.apply();
		this.vibrateOn = vibrateOn;;
	}

}
