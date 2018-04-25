package com.example.calc;

import android.app.Application;

public class AppContext extends Application {

	static Settings mSettings;
	static DataManager mDataManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mSettings = new Settings();
		mDataManager = new DataManager(this);
		
	}

	static public Settings getSettings() {
		return mSettings;
	}
	
	static public DataManager getDataManager() {
		return mDataManager;
	}
}
