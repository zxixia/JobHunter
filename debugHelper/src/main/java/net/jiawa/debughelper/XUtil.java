package net.jiawa.debughelper;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.os.Environment;
import android.view.View;

public class XUtil {
	private final static String TAG = "XUtil";
	private final static boolean DEBUG_EXTERNAL_DIRECTORY = true;
	private final static boolean DEBUG_TIME = true;
	enum ExternalDirectoryType {
		XFile
	}
	
	static String getCurrentTimeStamps() {
		SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("yyyy_MM_dd_HH_mm_ss");
		// return String.valueOf(System.currentTimeMillis());
		String rt = sdf.format(System.currentTimeMillis());
		XLog.log(DEBUG_TIME, TAG , "CurrentTimeStamps : " + rt);
		return rt;
	}
	
	/**
	 * 获取输出到设备的根目录下的debug文件夹路径
	 * @return
	 */
	static String getExternalDirectory(ExternalDirectoryType type) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +  
    			".Jiawa" + File.separator + type.name();
		XLog.log(DEBUG_EXTERNAL_DIRECTORY, TAG , "ExternalDirectory : " + path);
		File directory = new File(path);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		return directory.getAbsolutePath();
	}
	
	static DecimalFormat df2 = new DecimalFormat("###.00000"); 
	static String format(float f){
		   return String.format("% .5f", f);
		// return df2.format(f);
	}
	
	/**
	 * @return
	 */
	public static String getMatrix(float[] matrix){
		
		int sqrt = (int) Math.sqrt(matrix.length);
		if(sqrt * sqrt != matrix.length){
			return "error("+"matrix.length:"+matrix.length + ", sqrt:"+sqrt+")";
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<sqrt; i++){
			String temp = "";
			for(int j=0; j<sqrt; j++){
				temp = temp + 
				   format(matrix[i*sqrt + j]) + 
				   (j < sqrt-1 ? " " : "");
			}
			if(i==0){
				//第一行不要"["
				sb.append(temp+" ]"); 
				sb.append("\n");
			} else if(i<sqrt-1){
				sb.append(XLog.getEmptyNoLine()); 
				sb.append("["+temp+" ]"); 
				sb.append("\n");
			} else if(i == sqrt-1){
				//最后一行不要"]"
				sb.append(XLog.getEmptyNoLine()); 
				sb.append("["+temp+" "); 
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * @return
	 */
	public static String getBoolean(float[] matrix){
		
		int sqrt = (int) Math.sqrt(matrix.length);
		if(sqrt * sqrt != matrix.length){
			return "error("+"matrix.length:"+matrix.length + ", sqrt:"+sqrt+")";
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<sqrt; i++){
			String temp = "";
			for(int j=0; j<sqrt; j++){
				temp = temp + 
				   format(matrix[i*sqrt + j]) + 
				   (j < sqrt-1 ? " " : "");
			}
			if(i==0){
				//第一行不要"["
				sb.append(temp+" ]"); 
				sb.append("\n");
			} else if(i<sqrt-1){
				sb.append(XLog.getEmptyNoLine()); 
				sb.append("["+temp+" ]"); 
				sb.append("\n");
			} else if(i == sqrt-1){
				//最后一行不要"]"
				sb.append(XLog.getEmptyNoLine()); 
				sb.append("["+temp+" "); 
			}
		}
		
		return sb.toString();
	}
	
	public static String getVisibility(View view) {
		
		if (null == view) return "NULL";
		
		final int visibility = view.getVisibility();
		
		switch (visibility) {
		case View.INVISIBLE:
			return "INVISIBLE";
		case View.GONE:
			return "GONE";
		case View.VISIBLE:
			return "VISIBLE";
		}
		return "UNKNOWN";
	}
	
	public static <T> String getStringFromList(ArrayList<T> list) {
		try {
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<list.size()-1; i++) {
				sb.append(list.get(i).toString());
				sb.append(", ");
			}
			final int lastIndex = list.size()-1;
			sb.append(list.get(lastIndex).toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 判断第一个参数first是否出现于后面的参数others数组里
	 * @param first 
	 * @param others
	 * @return
	 */
	public static boolean firstExistInOthers(int first, int... others) {
		if (null == others) return false;
		if (others.length < 1) return false;
		for (int i=0; i<others.length; i++) {
			int other = others[i];
			if (first == other) {
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * 获取输出到设备的根目录下的debug文件夹路径
	 * @return
	 */
	public static String getOrCreateExternalDirectory(String folderName) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +  
    			".Jiawa" + File.separator + folderName;
		XLog.log(DEBUG_EXTERNAL_DIRECTORY, TAG , "ExternalDirectory : " + path);
		File directory = new File(path);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		return directory.getAbsolutePath();
	}
}
