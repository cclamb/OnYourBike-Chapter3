package com.androiddevbook.onyourbike.chapter4;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.CheckBox;

public class SettingsActivity extends Activity {
	
	private CheckBox vibrate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		vibrate = (CheckBox) findViewById(R.id.vibrate_checkbox);
		Settings settings = ((OnYourBike) getApplication())
				.getSettings();
		vibrate.setChecked(settings.isVibrateOn());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Settings settings = ((OnYourBike) getApplication())
				.getSettings();
		settings.setVibrateOn(vibrate.isChecked());
	}

}
