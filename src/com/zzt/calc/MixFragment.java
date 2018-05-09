package com.zzt.calc;
import com.zzt.calc.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class MixFragment extends Fragment implements ContentCheck {

	Settings mSet;
	CheckBox mEnable;
	CheckBox mAdd;
	CheckBox mSub;
	CheckBox mMul;
	EditText mFrom;
	EditText mTo;
	EditText mNum;
	CheckBox mBracket;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mSet = AppContext.getSettings();
		View view = inflater.inflate(R.layout.mix_set, container, false);
		mEnable = (CheckBox)view.findViewById(R.id.enable);
		mAdd = (CheckBox)view.findViewById(R.id.mix_add);
		mSub = (CheckBox)view.findViewById(R.id.mix_sub);
		mMul = (CheckBox)view.findViewById(R.id.mix_mul);
		mFrom = (EditText)view.findViewById(R.id.from_num);
		mTo = (EditText)view.findViewById(R.id.to_num);
		mNum = (EditText)view.findViewById(R.id.op_nums);
		mBracket = (CheckBox)view.findViewById(R.id.bracket);
		
		mEnable.setChecked(mSet.mMixEnable);
		if((mSet.mMixFrom == 0) && (mSet.mMixTo == 0)) {
			mSet.mMixFrom = this.getResources().getInteger(R.integer.def_mix_from);
			mSet.mMixTo = this.getResources().getInteger(R.integer.def_mix_to);
		}
		mFrom.setText("" + mSet.mMixFrom);
		mTo.setText("" + mSet.mMixTo);
		if(mSet.mMixNum == 0) {
			mSet.mMixNum = this.getResources().getInteger(R.integer.def_mix_num);
		}
		mNum.setText("" +mSet.mMixNum);
		mBracket.setChecked(mSet.mMixBracket);
		if(mSet.mMixFlag == 0) {
			if(getResources().getBoolean(R.bool.def_mix_add)) {
				mSet.mMixFlag |= Settings.MIX_ADD;
			}
			if(getResources().getBoolean(R.bool.def_mix_sub)) {
				mSet.mMixFlag |= Settings.MIX_SUB;
			}
			if(getResources().getBoolean(R.bool.def_mix_mul)) {
				mSet.mMixFlag |= Settings.MIX_MUL;
			}
		}
		if((mSet.mMixFlag & Settings.MIX_ADD) != 0) {
			mAdd.setChecked(true);
		}
		if((mSet.mMixFlag & Settings.MIX_SUB) != 0) {
			mSub.setChecked(true);
		}
		if((mSet.mMixFlag & Settings.MIX_MUL) != 0) {
			mMul.setChecked(true);
		}
		
		return view;
	}
	@Override
	public boolean check() {
		mSet.mMixEnable = mEnable.isChecked();
		mSet.mMixFlag = 0;
		if(mAdd.isChecked()) {
			mSet.mMixFlag |= Settings.MIX_ADD;
		}
		if(mSub.isChecked()) {
			mSet.mMixFlag |= Settings.MIX_SUB;
		}
		if(mMul.isChecked()) {
			mSet.mMixFlag |= Settings.MIX_MUL;
		}

		mSet.mMixBracket = mBracket.isChecked();
		
		String from = mFrom.getText().toString();
		String to = mTo.getText().toString();
		String num = mNum.getText().toString();

		if(mSet.mMixEnable) {
			if(from == null || from.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.from_empty);
				return false;
			}
			mSet.mMixFrom = Integer.parseInt(from);
			if(mSet.mMixFrom < 0) {
				Utils.Alert(this.getActivity(), R.string.invalid_number);
				return false;
			}
			
			if(to == null || to.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.to_empty);
				return false;
			}
			mSet.mMixTo = Integer.parseInt(to);
			if(mSet.mMixTo < 0) {
				Utils.Alert(this.getActivity(), R.string.invalid_number);
				return false;
			}
			
			if(num == null || num.isEmpty()) {
				Utils.Alert(this.getActivity(), R.string.num_empty);
				return false;
			}
			mSet.mMixNum = Integer.parseInt(num);
			
			if(mSet.mMixNum < 3 || mSet.mMixNum > 5) {
				Utils.Alert(this.getActivity(), R.string.invalid_mixnum);
				return false;
			}
			if(Integer.bitCount(mSet.mMixFlag) < 2) {
				Utils.Alert(this.getActivity(), R.string.invalid_mix);
				return false;
			}
		}
		return true;
	}


}
