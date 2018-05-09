package com.zzt.calc;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class QuestionGen {
	interface IQuestion {
		String getQuestion();
	}
	class Num {
		String mOp;
		int mVal;
		Num(String op, int val) {
			mOp = op;
			mVal = val;
		}
		String getOp() {
			return mOp;
		}
		void setOp(String op) {
			mOp = op;
		}
		int getVal() {
			return mVal;
		}
	}
	class Express {
		ArrayList<Num> mVals;
		boolean mFinal;
		String mOp="+";
		int mNums;
		String mExpress = "";
		int mResult = 0;
		Express(final Random ra, final int val) {
			mExpress += val;
			mFinal = ra.nextBoolean();
			mResult = val;
			mNums = 1;
			mVals = new ArrayList<Num>();
			mVals.add(new Num("+", val));
		}
		void expand(final Random ra, final int val, final String op) {
			mFinal = ra.nextBoolean();
			mExpress +=op;
			mExpress +=val;
			mNums++;
			mVals.add(new Num(op, val));
			Calculator cal = new Calculator();
			mResult = (int)cal.caculate(mExpress);
		}
		
		boolean invert() {
			for(int i=0;i < mVals.size();i++) {
				Num v = mVals.get(i);
				if(v.getOp().equals("+")) {
					v.setOp("-");
				} else if(v.getOp().equals("-")) {
					v.setOp("+");
				}
			}
			
			boolean found = false;
			int index = 0;
			ArrayList<Num> newVals = new ArrayList<Num>();
			for(int i=0;i < mVals.size();i++) {
				Num v = mVals.get(i);
				if(!found && !v.getOp().equals("+")) {
					newVals.add(v);
				} else {
					found = true;
					newVals.add(index++, v);
				}
			}
			if(!found) {
				return false;
			}
			mExpress="";
			for(int i=0;i < newVals.size();i++) {
				Num v = newVals.get(i);
				if(i==0) {
					mExpress += v.getVal();
				} else {
					mExpress += v.getOp();
					mExpress += v.getVal();
				}
			}
			mResult *= -1;
			return true;
		}
		boolean isFinal() {
			return mFinal;
		}
		
		int getNum() {
			return mNums;
		}
		String getOp() {
			return mOp;
		}
		void setOp(final String op) {
			mOp = op;
		}
		int getResult() {
			return mResult;
		}
		
		public String toString() {
			return mExpress;
		}
	}
	class AddGen implements IQuestion {
		boolean checkCarry(int a, int b) {
			int r1,r2;
			int v1,v2;
			v1 = a;
			v2 = b;

			do {
				r1 = v1 % 10;
				r2 = v2 % 10;
				v1 = v1 / 10;
				v2 = v2 / 10;
				if(r1 + r2 >= 10) {
					return true;
				}
			} while((v1 != 0) && (v2 != 0));
			
			return false;
		}
		int adjustCarry(int a, int b) {
			int r1,r2;
			int v1,v2;
			int ret = 0;
			int i = 0;
			v1 = a;
			v2 = b;

			do {
				r1 = v1 % 10;
				r2 = v2 % 10;
				v1 = v1 / 10;
				v2 = v2 / 10;
				if(r1 + r2 >= 10) {
					r2 = 9-r1;
					if(r2 > 0) {
						r2 = mRa.nextInt(r2+1);
					}
				}
				ret = r2*((int)(Math.pow(10, i))) + ret;
				i++;
			} while((v1 != 0) && (v2 != 0));
			return ret;
		}
		String TwoAddSingle() {
			int first = mRa.nextInt(90) + 10;
			int second = mRa.nextInt(10);
			if((mSet.mAddFlag & Settings.ADD_FLAG_NOCARRY) != 0) {
				if(checkCarry(first, second)) {
					second = adjustCarry(first, second);
				}
			}
			return first+"+"+second;
		}
		String TwoAddTens() {
			int first = mRa.nextInt(90) + 10;//10~99
			int second = (mRa.nextInt(9)+1)*10;//10~90
			if((mSet.mAddFlag & Settings.ADD_FLAG_NOCARRY) != 0) {
				first = mRa.nextInt(80) + 10;//10~89
				second = (mRa.nextInt(8)+1)*10;//10~80
				if(checkCarry(first, second)) {
					int v1 = 9 - first/10;
					second = (mRa.nextInt(v1)+1)*10;
					
				}
			}
			return first+"+"+second;
		}
		String BothTens() {
			int first = (mRa.nextInt(9)+1)*10;//10~90
			int second = (mRa.nextInt(9)+1)*10;//10~90
			if((mSet.mAddFlag & Settings.ADD_FLAG_NOCARRY) != 0) {
				first = (mRa.nextInt(8)+1)*10;//10~80
				second = (mRa.nextInt(8)+1)*10;//10~80
				if(checkCarry(first, second)) {
					int v1 = 9 - first/10;
					second = (mRa.nextInt(v1)+1)*10;
				}
			}
			return first+"+"+second;
		}
		String normal() {
			String str = "";
			int num = 0;
			int max_num = mRa.nextInt(mSet.mAddNum - 1) + 2;
			int v = mRa.nextInt(mSet.mAddTo-mSet.mAddFrom + 1) + mSet.mAddFrom;
			int r = v;
			str +=v;
			num++;
			while(num++ < max_num) {
				v = mRa.nextInt(mSet.mAddTo-mSet.mAddFrom + 1) + mSet.mAddFrom;
				if((mSet.mAddFlag & Settings.ADD_FLAG_NOCARRY) != 0) {
					v = adjustCarry(r, v);
				}
				r+=v;
				str +="+";
				str +=v;
			}
			
			return str;
		}
		@Override
		public String getQuestion() {
			String str = "";
			if(mMethod != null && mMethod.size() > 0) {
				if(mCounter >= mMethod.size()) {
					mCounter = 0;
				} 

				String method = mMethod.get(mCounter);
				if("twosingle".equals(method)) {
					str = TwoAddSingle();
				} else if("twotens".equals(method)) {
					str = TwoAddTens();
				} else if("bothtens".equals(method)) {
					str = BothTens();
				}
				mCounter++;

			} else {
				str = normal();
			}

			return str;
		}
		ArrayList<String> mMethod;
		int mCounter;

		AddGen() {
			mMethod = new ArrayList<String>();
			mCounter = 0;
			if((mSet.mAddFlag & Settings.ADD_FLAG_TWO_SINGLE) != 0) {
				mMethod.add("twosingle");
			}
			if((mSet.mAddFlag & Settings.ADD_FLAG_TWO_TENS) != 0) {
				mMethod.add("twotens");
			}
			if((mSet.mAddFlag & Settings.ADD_FLAG_BOTH_TENS) != 0) {
				mMethod.add("bothtens");
			}
		}
		
	}
	
	class SubGen implements IQuestion {
		boolean checkBorrow(int a, int b) {
			int r1,r2;
			int v1,v2;
			v1 = a;
			v2 = b;

			do {
				r1 = v1 % 10;
				r2 = v2 % 10;
				v1 = v1 / 10;
				v2 = v2 / 10;
				if(r1 < r2) {
					return true;
				}
			} while((v1 != 0) && (v2 != 0));
			
			return false;
		}
		int adjustBorrow(int a, int b) {
			int r1,r2;
			int v1,v2;
			int ret = 0;
			int i = 0;
			v1 = a;
			v2 = b;

			do {
				r1 = v1 % 10;
				r2 = v2 % 10;
				v1 = v1 / 10;
				v2 = v2 / 10;
				if(r1 < r2) {
					r2 = r1;
					if(r2 > 0) {
						r2 = mRa.nextInt(r2+1);
					}
				}
				ret = r2*((int)(Math.pow(10, i))) + ret;
				i++;
			} while((v1 != 0) && (v2 != 0));
			return ret;
		}
		String TwoSubSingle() {
			int first = mRa.nextInt(90) + 10;
			int second = mRa.nextInt(10);
			if((mSet.mSubFlag & Settings.SUB_FLAG_NOBORROW) != 0) {
				if(checkBorrow(first, second)) {
					second = adjustBorrow(first, second);
				}
			}
			return first+"-"+second;
		}
		String TwoSubTens() {
			int first = mRa.nextInt(90) + 10;//10~99
			int v1 = first/10;
			int second = (mRa.nextInt(v1)+1)*10;//10~90

			return first+"-"+second;
		}
		String BothTens() {
			int first = (mRa.nextInt(9)+1)*10;//10~90
			int v1 = first/10;
			int second = (mRa.nextInt(v1)+1)*10;//10~90

			return first+"-"+second;
		}
		String normal() {
			String str = "";
			int v1 = mRa.nextInt(mSet.mSubTo-mSet.mSubFrom + 1) + mSet.mSubFrom;
			int v2 = mRa.nextInt(mSet.mSubTo-mSet.mSubFrom + 1) + mSet.mSubFrom;

			if(v1 < v2) {
				int v = v1;
				v1 = v2;
				v2 = v;

			}
			if((mSet.mSubFlag & Settings.SUB_FLAG_NOBORROW) != 0) {
				if(checkBorrow(v1, v2)) {
					v2 = adjustBorrow(v1, v2);
				}
			}
			str += v1;
			str += "-";
			str += v2;
			return str;
		}
		@Override
		public String getQuestion() {
			String str = "";
			if(mMethod != null && mMethod.size() > 0) {
				if(mCounter >= mMethod.size()) {
					mCounter = 0;
				} 

				String method = mMethod.get(mCounter);
				if("twosingle".equals(method)) {
					str = TwoSubSingle();
				} else if("twotens".equals(method)) {
					str = TwoSubTens();
				} else if("bothtens".equals(method)) {
					str = BothTens();
				}
				mCounter++;

			} else {
				str = normal();
			}

			return str;
		}
		ArrayList<String> mMethod;
		int mCounter;

		SubGen() {
			mMethod = new ArrayList<String>();
			mCounter = 0;
			if((mSet.mSubFlag & Settings.SUB_FLAG_TWO_SINGLE) != 0) {
				mMethod.add("twosingle");
			}
			if((mSet.mSubFlag & Settings.SUB_FLAG_TWO_TENS) != 0) {
				mMethod.add("twotens");
			}
			if((mSet.mSubFlag & Settings.SUB_FLAG_BOTH_TENS) != 0) {
				mMethod.add("bothtens");
			}
		}
	}
	
	class MulGen implements IQuestion {
		ArrayList<Express> mExpresses = new ArrayList<Express>();
		@Override
		public String getQuestion() {
			String str = "";
			int num = 0;
			int max_num = mRa.nextInt(mSet.mMulNum - 1) + 2;
			int v = mRa.nextInt(mSet.mMulTo-mSet.mMulFrom + 1) + mSet.mMulFrom;
			str +=v;
			num++;
			while(num++ < max_num) {
				v = mRa.nextInt(mSet.mMulTo-mSet.mMulFrom + 1) + mSet.mMulFrom;
				str +="X";
				str +=v;
			}
			
			return str;
		}
	}
	
	class MixGen implements IQuestion {
		ArrayList<Express> mExpresses = new ArrayList<Express>();
		
		String getOp() {
			String op = "+";
			int v = mRa.nextInt(3);
			switch(v) {
			case 0:
				op = "+";
				break;
			case 1:
				op = "-";
				break;
			case 2:
				op = "X";
				break;
			}
			return op;
		}
		@Override
		public String getQuestion() {
			String str = "";
			String op = "+";
			int val;
			Express express = null;
			int num = 0;
			int max_num = mRa.nextInt(mSet.mMixNum - 2) + 3;
			mExpresses.clear();
			while(num < max_num) {
				val = mRa.nextInt(mSet.mMixTo-mSet.mMixFrom + 1) + mSet.mMixFrom;
				express = new Express(mRa, val);
				if(num > 0) {
					op = getOp();
					express.setOp(op);
				}
				num++;
				while(num < max_num && !express.isFinal() && mSet.mMixBracket) {
					val = mRa.nextInt(mSet.mMixTo-mSet.mMixFrom + 1) + mSet.mMixFrom;
					op = getOp();
					express.expand(mRa, val, op);
					num++;
				}
				
				mExpresses.add(express);
			}
			Calculator cal = new Calculator();
			int ret;
			if(express != null && mExpresses.size() == 1) {
				ret = (int)cal.caculate(express.toString());
				if(ret < 0) {
					express.invert();
				}
				mExpresses.clear();
				return express.toString();
			}
			num = 0;
			for(num = 0;num < mExpresses.size();num++) {
				express = mExpresses.get(num);
				if(num > 0) {
					str += express.getOp();
				}
				if(express.getNum() > 1) {
					str += "(";
					str += express;
					str += ")";
				} else {
					str += express;
				}
			}
			
			ret = (int)cal.caculate(str);
			if(ret < 0) {
				for(int i=0;i < mExpresses.size();i++) {
					express = mExpresses.get(i);
					if(express.getOp().equals("+")) {
						express.setOp("-");
					} else if(express.getOp().equals("-")) {
						express.setOp("+");
					}
				}
				express = mExpresses.get(0);
				if(express.getOp().equals("-")) {
					boolean found = false;
					int index = 0;
					ArrayList<Express> newExpresses = new ArrayList<Express>();
					for(int i=0;i < mExpresses.size();i++) {
						express = mExpresses.get(i);
						if(!found && !express.getOp().equals("+")) {
							newExpresses.add(express);
						} else {
							found = true;
							newExpresses.add(index++, express);
						}
					}
					if(!found) {
						for(int i=1;i < mExpresses.size();i++) {
							express = mExpresses.get(i);
							if(express.getOp().equals("X")) {
								if(express.invert()) {
									express = mExpresses.get(i);
									express.setOp("+");
									found = true;
									break;
								}
							} else {
								break;
							}
						}
						
					}
					mExpresses = newExpresses;
					str="";
					num = 0;
					boolean inv = false;
					for(num = 0;num < mExpresses.size();num++) {
						express = mExpresses.get(num);
						if(num == 0 && express.getOp().equals("-")) {
							express.invert();
						}
						if(num > 0) {
							str += express.getOp();
						}
						if(express.getNum() > 1) {
							str += "(";
							str += express;
							str += ")";
						} else {
							str += express;
						}
					}
				}
			}
			mExpresses.clear();
			return str;
		}
	}
	Random mRa;
	ArrayList<IQuestion> IQuestions;
	Settings mSet;
	long mResult;
	
	QuestionGen() {
		mRa = new Random();
		IQuestions = new ArrayList<IQuestion>();
		mSet = AppContext.getSettings();
		if(mSet.mAddEnable) {
			IQuestions.add(new AddGen());
		}
		if(mSet.mSubEnable) {
			IQuestions.add(new SubGen());
		}
		if(mSet.mMulEnable) {
			IQuestions.add(new MulGen());
		}
		if(mSet.mMixEnable) {
			IQuestions.add(new MixGen());
		}
		
	}
	IQuestion selectMethod() {
		int index  = mRa.nextInt(IQuestions.size());
		if(index < 0) index = 0;
		if(index >= IQuestions.size()) index = IQuestions.size()-1;
		return IQuestions.get(index);
	}
	
	String getQuestion() {
		IQuestion qs = selectMethod();
		if(qs != null) {
			String str = qs.getQuestion();
			Calculator cal = new Calculator();
			mResult = cal.caculate(str);
			return str;
		}
		return "";
	}
	int getResult() {
		return (int)mResult;
	}

}
