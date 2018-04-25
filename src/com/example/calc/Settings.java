package com.example.calc;

public class Settings {
	static public final int MODE_TOTAL_TIME = 1;
	static public final int MODE_PER_TIME = 2;
	
	static public final int MIX_ADD = 0x01;
	static public final int MIX_SUB = 0x02;
	static public final int MIX_MUL = 0x04;
	
	public String mName;
	
	public int mMode;
	public int mTimeHour;
	public int mTimeMin;
	public int mTimeSec;
	
	public boolean mAddEnable;
	public int mAddFrom;
	public int mAddTo;
	public int mAddNum;
	//public boolean mAddBracket;
	
	public boolean mSubEnable;
	public int mSubFrom;
	public int mSubTo;
	//public int mSubNum;
	//public boolean mSubBracket;
	
	public boolean mMulEnable;
	public int mMulFrom;
	public int mMulTo;
	public int mMulNum;
	//public boolean mMulBracket;
	
	public boolean mMixEnable;
	public int mMixFlag;
	public int mMixFrom;
	public int mMixTo;
	public int mMixNum;
	public boolean mMixBracket;
	
	public Settings(Settings in) {
		load(in);
	}
	
	public Settings() {
		mName = "";
		mMode = 0;
		mTimeHour = 0;
		mTimeMin = 0;
		mTimeSec = 0;
		
		mAddEnable = false;
		mAddFrom = 0;
		mAddTo = 0;
		mAddNum = 0;
		
		mSubEnable = false;
		mSubFrom = 0;
		mSubTo = 0;
		
		mMulEnable = false;
		mMulFrom = 0;
		mMulTo = 0;
		mMulNum = 0;
		
		mMixEnable = false;
		mMixFlag = 0;
		mMixFrom = 0;
		mMixTo = 0;
		mMixNum = 0;
		mMixBracket = false;
	}
	public void load(Settings in) {
		mName = in.mName;
		mMode = in.mMode;
		mTimeHour = in.mTimeHour;
		mTimeMin = in.mTimeMin;
		mTimeSec = in.mTimeSec;
		
		mAddEnable = in.mAddEnable;
		mAddFrom = in.mAddFrom;
		mAddTo = in.mAddTo;
		mAddNum = in.mAddNum;
		
		mSubEnable = in.mSubEnable;
		mSubFrom = in.mSubFrom;
		mSubTo = in.mSubTo;
		
		mMulEnable = in.mMulEnable;
		mMulFrom = in.mMulFrom;
		mMulTo = in.mMulTo;
		mMulNum = in.mMulNum;
		
		mMixEnable = in.mMixEnable;
		mMixFlag = in.mMixFlag;
		mMixFrom = in.mMixFrom;
		mMixTo = in.mMixTo;
		mMixNum = in.mMixNum;
		mMixBracket = in.mMixBracket;
	}
	public int getTimeSecs() {
		return mTimeHour*3600+mTimeMin*60+mTimeSec;
	}
}
