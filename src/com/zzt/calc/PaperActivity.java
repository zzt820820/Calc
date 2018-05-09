package com.zzt.calc;

import com.zzt.calc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PaperActivity extends Activity implements OnClickListener {
	boolean SIM = false;

	TextView mRemainTime;
	TextView mTotalNum;
	TextView mQuestion;
	TextView mAnswer;
	Button mPad0;
	Button mPad1;
	Button mPad2;
	Button mPad3;
	Button mPad4;
	Button mPad5;
	Button mPad6;
	Button mPad7;
	Button mPad8;
	Button mPad9;
	Button mPadDel;
	Button mPadClr;
	
	Button mNext;
	Button mEnd;
	
	String mQuestionStr;
	String mAnswerStr;
	int mCorrectAnswer;
	
	QuestionGen mQuestionGen;
	CountDown mCountDown;
	
	Settings mSet;
	int mTotal;
	int mCorrect;
	
	DataManager mDm;
	boolean mStarted = false;
	long mPaperId;
	long mStartTime;
	
	class CountDown {
		Handler mHostHandler;
		int mTimeoutMsgId;
		int mUpdateMsgId;
		boolean mRunning;
		Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				mCounter--;
				if(mCounter <= 0) {
					mHostHandler.sendEmptyMessage(mTimeoutMsgId);
					return;
				}
				mHandler.sendEmptyMessageDelayed(1, 1000);
				mHostHandler.obtainMessage(mUpdateMsgId, mCounter, 0).sendToTarget();
			}
			
		};
		int mTime;
		int mCounter;
		CountDown(int time, Handler h, int updata_msgid, int timeout_msgid) {
			mHostHandler = h;
			mUpdateMsgId = updata_msgid;
			mTimeoutMsgId = timeout_msgid;
			mTime = time;
		}
		
		void restart() {
			mCounter = mTime;
			mHandler.removeMessages(1);
			mHostHandler.obtainMessage(mUpdateMsgId, mCounter, 0).sendToTarget();
			mHandler.sendEmptyMessageDelayed(1, 1000);
			mRunning = true;
		}
		void stop() {
			mHandler.removeMessages(1);
		}
		boolean isRunning() {
			return mRunning;
		}
	}
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int id = msg.what;
			switch(id) {
			case 0:
				String str = String.format("%d:%02d:%02d", msg.arg1/3600, (msg.arg1%3600)/60, msg.arg1%60);
				mRemainTime.setText(str);
				break;
			case 1:
				if(mSet.mMode == Settings.MODE_PER_TIME) {
					next(false);
				} else if(mSet.mMode == Settings.MODE_TOTAL_TIME) {
					end(true);
				}
				break;
			}
			
		}
		
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paper);
		mSet = AppContext.getSettings();
		mRemainTime = (TextView)findViewById(R.id.remain_time);
		mTotalNum = (TextView)findViewById(R.id.total_num);
		mQuestion = (TextView)findViewById(R.id.question);
		mAnswer = (TextView)findViewById(R.id.answer);
		mPad0 = (Button)findViewById(R.id.num_0);
		mPad1 = (Button)findViewById(R.id.num_1);
		mPad2 = (Button)findViewById(R.id.num_2);
		mPad3 = (Button)findViewById(R.id.num_3);
		mPad4 = (Button)findViewById(R.id.num_4);
		mPad5 = (Button)findViewById(R.id.num_5);
		mPad6 = (Button)findViewById(R.id.num_6);
		mPad7 = (Button)findViewById(R.id.num_7);
		mPad8 = (Button)findViewById(R.id.num_8);
		mPad9 = (Button)findViewById(R.id.num_9);
		mPadDel = (Button)findViewById(R.id.num_del);
		mPadClr = (Button)findViewById(R.id.num_clr);
		
		mNext = (Button)findViewById(R.id.next);
		mEnd = (Button)findViewById(R.id.end);
		
		mPad0.setOnClickListener(this);
		mPad1.setOnClickListener(this);
		mPad2.setOnClickListener(this);
		mPad3.setOnClickListener(this);
		mPad4.setOnClickListener(this);
		mPad5.setOnClickListener(this);
		mPad6.setOnClickListener(this);
		mPad7.setOnClickListener(this);
		mPad8.setOnClickListener(this);
		mPad9.setOnClickListener(this);
		mPadDel.setOnClickListener(this);
		mPadClr.setOnClickListener(this);
		
		mNext.setOnClickListener(this);
		mEnd.setOnClickListener(this);
		mAnswerStr = "";
		mQuestionGen = new QuestionGen();
		mDm = AppContext.getDataManager();
		if(mDm != null) {
			Settings set = AppContext.getSettings();
			if(set != null) {
				Settings last = new Settings(set);
				last.mName = "";
				if(mDm.update_profile_by_name("", last) == 0) {
					mDm.insert_profile(last);
				}
			}
		}
		
		mCountDown = new CountDown(mSet.getTimeSecs(), mHandler, 0, 1);
		next(true);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		end(false);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch(id) {
		case R.id.num_0:
			mAnswerStr += "0";
			break;
		case R.id.num_1:
			mAnswerStr += "1";
			break;
		case R.id.num_2:
			mAnswerStr += "2";
			break;
		case R.id.num_3:
			mAnswerStr += "3";
			break;
		case R.id.num_4:
			mAnswerStr += "4";
			break;
		case R.id.num_5:
			mAnswerStr += "5";
			break;
		case R.id.num_6:
			mAnswerStr += "6";
			break;
		case R.id.num_7:
			mAnswerStr += "7";
			break;
		case R.id.num_8:
			mAnswerStr += "8";
			break;
		case R.id.num_9:
			mAnswerStr += "9";
			break;
		case R.id.num_del:
			int len = mAnswerStr.length();
			if(len > 0) {
				mAnswerStr = mAnswerStr.substring(0,len-1);
			}
			break;
		case R.id.num_clr:
			mAnswerStr = "";
			break;
		case R.id.next:
			next(false);
			break;
		case R.id.end:
			end(true);
			break;
		}

		if(!SIM) {
			mAnswer.setText(mAnswerStr);
		}
		
	}
	void next(boolean first) {
		if(!mStarted) {
			mPaperId = mDm.create_paper();
			mStarted = true;
		}
		if(mSet.mTotalNum > 0 && mTotal >= mSet.mTotalNum) {
			end(true);
			return;
		}
		if(!first) {
			if(checkAnswer()) {
				mCorrect++;
			}
		}
		mAnswerStr = "";
		if(!SIM) {
			mAnswer.setText(mAnswerStr);
		}
		
		String str = mQuestionGen.getQuestion();
		mQuestion.setText(str);
		mQuestionStr = str;
		mCorrectAnswer = mQuestionGen.getResult();
		if(SIM) {
			mAnswerStr = ""+mCorrectAnswer;
			mAnswer.setText(mAnswerStr);
		}
		if(mSet.mMode == Settings.MODE_TOTAL_TIME) {
			if(!mCountDown.isRunning()) {
				mCountDown.restart();
			}
		} else if(mSet.mMode == Settings.MODE_PER_TIME) {
			mCountDown.restart();
		}
		mTotal++;
		mTotalNum.setText("" + mTotal);
		mStartTime = SystemClock.uptimeMillis();

	}
	
	boolean checkAnswer() {
		long time = SystemClock.uptimeMillis();
		int used = (int)((time - mStartTime)/1000);
		if(used < 1) used = 1;
		mDm.add_question(mPaperId, mQuestionStr, mAnswerStr == null?"":mAnswerStr, "" + mCorrectAnswer, used);
		mQuestionStr = null;
		if(mAnswerStr == null || mAnswerStr.isEmpty()) {
			return false;
		}
		int ret = Integer.parseInt(mAnswerStr);
		if(ret == mCorrectAnswer) {
			return true;
		}

		return false;
	}
	
	void end(boolean show) {
		if(mQuestionStr == null) {
			return;
		}
		if(checkAnswer()) {
			mCorrect++;
		}
		mCountDown.stop();
		mDm.end_paper(mPaperId, mTotal, mCorrect);
		if(show) {
			AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
			AlertDialog alertDialog = alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					
				}
				
			}).create();
			alertDialog.setOnDismissListener(new OnDismissListener() {
	
				@Override
				public void onDismiss(DialogInterface arg0) {
					finish();
				}
				
			});
			String sInfoFormat = getResources().getString(R.string.mark_content);
			String sFinalInfo=String.format(sInfoFormat, mTotal, mCorrect, mTotal-mCorrect); 
			alertDialog.setMessage(sFinalInfo);
		    alertDialog.show();
		} else {
			finish();
		}
	}
}
