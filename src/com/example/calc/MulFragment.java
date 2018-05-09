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


public class MulFragment extends Fragment implements ContentCheck {

	Settings mSet;
	CheckBox mEnable;
	EditText mFrom;
	EditText mTo;
	EditText mNum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mSet = AppContext.getSettings();
		View view = inflater.inflate(R.layout.mul_set, container, false);
		mEnable = (CheckBox)view.findViewById(R.id.enable);
		mFrom = (EditText)view.findViewById(R.id.from_num);
		mTo = (EditText)view.findViewById(R.id.to_num);
		mNum = (EditText)view.findViewById(R.id.op_nums);
		mEnable.setChecked(mSet.mMulEnable);
		if((mSet.mMulFrom == 0) && (mSet.mMulTo == 0)) {
			mSet.mMulFrom = this.getResources().getInteger(R.integer.def_mul_from);
			mSet.mMulTo = this.getResources().getInteger(R.integer.def_mul_to);
		}
		mFrom.setText("" + mSet.mMulFrom);
		mTo.setText("" + mSet.mMulTo);
		if(mSet.mMulNum == 0) {
			mSet.mMulNum = this.getResources().getInteger(R.integer.def_mul_num);
		}
		mNum.setText("" +mSet.mMulNum);
		return view;
	}
	@Override
	public boolean check() {
		mSet.mMulEnable = mEnable.isChecked();
		
		String from = mFrom.getText().toString();
		String to = mTo.getText().toString();
		String num = mNum.getText().toString();

		if(mSet.mMulEnable) {
			if(from == null || from.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.from_empty);
				return false;
			}
			mSet.mMulFrom = Integer.parseInt(from);
			if(mSet.mMulFrom < 0) {
				Utils.Alert(this.getActivity(), R.string.invalid_number);
				return false;
			}
			
			if(to == null || to.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.to_empty);
				return false;
			}
			mSet.mMulTo = Integer.parseInt(to);
			if(mSet.mMulTo < 0) {
				Utils.Alert(this.getActivity(), R.string.invalid_number);
				return false;
			}
			
			if(num == null || num.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.num_empty);
				return false;
			}
			mSet.mMulNum = Integer.parseInt(num);
			if(mSet.mMulNum < 0) {
				Utils.Alert(this.getActivity(), R.string.invalid_number);
				return false;
			}
			
			if(mSet.mMulFrom >= mSet.mMulTo) {
				Utils.Alert(this.getActivity(), R.string.invalid_from_to);
				return false;
			}
			
			if(mSet.mMulNum < 2 || mSet.mMulNum > 5) {
				Utils.Alert(this.getActivity(), R.string.invalid_num);
				return false;
			}
		}
		return true;
	}


}
