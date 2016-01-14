package com.yunfangdata.fgg.utils;

import android.util.Log;

/**
 * log 类
 *
 */
public class ZLog {

	//	/**
	//	 * 示例
	//	 */
	//	private static final String TAG = "";
	//	private static final int LOGLEVEL = 9;



	public ZLog(String TAG , int[] level) {
		if (level != null && level.length > 0) {
			showlevel = level[level.length -1];
			showTAG = TAG;
		}

	}

	/**
	 * 为false是所有log不输出
	 */
	public static boolean isLogShow = true;

	/**
	 * 属于显示哪个等级的log
	 */
	private static int showWhoLervel = 1;
	

	/**
	 * 普通的log显示 log.i
	 * 
	 * @param string
	 *            显示的内容
	 * @param TAG
	 *            标签
	 * @param LOGLEVEL
	 *            log的等级
	 */
	public static void Zlogi(String string, String TAG, int LOGLEVEL) {
		if (isLogShow) {
			if (showWhoLervel == LOGLEVEL) {
				Log.i(TAG, string);
			}
		}
	}
	
	
	/**
	 * 属于显示哪个等级的log
	 */
	private int showlevel = 1;
	private String showTAG = "";

	public void zzlog(String string){
		if (isLogShow) {
			Log.i(showTAG, string);
		}
	}
}
