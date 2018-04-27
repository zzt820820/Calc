package com.example.calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ProfileActivity extends Activity implements OnItemSelectedListener {

	ListView mListView;
	List<Settings> mSettings;
	
	DataManager mDm;
	Settings mSet;
	List<HashMap<String, Object>> mData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_profile);
		mListView = (ListView)findViewById(R.id.profile_list);
		mDm = AppContext.getDataManager();
		mSet = AppContext.getSettings();
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
		mSettings = mDm.get_profiles();
		if(mSettings != null) {
			int i = 1;
			for(Settings set : mSettings) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("id", i++);
				item.put("name", set.mName);
				mData.add(item);
			}
			MySimpleAdapter adapter = new MySimpleAdapter(this, mData, R.layout.profile_item,
					new String[] {"id", "name"}, new int[] {R.id.id, R.id.name});
			mListView.setAdapter(adapter);
			mListView.setOnItemSelectedListener(this);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i,
			long l) {
		HashMap<String, Object> item = (HashMap<String, Object>)adapterView.getItemAtPosition(i);
		int id = (int)item.get("id");
		id--;
		if(mSettings != null && id >= 0 && id < mSettings.size()) {
			Settings set = mSettings.get(id);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	class MySimpleAdapter extends SimpleAdapter implements OnClickListener {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v =  super.getView(position, convertView, parent);
			Button btn = (Button)v.findViewById(R.id.edit);
			btn.setTag(position);
			btn.setOnClickListener(this);
			
			btn = (Button)v.findViewById(R.id.start);
			btn.setTag(position);
			btn.setOnClickListener(this);
			
			btn = (Button)v.findViewById(R.id.delete);
			btn.setTag(position);
			btn.setOnClickListener(this);
			return v;
		}

		public MySimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onClick(View view) {
			Intent intent;
			int position = (int)view.getTag();
			HashMap<String, Object> item = (HashMap<String, Object>)this.getItem(position);
			int id = (int)item.get("id");
			id--;
			if(mSettings != null && id >= 0 && id < mSettings.size()) {
				Settings set = mSettings.get(id);
				switch(view.getId()) {
				case R.id.edit:
					mSet.load(set);
					intent = new Intent(ProfileActivity.this, SettingsActivity.class);
					intent.putExtra("mode", "edit");
					startActivity(intent);
					break;
				case R.id.start:
					mSet.load(set);
					intent = new Intent(ProfileActivity.this, PaperActivity.class);
					String sInfoFormat = getResources().getString(R.string.start_exam);
					String ok = ProfileActivity.this.getString(R.string.ok);
					String sFinalInfo=String.format(sInfoFormat, ok); 
					Utils.startActivity(ProfileActivity.this, sFinalInfo, intent);
					break;
				case R.id.delete:
					mDm.del_profile(set.mId);
					mData.remove(position);
					notifyDataSetChanged();
					break;
				}
			}
		}
		
	}
}
