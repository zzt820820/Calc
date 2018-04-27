package com.example.calc;

import java.util.Random;

public class GenQuestion {
	class Express {
		boolean mFinal;
		String mOp;
		int mNums;
		String mExpress = "";
		Express(final Random ra, final int val) {
			mExpress += val;
			mFinal = ra.nextBoolean();
			mNums = 1;
		}
		void expand(final Random ra, final int val, final String op) {
			mFinal = ra.nextBoolean();
			mExpress +=op;
			mExpress +=val;
			mNums++;
		}
		boolean isFinal() {
			return mFinal;
		}
		
		public String toString() {
			return mExpress;
		}
	}
}
