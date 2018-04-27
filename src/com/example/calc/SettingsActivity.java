package com.example.calc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
	String mMode = "new";
	
	public String getMode() {
		return mMode;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		if(mMode.equals("new")) {
			DataManager dm = AppContext.getDataManager();
			if(dm != null) {
				Settings set = AppContext.getSettings();
				if(set != null) {
					Settings saved = dm.get_profile_by_name("");
					if(saved != null) {
						set.load(saved);
					}
				}
			}
		}
		ModeFragment frag = new ModeFragment();
		getFragmentManager().beginTransaction().replace(R.id.setting_frag, frag, "mode").commit();
		findViewById(R.id.next).setOnClickListener(this);
		mStep = 0;
		Intent intent = getIntent();
		if(intent != null) {
			String mode = intent.getStringExtra("mode");
			if(mode != null) {
				mMode = mode; 
			}
		}

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
		Fragment frag; 
		ModeFragment mode_frag;
		TimeFragment time_frag;
		AddFragment add_frag;
		SubFragment sub_frag;
		MulFragment mul_frag;
		MixFragment mix_frag;
		boolean checkok = false;
		
		switch(mStep) {
		case STEP_MODE:
			frag = getFragmentManager().findFragmentByTag("mode");
			if(frag != null && frag instanceof ModeFragment) {
				mode_frag = (ModeFragment)frag;
				if(mode_frag.check()) {
					checkok = true;
					time_frag = new TimeFragment();
					getFragmentManager().beginTransaction().replace(R.id.setting_frag, time_frag, "time").commit();
				}
			}
			
			break;
		case STEP_TIME:
			frag = getFragmentManager().findFragmentByTag("time");
			if(frag != null && frag instanceof TimeFragment) {
				time_frag = (TimeFragment)frag;
				if(time_frag.check()) {
					checkok = true;
					add_frag = new AddFragment();
					getFragmentManager().beginTransaction().replace(R.id.setting_frag, add_frag, "add").commit();
				}
			}
			break;
		case STEP_ADD:
			frag = getFragmentManager().findFragmentByTag("add");
			if(frag != null && frag instanceof AddFragment) {
				add_frag = (AddFragment)frag;
				if(add_frag.check()) {
					checkok = true;
					sub_frag = new SubFragment();
					getFragmentManager().beginTransaction().replace(R.id.setting_frag, sub_frag, "sub").commit();
				}
			}
			break;
		case STEP_SUB:
			frag = getFragmentManager().findFragmentByTag("sub");
			if(frag != null && frag instanceof SubFragment) {
				sub_frag = (SubFragment)frag;
				if(sub_frag.check()) {
					checkok = true;
					mul_frag = new MulFragment();
					getFragmentManager().beginTransaction().replace(R.id.setting_frag, mul_frag, "mul").commit();
				}
			}
			break;
		case STEP_MUL:
			frag = getFragmentManager().findFragmentByTag("mul");
			if(frag != null && frag instanceof MulFragment) {
				mul_frag = (MulFragment)frag;
				if(mul_frag.check()) {
					checkok = true;
					mix_frag = new MixFragment();
					getFragmentManager().beginTransaction().replace(R.id.setting_frag, mix_frag, "mix").commit();
				}
			}
			break;
		case STEP_MIX:
			frag = getFragmentManager().findFragmentByTag("mix");
			if(frag != null && frag instanceof MixFragment) {
				mix_frag = (MixFragment)frag;
				if(mix_frag.check()) {
					Settings set = AppContext.getSettings();
					if(!set.mAddEnable && !set.mSubEnable && !set.mMulEnable && !set.mMixEnable) {
						Utils.Alert(this, R.string.no_enabled);
						break;
					}
					
					if(set != null && set.mName != null && !set.mName.isEmpty()) {
						DataManager dm = AppContext.getDataManager();
						if(dm != null) {
							if(mMode.equals("new")) {
								dm.insert_profile(set);
							} else if(mMode.equals("edit")){
								dm.update_profile_by_id(set);
							}
						}
					}
					Intent intent = new Intent(this, PaperActivity.class);
					String sInfoFormat = getResources().getString(R.string.start_exam);
					String ok = this.getString(R.string.ok);
					String sFinalInfo=String.format(sInfoFormat, ok); 
					Utils.startActivity(this, sFinalInfo, intent);
				}
			}
			break;
		}
		if(checkok) {
			mStep++;
		}
	}

}
