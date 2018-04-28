package com.example.calc;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class ModeFragment extends Fragment implements OnCheckedChangeListener, ContentCheck {

	Settings mSet;
	
	RadioGroup mModeSelect;
	RadioButton mTotalTime;
	RadioButton mPerTime;
	EditText mProfileName;
	
	SettingsActivity mActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = (SettingsActivity)getActivity();
		mSet = AppContext.getSettings();
		View view = inflater.inflate(R.layout.mode_choice, container, false);
		mTotalTime = (RadioButton)view.findViewById(R.id.mode_total_time);
		mPerTime = (RadioButton)view.findViewById(R.id.mode_per_time);
		mModeSelect = (RadioGroup)view.findViewById(R.id.mode_select);
		mModeSelect.setOnCheckedChangeListener(this);
		mProfileName = (EditText)view.findViewById(R.id.profile_name);
		if(mActivity.getMode().equals("edit")) {
			mProfileName.setText(mSet.mName);
		}
		if(mSet.mMode == 0) {
			String str = getResources().getString(R.string.def_mode);
			if("total".equals(str)) {
				mSet.mMode = Settings.MODE_TOTAL_TIME;
			} else if("per".equals(str)) {
				mSet.mMode = Settings.MODE_PER_TIME;
			}
		}
		if(mSet.mMode == Settings.MODE_TOTAL_TIME) {
			mTotalTime.setChecked(true);
		} else if(mSet.mMode == Settings.MODE_PER_TIME) {
			mPerTime.setChecked(true);
		} else {
			
		}
		return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(mSet == null) return;
		
		switch(checkedId) {
		case R.id.mode_per_time:
			mSet.mMode = Settings.MODE_PER_TIME;
			break;
		case R.id.mode_total_time:
			mSet.mMode = Settings.MODE_TOTAL_TIME;
			break;
			
		}
	}

	@Override
	public boolean check() {
		if(mSet.mMode == 0) {
			Utils.Alert(this.getActivity(), R.string.invalid_mode);
			return false;
		}
		mSet.mName = mProfileName.getText().toString();
		return true;
	}

}
