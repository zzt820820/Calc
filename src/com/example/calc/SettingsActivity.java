package com.example.calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingsActivity extends Activity implements OnClickListener {
	static final int STEP_MODE = 0;
	static final int STEP_TIME = 1;
	static final int STEP_ADD = 2;
	static final int STEP_SUB = 3;
	static final int STEP_MUL = 4;
	static final int STEP_MIX = 5;
	int mStep = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		ModeFragment frag = new ModeFragment();
		getFragmentManager().beginTransaction().replace(R.id.setting_frag, frag, "mode").commit();
		findViewById(R.id.next).setOnClickListener(this);
		mStep = 0;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(mStep) {
		case STEP_MODE:
			TimeFragment time_frag = new TimeFragment();
			getFragmentManager().beginTransaction().replace(R.id.setting_frag, time_frag, "time").commit();
			break;
		case STEP_TIME:
			AddFragment add_frag = new AddFragment();
			getFragmentManager().beginTransaction().replace(R.id.setting_frag, add_frag, "add").commit();
			break;
		case STEP_ADD:
			SubFragment sub_frag = new SubFragment();
			getFragmentManager().beginTransaction().replace(R.id.setting_frag, sub_frag, "sub").commit();
			break;
		case STEP_SUB:
			MulFragment mul_frag = new MulFragment();
			getFragmentManager().beginTransaction().replace(R.id.setting_frag, mul_frag, "mul").commit();
			break;
		case STEP_MUL:
			MixFragment mix_frag = new MixFragment();
			getFragmentManager().beginTransaction().replace(R.id.setting_frag, mix_frag, "mix").commit();
			break;
		case STEP_MIX:

			break;
		}
		mStep++;
	}

}
