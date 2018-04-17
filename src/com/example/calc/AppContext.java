package com.example.calc;

import android.app.Application;

public class AppContext extends Application {

	static Settings mSettings;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	static public Settings getSettings() {
		if(mSettings == null) mSettings = new Settings();
		return mSettings;
	}
}
