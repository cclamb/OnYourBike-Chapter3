package com.androiddevbook.onyourbike.chapter4;

import android.app.Application;
import android.util.Log;

public final class OnYourBike extends Application {
	private static final String CLASS_NAME = OnYourBike.class.getSimpleName();
	
	private Settings settings;

	public Settings getSettings() {
		Log.d(CLASS_NAME, "getting settings");
		if (settings == null ) {
			Log.d(CLASS_NAME, "returning settings");
			settings = new Settings();
		}
		return settings;
	}

	public void setSettings(Settings settings) {
		Log.d(CLASS_NAME, "setting settings");
		this.settings = settings;
	}
	
}
