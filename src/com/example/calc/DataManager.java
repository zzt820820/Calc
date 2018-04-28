package com.example.calc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManager {
	private static final String DB_NAME = "data.db";
	private static final int DB_VERSION = 8;
	private static final String PROFILE_TABLE = "profiles";
	private static final String PROFILE_ID = "_id";
	private static final String PROFILE_NAME = "name";
	private static final String PROFILE_MODE = "mode";
	private static final String PROFILE_TOTAL_NUM = "total_num";
	private static final String PROFILE_TIME = "time";
	private static final String PROFILE_ADD_ENABLE = "add_enable";
	private static final String PROFILE_ADD_FROM = "add_from";
	private static final String PROFILE_ADD_TO = "add_to";
	private static final String PROFILE_ADD_NUM = "add_num";
	private static final String PROFILE_SUB_ENABLE = "sub_enable";
	private static final String PROFILE_SUB_FROM = "sub_from";
	private static final String PROFILE_SUB_TO = "sub_to";
	private static final String PROFILE_MUL_ENABLE = "mul_enable";
	private static final String PROFILE_MUL_FROM = "mul_from";
	private static final String PROFILE_MUL_TO = "mul_to";
	private static final String PROFILE_MUL_NUM = "mul_num";
	private static final String PROFILE_MIX_ENABLE = "mix_enable";
	private static final String PROFILE_MIX_FLAGS = "mix_flags";
	private static final String PROFILE_MIX_FROM = "mix_from";
	private static final String PROFILE_MIX_TO = "mix_to";
	private static final String PROFILE_MIX_NUM = "mix_num";
	private static final String PROFILE_MIX_BRACKET = "mix_bracket";
	
	private static final String PAPER_TABLE = "papers";
	private static final String PAPER_ID = "_id";
	private static final String PAPER_TITLE = "paper_title";
	private static final String PAPER_START_TIME = "start_time";
	private static final String PAPER_END_TIME = "end_time";
	private static final String PAPER_TOTAL_NUM = "total_num";
	private static final String PAPER_CORRECT_NUM = "correct_num";
	
	private static final String QUESTION_TABLE = "questions";
	private static final String QUESTION_PAPER_ID = "paper_id";
	private static final String QUESTION_ID = "_id";
	private static final String QUESTION_CONTENT = "content";
	private static final String QUESTION_USER_ANSWER = "user_answer";
	private static final String QUESTION_CORRECT_ANSWER = "correct_answer";
	private static final String QUESTION_USED_TIME = "used_time";
	
	SQLiteOpenHelper mOpenHelper;
	private final class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + PROFILE_TABLE + "(" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"name TEXT UNIQUE ON CONFLICT REPLACE, " + 
					"mode INTEGER, " +
					"total_num INTEGER, " +
					"time INTEGER, " +
					"add_enable INTEGER, " +
					"add_from INTEGER, " +
					"add_to INTEGER, " +
					"add_num INTEGER, " +
					"sub_enable INTEGER, " +
					"sub_from INTEGER, " +
					"sub_to INTEGER, " +
					"mul_enable INTEGER, " +
					"mul_from INTEGER, " +
					"mul_to INTEGER, " +
					"mul_num INTEGER, " +
					"mix_enable INTEGER, " +
					"mix_flags INTEGER, " +
					"mix_from INTEGER, " +
					"mix_to INTEGER, " +
					"mix_num INTEGER, " +
					"mix_bracket INTEGER);"
					);
			db.execSQL("CREATE TABLE " + PAPER_TABLE + "(" +
					PAPER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					PAPER_TITLE + " TEXT UNIQUE ON CONFLICT REPLACE, " + 
					PAPER_START_TIME + " INTEGER, " +
					PAPER_END_TIME + " INTEGER, " +
					PAPER_TOTAL_NUM + " INTEGER, " +
					PAPER_CORRECT_NUM + " INTEGER);"
					);
			
			db.execSQL("CREATE TABLE " + QUESTION_TABLE + "(" +
					QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					QUESTION_PAPER_ID + " TEXT, " + 
					QUESTION_CONTENT + " TEXT, " +
					QUESTION_USER_ANSWER + " TEXT, " +
					QUESTION_CORRECT_ANSWER + " TEXT, " +
					QUESTION_USED_TIME + " INTEGER);"
					);
			db.execSQL("CREATE TRIGGER IF NOT EXISTS question_cleanup DELETE ON " + PAPER_TABLE + " " +
					"BEGIN " +
						"DELETE FROM " + QUESTION_TABLE + " WHERE " + QUESTION_PAPER_ID + " = " + "old." + PAPER_ID + ";" +
					"END"
					);

		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}
	
	public DataManager(Context context) {
		mOpenHelper = new DatabaseHelper(context);
	}
	
	public long insert_profile(Settings settings) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PROFILE_NAME,settings.mName);
		values.put(PROFILE_MODE,settings.mMode);
		values.put(PROFILE_TOTAL_NUM,settings.mTotalNum);
		values.put(PROFILE_TIME,settings.mTimeHour*3600+settings.mTimeMin*60+settings.mTimeSec);
		values.put(PROFILE_ADD_ENABLE,settings.mAddEnable?1:0);
		values.put(PROFILE_ADD_FROM,settings.mAddFrom);
		values.put(PROFILE_ADD_TO,settings.mAddTo);
		values.put(PROFILE_ADD_NUM,settings.mAddNum);
		values.put(PROFILE_SUB_ENABLE,settings.mSubEnable?1:0);
		values.put(PROFILE_SUB_FROM,settings.mSubFrom);
		values.put(PROFILE_SUB_TO,settings.mSubTo);
		values.put(PROFILE_MUL_ENABLE,settings.mMulEnable?1:0);
		values.put(PROFILE_MUL_FROM,settings.mMulFrom);
		values.put(PROFILE_MUL_TO,settings.mMulTo);
		values.put(PROFILE_MUL_NUM,settings.mMulNum);
		values.put(PROFILE_MIX_ENABLE,settings.mMixEnable?1:0);
		values.put(PROFILE_MIX_FLAGS,settings.mMixFlag);
		values.put(PROFILE_MIX_FROM,settings.mMixFrom);
		values.put(PROFILE_MIX_TO,settings.mMixTo);
		values.put(PROFILE_MIX_NUM,settings.mMixNum);
		values.put(PROFILE_MIX_BRACKET,settings.mMixBracket?1:0);
		
		long id = db.insert(
				PROFILE_TABLE,
				null,
				values
				);
		return id; 
	}
	
	public int update_profile_by_name(String name, Settings settings) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PROFILE_NAME,settings.mName);
		values.put(PROFILE_MODE,settings.mMode);
		values.put(PROFILE_TOTAL_NUM,settings.mTotalNum);
		values.put(PROFILE_TIME,settings.mTimeHour*3600+settings.mTimeMin*60+settings.mTimeSec);
		values.put(PROFILE_ADD_ENABLE,settings.mAddEnable?1:0);
		values.put(PROFILE_ADD_FROM,settings.mAddFrom);
		values.put(PROFILE_ADD_TO,settings.mAddTo);
		values.put(PROFILE_ADD_NUM,settings.mAddNum);
		values.put(PROFILE_SUB_ENABLE,settings.mSubEnable?1:0);
		values.put(PROFILE_SUB_FROM,settings.mSubFrom);
		values.put(PROFILE_SUB_TO,settings.mSubTo);
		values.put(PROFILE_MUL_ENABLE,settings.mMulEnable?1:0);
		values.put(PROFILE_MUL_FROM,settings.mMulFrom);
		values.put(PROFILE_MUL_TO,settings.mMulTo);
		values.put(PROFILE_MUL_NUM,settings.mMulNum);
		values.put(PROFILE_MIX_ENABLE,settings.mMixEnable?1:0);
		values.put(PROFILE_MIX_FLAGS,settings.mMixFlag);
		values.put(PROFILE_MIX_FROM,settings.mMixFrom);
		values.put(PROFILE_MIX_TO,settings.mMixTo);
		values.put(PROFILE_MIX_NUM,settings.mMixNum);
		values.put(PROFILE_MIX_BRACKET,settings.mMixBracket?1:0);
		
		int count = db.update(
				PROFILE_TABLE,
				values,
				PROFILE_NAME+" =?",
				new String[]{name}
				);
		return count; 
	}
	
	public int update_profile_by_id(Settings settings) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PROFILE_NAME,settings.mName);
		values.put(PROFILE_MODE,settings.mMode);
		values.put(PROFILE_TOTAL_NUM,settings.mTotalNum);
		values.put(PROFILE_TIME,settings.mTimeHour*3600+settings.mTimeMin*60+settings.mTimeSec);
		values.put(PROFILE_ADD_ENABLE,settings.mAddEnable?1:0);
		values.put(PROFILE_ADD_FROM,settings.mAddFrom);
		values.put(PROFILE_ADD_TO,settings.mAddTo);
		values.put(PROFILE_ADD_NUM,settings.mAddNum);
		values.put(PROFILE_SUB_ENABLE,settings.mSubEnable?1:0);
		values.put(PROFILE_SUB_FROM,settings.mSubFrom);
		values.put(PROFILE_SUB_TO,settings.mSubTo);
		values.put(PROFILE_MUL_ENABLE,settings.mMulEnable?1:0);
		values.put(PROFILE_MUL_FROM,settings.mMulFrom);
		values.put(PROFILE_MUL_TO,settings.mMulTo);
		values.put(PROFILE_MUL_NUM,settings.mMulNum);
		values.put(PROFILE_MIX_ENABLE,settings.mMixEnable?1:0);
		values.put(PROFILE_MIX_FLAGS,settings.mMixFlag);
		values.put(PROFILE_MIX_FROM,settings.mMixFrom);
		values.put(PROFILE_MIX_TO,settings.mMixTo);
		values.put(PROFILE_MIX_NUM,settings.mMixNum);
		values.put(PROFILE_MIX_BRACKET,settings.mMixBracket?1:0);
		
		int count = db.update(
				PROFILE_TABLE,
				values,
				PROFILE_ID+" =?",
				new String[]{settings.mId+""}
				);
		return count; 
	}
	
	public Settings get_profile_by_id(int id) {
		Settings set = null;
		    SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		    Cursor cursor = db.query(
		    		PROFILE_TABLE,
		            new String[]{
		    				PROFILE_ID,
		    				PROFILE_NAME,
		    				PROFILE_MODE,
		    				PROFILE_TOTAL_NUM,
		    				PROFILE_TIME,
		    				PROFILE_ADD_ENABLE,
		    				PROFILE_ADD_FROM,
		    				PROFILE_ADD_TO,
		    				PROFILE_ADD_NUM,
		    				PROFILE_SUB_ENABLE,
		    				PROFILE_SUB_FROM,
		    				PROFILE_SUB_TO,
		    				PROFILE_MUL_ENABLE,
		    				PROFILE_MUL_FROM,
		    				PROFILE_MUL_TO,
		    				PROFILE_MUL_NUM,
		    				PROFILE_MIX_ENABLE,
		    				PROFILE_MIX_FLAGS,
		    				PROFILE_MIX_FROM,
		    				PROFILE_MIX_TO,
		    				PROFILE_MIX_NUM,
		    				PROFILE_MIX_BRACKET
		            },
		            PROFILE_ID+" =?",
		            new String[]{id+""},
		            null,
		            null,
		            null,
		            null
		    );
		    while(cursor.moveToNext()){
		    	set = new Settings();
		    	set.mId = cursor.getInt(cursor.getColumnIndex(PROFILE_ID));
		    	set.mName = cursor.getString(cursor.getColumnIndex(PROFILE_NAME));
		    	set.mMode = cursor.getInt(cursor.getColumnIndex(PROFILE_MODE));
		    	set.mTotalNum = cursor.getInt(cursor.getColumnIndex(PROFILE_TOTAL_NUM));
		    	int time = cursor.getInt(cursor.getColumnIndex(PROFILE_TIME));
		    	set.mTimeHour = time/3600;
		    	set.mTimeMin = (time % 3600) / 60;
		    	set.mTimeSec = time % 60;
		    	set.mAddEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_ENABLE)) > 0)?true:false;
		    	set.mAddFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_FROM));
		    	set.mAddTo = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_TO));
		    	set.mAddNum = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_NUM));
		    	set.mSubEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_ENABLE)) > 0)?true:false;
		    	set.mSubFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_FROM));
		    	set.mSubTo = cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_TO));
		    	set.mMulEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_ENABLE)) > 0)?true:false;
		    	set.mMulFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_FROM));
		    	set.mMulTo = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_TO));
		    	set.mMulNum = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_NUM));
		    	set.mMixEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_ENABLE)) > 0)?true:false;
		    	set.mMixFlag = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_FLAGS));
		    	set.mMixFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_FROM));
		    	set.mMixTo = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_TO));
		    	set.mMixNum = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_NUM));
		    	set.mMixBracket = (cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_BRACKET)) > 0)?true:false;
		    }
		    cursor.close();

		return set;
	}
	public Settings get_profile_by_name(String name) {
		Settings set = null;
		    SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		    Cursor cursor = db.query(
		    		PROFILE_TABLE,
		            new String[]{
		    				PROFILE_ID,
		    				PROFILE_NAME,
		    				PROFILE_MODE,
		    				PROFILE_TOTAL_NUM,
		    				PROFILE_TIME,
		    				PROFILE_ADD_ENABLE,
		    				PROFILE_ADD_FROM,
		    				PROFILE_ADD_TO,
		    				PROFILE_ADD_NUM,
		    				PROFILE_SUB_ENABLE,
		    				PROFILE_SUB_FROM,
		    				PROFILE_SUB_TO,
		    				PROFILE_MUL_ENABLE,
		    				PROFILE_MUL_FROM,
		    				PROFILE_MUL_TO,
		    				PROFILE_MUL_NUM,
		    				PROFILE_MIX_ENABLE,
		    				PROFILE_MIX_FLAGS,
		    				PROFILE_MIX_FROM,
		    				PROFILE_MIX_TO,
		    				PROFILE_MIX_NUM,
		    				PROFILE_MIX_BRACKET
		            },
		            PROFILE_NAME+" =?",
		            new String[]{name+""},
		            null,
		            null,
		            null,
		            null
		    );
		    while(cursor.moveToNext()){
		    	set = new Settings();
		    	set.mId = cursor.getInt(cursor.getColumnIndex(PROFILE_ID));
		    	set.mName = cursor.getString(cursor.getColumnIndex(PROFILE_NAME));
		    	set.mMode = cursor.getInt(cursor.getColumnIndex(PROFILE_MODE));
		    	set.mTotalNum = cursor.getInt(cursor.getColumnIndex(PROFILE_TOTAL_NUM));
		    	int time = cursor.getInt(cursor.getColumnIndex(PROFILE_TIME));
		    	set.mTimeHour = time/3600;
		    	set.mTimeMin = (time % 3600) / 60;
		    	set.mTimeSec = time % 60;
		    	set.mAddEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_ENABLE)) > 0)?true:false;
		    	set.mAddFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_FROM));
		    	set.mAddTo = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_TO));
		    	set.mAddNum = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_NUM));
		    	set.mSubEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_ENABLE)) > 0)?true:false;
		    	set.mSubFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_FROM));
		    	set.mSubTo = cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_TO));
		    	set.mMulEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_ENABLE)) > 0)?true:false;
		    	set.mMulFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_FROM));
		    	set.mMulTo = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_TO));
		    	set.mMulNum = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_NUM));
		    	set.mMixEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_ENABLE)) > 0)?true:false;
		    	set.mMixFlag = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_FLAGS));
		    	set.mMixFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_FROM));
		    	set.mMixTo = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_TO));
		    	set.mMixNum = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_NUM));
		    	set.mMixBracket = (cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_BRACKET)) > 0)?true:false;
		    }
		    cursor.close();

		return set;
	}
	public List<Settings> get_profiles() {
		List<Settings> list = new ArrayList<Settings>();
	    SQLiteDatabase db = mOpenHelper.getReadableDatabase();
	    Cursor cursor = db.query(
	    		PROFILE_TABLE,
	            new String[]{
	    				PROFILE_ID,
	    				PROFILE_NAME,
	    				PROFILE_MODE,
	    				PROFILE_TOTAL_NUM,
	    				PROFILE_TIME,
	    				PROFILE_ADD_ENABLE,
	    				PROFILE_ADD_FROM,
	    				PROFILE_ADD_TO,
	    				PROFILE_ADD_NUM,
	    				PROFILE_SUB_ENABLE,
	    				PROFILE_SUB_FROM,
	    				PROFILE_SUB_TO,
	    				PROFILE_MUL_ENABLE,
	    				PROFILE_MUL_FROM,
	    				PROFILE_MUL_TO,
	    				PROFILE_MUL_NUM,
	    				PROFILE_MIX_ENABLE,
	    				PROFILE_MIX_FLAGS,
	    				PROFILE_MIX_FROM,
	    				PROFILE_MIX_TO,
	    				PROFILE_MIX_NUM,
	    				PROFILE_MIX_BRACKET
	            },
	            PROFILE_NAME+" <>?",
	            new String[]{""},
	            null,
	            null,
	            null,
	            null
	    );
	    while(cursor.moveToNext()){
	    	Settings set = new Settings();
	    	set.mId = cursor.getInt(cursor.getColumnIndex(PROFILE_ID));
	    	set.mName = cursor.getString(cursor.getColumnIndex(PROFILE_NAME));
	    	set.mMode = cursor.getInt(cursor.getColumnIndex(PROFILE_MODE));
	    	set.mTotalNum = cursor.getInt(cursor.getColumnIndex(PROFILE_TOTAL_NUM));
	    	int time = cursor.getInt(cursor.getColumnIndex(PROFILE_TIME));
	    	set.mTimeHour = time/3600;
	    	set.mTimeMin = (time % 3600) / 60;
	    	set.mTimeSec = time % 60;
	    	set.mAddEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_ENABLE)) > 0)?true:false;
	    	set.mAddFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_FROM));
	    	set.mAddTo = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_TO));
	    	set.mAddNum = cursor.getInt(cursor.getColumnIndex(PROFILE_ADD_NUM));
	    	set.mSubEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_ENABLE)) > 0)?true:false;
	    	set.mSubFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_FROM));
	    	set.mSubTo = cursor.getInt(cursor.getColumnIndex(PROFILE_SUB_TO));
	    	set.mMulEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_ENABLE)) > 0)?true:false;
	    	set.mMulFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_FROM));
	    	set.mMulTo = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_TO));
	    	set.mMulNum = cursor.getInt(cursor.getColumnIndex(PROFILE_MUL_NUM));
	    	set.mMixEnable = (cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_ENABLE)) > 0)?true:false;
	    	set.mMixFlag = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_FLAGS));
	    	set.mMixFrom = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_FROM));
	    	set.mMixTo = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_TO));
	    	set.mMixNum = cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_NUM));
	    	set.mMixBracket = (cursor.getInt(cursor.getColumnIndex(PROFILE_MIX_BRACKET)) > 0)?true:false;
	    	list.add(set);
	    }
	    cursor.close();
		return list;
	}
	public void del_profile(int id){
	    SQLiteDatabase db = mOpenHelper.getWritableDatabase();
	    db.delete(
	    		PROFILE_TABLE,
	    		PROFILE_ID+" =?",
	            new String[]{id+""}
	    );
	    db.close();
	}
	
	public int get_paper_num_by_title_prefix(String title_prefix) {
	    SQLiteDatabase db = mOpenHelper.getReadableDatabase();
	    Cursor cursor = db.query(
	    		PAPER_TABLE,
	            new String[]{
	    				PAPER_TITLE,
	    				PAPER_START_TIME,
	    				PAPER_END_TIME,
	    				PAPER_TOTAL_NUM,
	    				PAPER_CORRECT_NUM
	            },
	            PAPER_TITLE +" LIKE ?",
	            new String[]{title_prefix+"%"},
	            null,
	            null,
	            null,
	            null
	    );
	    int count = cursor.getCount();

	    cursor.close();
		return count;
	}
	public List<Paper> get_all_papers() {
		List<Paper> list = new ArrayList<Paper>();
	    SQLiteDatabase db = mOpenHelper.getReadableDatabase();
	    Cursor cursor = db.query(
	    		PAPER_TABLE,
	            new String[]{
	    				PAPER_ID,
	    				PAPER_TITLE,
	    				PAPER_START_TIME,
	    				PAPER_END_TIME,
	    				PAPER_TOTAL_NUM,
	    				PAPER_CORRECT_NUM
	            },
	            null,
	            null,
	            null,
	            null,
	            null,
	            null
	    );
	    while(cursor.moveToNext()){
	    	Paper paper = new Paper();
	    	paper.mId = cursor.getInt(cursor.getColumnIndex(PAPER_ID));
	    	paper.mTitle = cursor.getString(cursor.getColumnIndex(PAPER_TITLE));
	    	paper.mStartTime = cursor.getLong(cursor.getColumnIndex(PAPER_START_TIME));
	    	paper.mEndTime = cursor.getLong(cursor.getColumnIndex(PAPER_END_TIME));
	    	paper.mTotalNum = cursor.getInt(cursor.getColumnIndex(PAPER_TOTAL_NUM));
	    	paper.mCorrectNum = cursor.getInt(cursor.getColumnIndex(PAPER_CORRECT_NUM));

	    	list.add(paper);
	    }

	    cursor.close();
		return list;
	}
	
	public long create_paper() {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_");
		String prefix = format.format(date);
		int count = get_paper_num_by_title_prefix(prefix);
		count++;
		
		ContentValues values = new ContentValues();
		values.put(PAPER_TITLE,prefix+count);
		values.put(PAPER_START_TIME,date.getTime());
		values.put(PAPER_END_TIME,0);
		values.put(PAPER_TOTAL_NUM,0);
		values.put(PAPER_CORRECT_NUM,0);
		
		long id = db.insert(
				PAPER_TABLE,
				null,
				values
				);
		return id; 
	}
	
	public void del_paper(int id){
	    SQLiteDatabase db = mOpenHelper.getWritableDatabase();
	    db.delete(
	    		PAPER_TABLE,
	    		PAPER_ID+" =?",
	            new String[]{id+""}
	    );
	}
	public int end_paper(long paper_id, int total, int correct) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		Date date = new Date();
		ContentValues values = new ContentValues();
		values.put(PAPER_END_TIME,date.getTime());
		values.put(PAPER_TOTAL_NUM,total);
		values.put(PAPER_CORRECT_NUM,correct);
		int count = db.update(PAPER_TABLE, 
				values, 
				PAPER_ID + "=?", 
				new String[]{""+paper_id}
		);
		return count;
	}
	public void clear_papers() {
	    SQLiteDatabase db = mOpenHelper.getWritableDatabase();
	    db.delete(
	    		PAPER_TABLE,
	    		PAPER_END_TIME+" =?",
	            new String[]{"0"}
	    );
	}
	
	public long add_question(long paper_id, String question, String user_answer, String correct_answer, int used_time) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(QUESTION_PAPER_ID,paper_id);
		values.put(QUESTION_CONTENT,question);
		values.put(QUESTION_USER_ANSWER,user_answer);
		values.put(QUESTION_CORRECT_ANSWER,correct_answer);
		values.put(QUESTION_USED_TIME,used_time);
		
		long id = db.insert(
				QUESTION_TABLE,
				null,
				values
				);
		return id;
	}
	
	public List<Question> get_questions_by_paper_id(int paper_id) {
		List<Question> list = new ArrayList<Question>();
	    SQLiteDatabase db = mOpenHelper.getReadableDatabase();
	    Cursor cursor = db.query(
	    		QUESTION_TABLE,
	            new String[]{
	    				QUESTION_ID,
	    				QUESTION_CONTENT,
	    				QUESTION_USER_ANSWER,
	    				QUESTION_CORRECT_ANSWER,
	    				QUESTION_USED_TIME
	            },
	            QUESTION_PAPER_ID+" =?",
	            new String[]{paper_id+""},
	            null,
	            null,
	            null,
	            null
	    );
	    while(cursor.moveToNext()){
	    	Question question = new Question();
	    	question.mQuestion = cursor.getString(cursor.getColumnIndex(QUESTION_CONTENT));
	    	question.mUserAnswer = cursor.getString(cursor.getColumnIndex(QUESTION_USER_ANSWER));
	    	question.mCorrectAnswer = cursor.getString(cursor.getColumnIndex(QUESTION_CORRECT_ANSWER));
	    	question.mUsedTime = cursor.getInt(cursor.getColumnIndex(QUESTION_USED_TIME));

	    	list.add(question);
	    }

	    cursor.close();
		return list;
	}
	public void  close() {
		mOpenHelper.close();
	}
}
