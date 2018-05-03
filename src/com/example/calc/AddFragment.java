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


public class AddFragment extends Fragment implements ContentCheck {

	Settings mSet;
	CheckBox mEnable;
	EditText mFrom;
	EditText mTo;
	EditText mNum;
	CheckBox mCarry;
	CheckBox mTwoAddSingle;
	CheckBox mTwoAddTens;
	CheckBox mTensAdd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mSet = AppContext.getSettings();
		View view = inflater.inflate(R.layout.add_set, container, false);
		mEnable = (CheckBox)view.findViewById(R.id.enable);
		mFrom = (EditText)view.findViewById(R.id.from_num);
		mTo = (EditText)view.findViewById(R.id.to_num);
		mNum = (EditText)view.findViewById(R.id.op_nums);
		mEnable.setChecked(mSet.mAddEnable);
		mCarry = (CheckBox)view.findViewById(R.id.carry);
		mTwoAddSingle = (CheckBox)view.findViewById(R.id.two_add_single);
		mTwoAddTens = (CheckBox)view.findViewById(R.id.two_add_tens);
		mTensAdd = (CheckBox)view.findViewById(R.id.tens_add);
		if((mSet.mAddFrom == 0) && (mSet.mAddTo == 0)) {
			mSet.mAddFrom = this.getResources().getInteger(R.integer.def_add_from);
			mSet.mAddTo = this.getResources().getInteger(R.integer.def_add_to);
		}
		mFrom.setText("" + mSet.mAddFrom);
		mTo.setText("" + mSet.mAddTo);
		if(mSet.mAddNum == 0) {
			mSet.mAddNum = this.getResources().getInteger(R.integer.def_add_num);
		}
		mNum.setText("" +mSet.mAddNum);
		if((mSet.mAddFlag & Settings.ADD_FLAG_CARRY) != 0) {
			mCarry.setChecked(true);
		}
		if((mSet.mAddFlag & Settings.ADD_FLAG_TWO_SINGLE) != 0) {
			mTwoAddSingle.setChecked(true);
		}
		if((mSet.mAddFlag & Settings.ADD_FLAG_TWO_TENS) != 0) {
			mTwoAddTens.setChecked(true);
		}
		if((mSet.mAddFlag & Settings.ADD_FLAG_BOTH_TENS) != 0) {
			mTensAdd.setChecked(true);
		}
		return view;
	}
	@Override
	public boolean check() {
		mSet.mAddEnable = mEnable.isChecked();
		mSet.mAddFlag = 0;
		if(mCarry.isChecked()) {
			mSet.mAddFlag |= Settings.ADD_FLAG_CARRY;
		}
		if(mTwoAddSingle.isChecked()) {
			mSet.mAddFlag |= Settings.ADD_FLAG_TWO_SINGLE;
		}
		if(mTwoAddTens.isChecked()) {
			mSet.mAddFlag |= Settings.ADD_FLAG_TWO_TENS;
		}
		if(mTensAdd.isChecked()) {
			mSet.mAddFlag |= Settings.ADD_FLAG_BOTH_TENS;
		}
		
		String from = mFrom.getText().toString();
		String to = mTo.getText().toString();
		String num = mNum.getText().toString();

		if(mSet.mAddEnable) {
			if(from == null || from.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.from_empty);
				return false;
			}
			mSet.mAddFrom = Integer.parseInt(from);
			
			if(to == null || to.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.to_empty);
				return false;
			}
			mSet.mAddTo = Integer.parseInt(to);
			
			if(num == null || num.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.num_empty);
				return false;
			}
			mSet.mAddNum = Integer.parseInt(num);
			
			if(mSet.mAddFrom >= mSet.mAddTo) {
				Utils.Alert(this.getActivity(), R.string.invalid_from_to);
				return false;
			}
			
			if(mSet.mAddNum < 2 || mSet.mAddNum > 5) {
				Utils.Alert(this.getActivity(), R.string.invalid_num);
				return false;
			}
		}
		return true;
	}


}
