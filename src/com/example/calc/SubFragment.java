package com.example.calc;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class SubFragment extends Fragment implements ContentCheck {
	
	Settings mSet;
	CheckBox mEnable;
	EditText mFrom;
	EditText mTo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mSet = AppContext.getSettings();
		View view = inflater.inflate(R.layout.sub_set, container, false);
		mEnable = (CheckBox)view.findViewById(R.id.enable);
		mFrom = (EditText)view.findViewById(R.id.from_num);
		mTo = (EditText)view.findViewById(R.id.to_num);
		mEnable.setChecked(mSet.mSubEnable);
		mFrom.setText("" + mSet.mSubFrom);
		mTo.setText("" + mSet.mSubTo);

		return view;
	}
	@Override
	public boolean check() {
		mSet.mSubEnable = mEnable.isChecked();
		//mSet.mSubBracket = mBracket.isChecked();
		
		String from = mFrom.getText().toString();
		String to = mTo.getText().toString();
		//String num = mNum.getText().toString();

		if(mSet.mSubEnable) {
			if(from == null || from.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.from_empty);
				return false;
			}
			mSet.mSubFrom = Integer.parseInt(from);
			
			if(to == null || to.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.to_empty);
				return false;
			}
			mSet.mSubTo = Integer.parseInt(to);
			/*
			if(num == null || num.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.num_empty);
				return false;
			}
			mSet.mSubNum = Integer.parseInt(num);
			*/
			
			if(mSet.mSubFrom >= mSet.mSubTo) {
				Utils.Alert(this.getActivity(), R.string.invalid_from_to);
				return false;
			}
			/*
			if(mSet.mSubNum < 2 || mSet.mSubNum > 5) {
				Utils.Alert(this.getActivity(), R.string.invalid_num);
				return false;
			}
			*/
		}
		return true;
	}


}
