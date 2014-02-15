package com.androiddevbook.onyourbike.chapter4;

import android.util.Log;

public final class Settings {
	private static final String CLASS_NAME = Settings.class.getSimpleName();
	
	private boolean vibrateOn;

	protected boolean isVibrateOn() {
		Log.d(CLASS_NAME, "returning vibrate");
		return vibrateOn;
	}

	protected void setVibrateOn(boolean vibrateOn) {
		Log.d(CLASS_NAME, "setting vibrate");
		this.vibrateOn = vibrateOn;
	}
}
