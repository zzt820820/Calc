package com.example.calc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

public class Utils {

	static void Alert(final Context context, String msg) {
		AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);
		AlertDialog alertDialog = alertDialogBuilder.setPositiveButton(R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
			
		}).create();
		alertDialog.setMessage(msg);
	    alertDialog.show();
	}
	static void Alert(final Context context, int resId) {
		String str = context.getString(resId);
		Alert(context, str);
	}
	
	static void startActivity(final Context context, String msg, final Intent intent) {
		AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);
		AlertDialog alertDialog = alertDialogBuilder.setPositiveButton(R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				context.startActivity(intent);
			}
			
		}).setNegativeButton(R.string.cancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
			
		}).create();
		alertDialog.setMessage(msg);
	    alertDialog.show();
	}
    public static String secondToTime(long second){
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        return String.format("%02d:%02d:%02d", hours, minutes, second);
    }
}
