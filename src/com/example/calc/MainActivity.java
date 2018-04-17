package com.example.calc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_set);
		findViewById(R.id.choice_profile).setOnClickListener(this);
		findViewById(R.id.new_profile).setOnClickListener(this);
		findViewById(R.id.query_paper).setOnClickListener(this);
		//SettingFragment frag = new SettingFragment();
		//getFragmentManager().beginTransaction().replace(R.id.setting_frag, frag, "menu").commit();
		//this.findViewById(R.id.next).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch(view.getId()) {
		case R.id.choice_profile:
			break;
		case R.id.new_profile:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.query_paper:
			break;
		}
	}
}