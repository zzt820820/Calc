package com.zzt.calc;

import java.util.List;

import com.zzt.calc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PaperReviewActivity extends Activity implements OnClickListener {

	List<Question> mQuestions;
	int index = 0;
	DataManager mDm;
	
	TextView mTime;
	TextView mTotal;
	TextView mCurrent;
	TextView mQuestion;
	TextView mAnswer;
	TextView mCorrectAnswer;
	
	Button mEnd;
	Button mNext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paper_review);
		mDm = AppContext.getDataManager();

		mTime = (TextView)findViewById(R.id.used_time);
		mTotal = (TextView)findViewById(R.id.total_num);
		mCurrent = (TextView)findViewById(R.id.current_num);
		mQuestion = (TextView)findViewById(R.id.question);
		mAnswer = (TextView)findViewById(R.id.answer);
		mCorrectAnswer = (TextView)findViewById(R.id.correct_answer);
		Intent intent = getIntent();
		if(intent != null) {
			int id = intent.getIntExtra("paper_id", -1);
			if(id >= 0) {
				mQuestions = mDm.get_questions_by_paper_id(id);
				next();
			}
			int num = intent.getIntExtra("total_num", -1);
			if(num > 0) {
				mTotal.setText(num+"");
			}
		}
		mEnd = (Button)findViewById(R.id.end);
		mNext = (Button)findViewById(R.id.next);
		mEnd.setOnClickListener(this);
		mNext.setOnClickListener(this);
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
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	boolean next() {
		if(index < mQuestions.size()) {
			Question q = mQuestions.get(index++);
			mTime.setText(q.mUsedTime+"");
			mCurrent.setText(index+"");
			mQuestion.setText(q.mQuestion);
			mAnswer.setText(q.mUserAnswer);
			mCorrectAnswer.setText(q.mCorrectAnswer);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.end:
			finish();
			break;
		case R.id.next:
			if(!next()) {
				finish();
			}
			break;
		}
		
	}

}
