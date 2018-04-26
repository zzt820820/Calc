package com.example.calc;

import android.app.Application;

public class AppContext extends Application {

	@Override
	public void onTerminate() {
		mDataManager.close();
		super.onTerminate();
	}

	static Settings mSettings;
	static DataManager mDataManager;

	@Override
	public void onCreate() {
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
