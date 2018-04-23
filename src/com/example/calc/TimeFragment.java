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
		
		str = mMin.getText().toString();
		if(str == null || str.isEmpty()) {
			Utils.Alert(this.getActivity(), R.string.min_empty);
			return false;
		}
		mSet.mTimeMin = Integer.parseInt(str);
		
		str = mSec.getText().toString();
		if(str == null || str.isEmpty()) {
			Utils.Alert(this.getActivity(), R.string.sec_empty);
			return false;
		}
		mSet.mTimeSec = Integer.parseInt(str);
		return true;
	}


}
