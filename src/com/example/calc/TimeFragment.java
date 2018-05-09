package com.example.calc;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class TimeFragment extends Fragment implements ContentCheck {

	Settings mSet;
	EditText mHour;
	EditText mMin;
	EditText mSec;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mSet = AppContext.getSettings();
		View view = inflater.inflate(R.layout.time_set, container, false);
		mHour = (EditText)view.findViewById(R.id.time_hour);
		mMin = (EditText)view.findViewById(R.id.time_min);
		mSec = (EditText)view.findViewById(R.id.time_sec);
		if((mSet.mTimeHour == 0) && (mSet.mTimeMin == 0) && (mSet.mTimeSec == 0)) {
			int time = 0;
			if(mSet.mMode == Settings.MODE_TOTAL_TIME) {
				time = getResources().getInteger(R.integer.def_total_time);
			} else if(mSet.mMode == Settings.MODE_PER_TIME) {
				time = getResources().getInteger(R.integer.def_per_time);
			}
			mSet.mTimeHour = time /3600;
			mSet.mTimeMin = (time % 3600) / 60;
			mSet.mTimeSec = time % 60;
		}
		mHour.setText(""+mSet.mTimeHour);
		mMin.setText(""+mSet.mTimeMin);
		mSec.setText(""+mSet.mTimeSec);
		return view;
	}
	@Override
	public boolean check() {
		String str = mHour.getText().toString();
		if(str == null || str.isEmpty()) {
			Utils.Alert(this.getActivity(), R.string.hour_empty);
			return false;
		}
		mSet.mTimeHour = Integer.parseInt(str);
		if(mSet.mTimeHour < 0) {
			Utils.Alert(this.getActivity(), R.string.invalid_number);
			return false;
		}
		
		str = mMin.getText().toString();
		if(str == null || str.isEmpty()) {
			Utils.Alert(this.getActivity(), R.string.min_empty);
			return false;
		}
		mSet.mTimeMin = Integer.parseInt(str);
		if(mSet.mTimeMin < 0) {
			Utils.Alert(this.getActivity(), R.string.invalid_number);
			return false;
		}
		
		str = mSec.getText().toString();
		if(str == null || str.isEmpty()) {
			Utils.Alert(this.getActivity(), R.string.sec_empty);
			return false;
		}
		mSet.mTimeSec = Integer.parseInt(str);
		if(mSet.mTimeSec < 0) {
			Utils.Alert(this.getActivity(), R.string.invalid_number);
			return false;
		}
		if((mSet.mTimeSec == 0) && (mSet.mTimeMin == 0) && (mSet.mTimeHour == 0)) {
			Utils.Alert(this.getActivity(), R.string.invalid_number);
			return false;
		}
		return true;
	}


}
