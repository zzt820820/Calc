package com.zzt.calc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzt.calc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PapersActivity extends Activity implements OnItemClickListener, OnClickListener {

	ListView mListView;
	List<Paper> mPapers;
	
	DataManager mDm;
	List<HashMap<String, Object>> mData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_profile);
		mListView = (ListView)findViewById(R.id.profile_list);
		mDm = AppContext.getDataManager();
		mData = new ArrayList<HashMap<String, Object>>();
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
		mData.clear();
		mPapers = mDm.get_all_papers();
		if(mPapers != null) {
			int i = 1;
			for(Paper paper : mPapers) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("id", i++);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date(paper.mEndTime);
				String str = format.format(date);
				item.put("title", str);
				item.put("used_time", Utils.secondToTime((paper.mEndTime-paper.mStartTime)/1000));
				item.put("total", paper.mTotalNum);
				item.put("correct", paper.mCorrectNum);
				mData.add(item);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.paper_item,
					new String[] {"id", "title", "used_time", "total", "correct"}, new int[] {R.id.id, R.id.title,R.id.used_time,R.id.total,R.id.correct});
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(this);
		}
	}

	AlertDialog dlg;
	void show(int position) {
		Paper paper = mPapers.get(position);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
	    final View layout = inflater.inflate(R.layout.paper_detail, null);//获取自定义布局
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String str = format.format(new Date(paper.mStartTime));
	    TextView view = (TextView)layout.findViewById(R.id.start_time);
	    view.setText(str);
	    str = format.format(new Date(paper.mEndTime));
	    view = (TextView)layout.findViewById(R.id.end_time);
	    view.setText(str);
	    view = (TextView)layout.findViewById(R.id.total_num);
	    view.setText(paper.mTotalNum+"");
	    view = (TextView)layout.findViewById(R.id.correct_num);
	    view.setText(paper.mCorrectNum+"");
	    Button btn = (Button)layout.findViewById(R.id.review);
	    btn.setOnClickListener(this);
	    btn.setTag(position);
	    btn = (Button)layout.findViewById(R.id.delete);
	    btn.setOnClickListener(this);
	    btn.setTag(position);
	    builder.setView(layout);
		builder.setTitle(R.string.detail);//设置标题内容

		dlg = builder.create();
		dlg.show();
	 }

	@Override
	public void onItemClick(AdapterView<?> adapterView, View View, int i, long l) {
		show(i);
	}

	@Override
	public void onClick(View view) {
		Integer ino = (Integer)view.getTag();
		int position = ino;
		Paper paper = mPapers.get(position);
		switch(view.getId()) {
		case R.id.review:
			Intent intent = new Intent(this, PaperReviewActivity.class);
			intent.putExtra("paper_id", paper.mId);
			intent.putExtra("total_num", paper.mTotalNum);
			startActivity(intent);
			break;
		case R.id.delete:
			mDm.del_paper(paper.mId);
			mData.remove(position);
			mPapers.remove(position);
			SimpleAdapter adapter = (SimpleAdapter)mListView.getAdapter();
			adapter.notifyDataSetChanged();
			break;
		}
		dlg.dismiss();
	}

}
