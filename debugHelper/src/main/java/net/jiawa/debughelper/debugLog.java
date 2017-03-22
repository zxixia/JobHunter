package net.jiawa.debughelper;

import android.util.Log;

public class debugLog {
	
	private final static String TAG = "xixia";
	private final static int STACK_PARENT_LAYER = 2;
	private final static int STACK_CURRENT_LAYER = 1;
	private final static String STACK_ERROR = "stack_error";
	private static boolean DEBUG_CURRENT_INFO = false;
	private static boolean DEBUG_PARRENT_INFO = false;
	
	/**
	 * 打印Log前面的序号
	 */
	private static int NO = 0;
	private static String getNo(){
		NO = NO + 1;
		String temp = String.format("%-6d", NO);
		return "[" + temp + "]";
	}
	
	private static boolean hadInit = false;
	public static void setup(boolean current, boolean parent){
		if(!hadInit){
			int stackLayer = 1;
			DEBUG_CURRENT_INFO = current;
			DEBUG_PARRENT_INFO = parent;
			hadInit = true;
			logDebug("setup{ DEBUG_CURRENT_INFO:"+DEBUG_CURRENT_INFO+",DEBUG_PARRENT_INFO:"+DEBUG_PARRENT_INFO + "}", 
					true, true, stackLayer + STACK_CURRENT_LAYER);
		}
	}
	
	private static void logDebug(String msg, boolean showCurrent, boolean showParent, int stackLayer){
		String no = getNo();
		String currentInfo = getDetailInfo(stackLayer + STACK_CURRENT_LAYER);
		String parentInfo = getDetailInfo(stackLayer + STACK_PARENT_LAYER);
		StringBuilder sb = new StringBuilder();
		if(showParent && !parentInfo.equals(STACK_ERROR)){
			sb.append(no);
			sb.append("[P]");
			sb.append(parentInfo);
			sb.append("\n");
		}
		if(showCurrent && !currentInfo.equals(STACK_ERROR)){
			sb.append(no);
			sb.append("[C]");
			sb.append(currentInfo);
			sb.append("\n");
		}
		sb.append(no);
		sb.append(msg);
		Log.d(TAG, sb.toString());
	}
	
	private static void logDebug(String msg){
		StringBuilder sb = new StringBuilder();
		sb.append(getNo());
		sb.append(msg);
		Log.d(TAG, sb.toString());
	}
	
	private static void logDebug(boolean debug, boolean showStack, String msg, int stackLayer){
		if(debug){
			if((DEBUG_CURRENT_INFO || DEBUG_PARRENT_INFO) && showStack){
				// 需要打印堆栈
				logDebug(msg, DEBUG_CURRENT_INFO, DEBUG_PARRENT_INFO, stackLayer + STACK_CURRENT_LAYER);
			} else {
				// 不需要打印堆栈
				logDebug(msg);
			}
		}
	}
	
	/**
	 * 可以打印堆栈的
	 * @param debug 是否当因当前Log
	 * @param msg
	 */
	public static void d(boolean debug, String msg){
		int stackLayer = 1;
		logDebug(debug, true, msg, stackLayer + STACK_CURRENT_LAYER);
	}
	
	/**
	 * 打印调用堆栈
	 * @param msg
	 */
	public static void d(String msg){
		int stackLayer = 1;
		logDebug(true, true, msg, stackLayer + STACK_CURRENT_LAYER);
	}
	
	/**
	 * 不可以打印堆栈的
	 * @param debug 是否当因当前Log
	 * @param msg
	 */
	public static void i(boolean debug, String msg){
		int stackLayer = 1;
		logDebug(debug, false, msg, stackLayer + STACK_CURRENT_LAYER);
	}
	
	/**
	 * 不打印调用堆栈
	 * @param msg
	 */
	public static void i(String msg){
		int stackLayer = 1;
		logDebug(true, false, msg, stackLayer + STACK_CURRENT_LAYER);
	}
	
	/*
	 * 得到当前代码文件名和行号
	 * */
    private static String getDetailInfo(int stackLayer) {
    	Throwable t = new Throwable();
    	/**
    	 * 如果当前stackLayer=3,
    	 * 表明当前t.getStackTrace()的长度至少是4:[0,1,2,3]
    	 */
    	if(t.getStackTrace().length < stackLayer + 1) return STACK_ERROR;
    	StackTraceElement ste = t.getStackTrace()[stackLayer];  
    	return "[" + ste.getClassName() + "," + ste.getMethodName() + "," + ste.getLineNumber() + "]";
    } 	
}