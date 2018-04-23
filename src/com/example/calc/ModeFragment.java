package com.example.calc;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class ModeFragment extends Fragment implements OnCheckedChangeListener, ContentCheck {

	Settings mSet;
	RadioGroup mModeSelect;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mSet = AppContext.getSettings();
		View view = inflater.inflate(R.layout.mode_choice, container, false);
		mModeSelect = (RadioGroup)view.findViewById(R.id.mode_select);
		mModeSelect.setOnCheckedChangeListener(this);
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
		return true;
	}

}
