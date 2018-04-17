package com.example.calc;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class ModeFragment extends Fragment implements OnCheckedChangeListener {

	RadioGroup mModeSelect;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mode_choice, container, false);
		mModeSelect = (RadioGroup)view.findViewById(R.id.mode_select);
		mModeSelect.setOnCheckedChangeListener(this);
		return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Settings set = AppContext.getSettings();
		if(set == null) return;
		
		switch(checkedId) {
		case R.id.mode_per_time:
			set.mMode = Settings.MODE_PER_TIME;
			break;
		case R.id.mode_total_time:
			set.mMode = Settings.MODE_TOTAL_TIME;
			break;
			
		}
	}

}
