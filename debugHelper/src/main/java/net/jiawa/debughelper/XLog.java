package net.jiawa.debughelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.jiawa.debughelper.flag.XFlag;
import android.util.Log;
import android.util.SparseArray;

public class XLog {
	
	private static String TAG = "xixia";
	private final static String NO_FORMAT = "%-8d";
	
	private final static int F_0 = -1;
	public final static int F_CLEAR = 0;
	// private static int sFlags = F_0;
	
	private static boolean debugFlag(int flag) {
		if(flag == F_0) {
			return true;
		}
		
		if (null == AllFlags.get(flag)) {
			return false;
		}
		
		return true;
	}
	
	static SparseArray<String> AllFlags = new SparseArray<String>();
	static int TagLength;
	static int TagNoLength;
	
	private static int getTagNoLength(int tag) {
		if (tag < 10) return 1;
		if (tag < 100) return 2;
		if (tag < 1000) return 3;
		if (tag < 10000) return 4;
 		return 5;
	}
	
	private static String getTag(int flag) {
		String tag = "";
		if (flag == F_0) {
			tag = "";
		} else if (flag == 0) {
			tag = "TAG";
		} else {
			tag = AllFlags.get(flag);
		}
		String format = "%" + TagLength + "s";
		return format(String.format(format, tag));
	}
	
	public static boolean Flag(XFlag... XFlags) {
		AllFlags.clear();
		int tagLength = 3;
		int tagNoLength = 1;
		for (int i=0; i<XFlags.length; i++) {
			XFlag xFlag = XFlags[i];
			if (!xFlag.Debug()) {
				/**
				 * 当前debug开关是关闭的
				 * 跳过这个
				 */
				continue;
			}
			
			final int index = xFlag.Index();
			if (null != AllFlags.get(index)) {
				// 删除重复的log tag
				continue;
			}
			
			final String tag = xFlag.Tag();
			AllFlags.put(index, tag);
			
			if (tag.length() > tagLength) {
				// 记录TAG的最大长度
				tagLength = tag.length();
			}
			
			if (getTagNoLength(index) > tagNoLength) {
				// 记录TAG NO的最大长度
				tagNoLength = getTagNoLength(index);
			}
			
		}
		TagLength = tagLength;
		TagNoLength = tagNoLength;
		return true;
	}
	
	/**
	 * 
	 * @param is, 只能是[1,26]之间的数字串,
	 * 		           比如:1,3,6,7,9
	 * @return
	 */
	public static int Flag(Integer... is){
		int flags = F_CLEAR;
		for(int i=0; i<is.length; i++) {
			int f = 1 << (is[i] - 1);
			flags = flags | f;
		}
		return flags;
	}
	
	private static String formatClassMethodLineNumber(String[] stack){
		return format(stack[Stack.STRING_LINE_NUMBER]) +
			   format(stack[Stack.STRING_METHOD]) +
			   format(stack[Stack.STRING_CLASS]);
	}
	
	private static String formatMethodLineNumber(String[] stack){
		return format(stack[Stack.STRING_LINE_NUMBER]) +
			   format(stack[Stack.STRING_METHOD]);
	}
	
	/*private static String formatLineNumber(String[] stack){
		return format(stack[Stack.STRING_LINE_NUMBER]);
	}*/
	
	/**
	 * 初步的标准化Log信息
	 * @param msg
	 * @return
	 */
	private static String format(String msg){
		return "["+msg+"]";
	}
	
	/**          <-   8  ->
	 * 正常的NO是[12      ]
	 * 这个函数用于获取中间相应位数的空字符串
	 */
	static String EmptyNo = null;
	private static String getEmptyNo() {
		
		if(EmptyNo != null) return EmptyNo;
		
		String temp = String.format(Locale.ENGLISH, NO_FORMAT, 1);
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<temp.length(); i++){
			sb.append(" ");
		}
		
		EmptyNo = " " + sb.toString() + "]";
		// 加上前面的DEBUG标记
		// EmptyNo = " " + " " + " " + " " + EmptyNo;
		sb = new StringBuilder();
		for(int i=0; i<TagNoLength; i++){
			sb.append(" ");
		}
		EmptyNo = " " + " " + sb.toString() + EmptyNo;
		
		// 还要加上每一个flag对应的tag的长度
		sb = new StringBuilder();
		for (int i=0; i<TagLength; i++) {
			sb.append(" ");
		}
		// 还要加上前后的两个空格
		EmptyNo = " " + sb.toString() + " " + EmptyNo;
		return EmptyNo;
	}
	
	/**
	 * 打印Log前面的序号
	 */
	private static int NO = 0;
	private static String getNo(){
		NO = NO + 1;
		String temp = String.format(Locale.ENGLISH, NO_FORMAT, NO);
		return format(temp);
	}
	private final static String LOG_HEADER = "[ F][No      ][ Line][Func ][Message            ]";
	//                                        [ F][1       ][  126][setup][2015-01-11 16:06:00]
	private final static void logHeader(){
		Log.d(TAG, "\n");
		Log.d(TAG, getFlag("F") + getTag(0) +  "[No      ][ Line][Func ][ClassName                 ][Message            ]");
	}
	private static boolean hadInit = false;
	public static void setup(String tag, boolean flags){
		if(!hadInit){
			TAG = tag;
			hadInit = true;
			NO = 0;
			EmptyNo = null;
			logHeader();
			d(true, F_0, Time.Now(), 2);
			// sFlags = flags;
			// d(true, F_0, Integer.toBinaryString(sFlags));
		}
	}
	
	/**
	 *   在外面这样初始化,表示Log的Tag是"xixia-1"
	 *   打印1,2,3,5,6,7和26的Log   
	 *   XLog.setup(1, 
        		XLog.Flag(1,2,3,5,6,7,26)
        		);
	 * @param tag
	 * @param flags
	 */
	public static void setup(int tag, boolean flags) {
		if(!hadInit){
			TAG = "xixia-"+tag;
			hadInit = true;
			NO = 0;
			EmptyNo = null;
			logHeader();
			d(true, F_0, Time.Now(), 2);
			// sFlags = flags;
			// d(true, F_0, Integer.toBinaryString(sFlags));
		}
	}
	
	private static String getFlag(Object flag) {
		/*if(flag == F_0) return format(" F");
		if(flag < 10) {
			return format(" " + flag);
		} else {
			return format(String.valueOf(flag));
		}*/
		String strFlag = "F";
		if (flag instanceof Integer) {
			int temp = (Integer) flag;
			if (temp > 0) {
				strFlag = String.valueOf(temp);
			}
		}
		String format = "%" + TagNoLength + "s";
		return format(String.format(format, strFlag));
	}
	
	/**
	 * 该方法只打印当前调用的方法名和行号
	 * @param debug 是否打印当前Log
	 */
	public static void d(boolean debug, int flag){
		if(debug && debugFlag(flag)){
			String stack[] = Stack.getClassMethodLindeNumberInfo(Stack.getStackLayer(1));
			Log.d(TAG, getFlag(flag) + getTag(flag) + getNo() + formatClassMethodLineNumber(stack));	
		}
	}
	
	// 一条log的最大长度
	private static final int MSG_MAX_LENGTH = 3500;
	
	/**
	 * 如果用户传入的一条信息长度大于3500,
	 * 则将其分隔显示
	 * @param msg
	 * @return
	 */
	private static List<String> splitMsg(String msg) {
		if (msg.length() < MSG_MAX_LENGTH) {
			return null;
		}
		
		int size = msg.length() / MSG_MAX_LENGTH;
		List<String> list = new ArrayList<String>();
		for (int i=0; i<size; i++) {
			int start = i * MSG_MAX_LENGTH;
			int end = (i+1) * MSG_MAX_LENGTH;
			list.add(msg.substring(start, end));
		}
		
		if (msg.length() % MSG_MAX_LENGTH > 0) {
			int start = size * MSG_MAX_LENGTH;
			list.add(msg.substring(start));
		}
		
		return list;
	}
	
	private static List<String> trySplitMsg(String msg) {
		try {
			List<String> rt = splitMsg(msg);
			return rt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	/**
	 * 这个方法打印当前调用的方法名和行号
	 * 也可根据layer打印堆栈信息
	 * @param debug 是否打印当前Log
	 * @param layer
	 * 		  <=1:仅输出当前Log所处信息
	 * 		  >=2:输出调用当前Log的堆栈层次信息
	 */
	public static void d(boolean debug, int flag, int layer){
		if(debug && debugFlag(flag)) {
			
			/**
			 * 第一行用户输入的信息
			 * 第二行是用户输入信息的DetailInfo
			 */
			String stack[] = Stack.getClassMethodLindeNumberInfo(Stack.getStackLayer(1));	
			Log.d(TAG, getFlag(flag) + getTag(flag) + getNo() + formatClassMethodLineNumber(stack));	
			// Log.d(TAG, getEmptyNo() + formatClassMethodLineNumber(stack));	
			
			for(int i=2; i<=layer; i++){
				stack = Stack.getClassMethodLindeNumberInfo(Stack.getStackLayer(i));
				if(null == stack) return;	
				Log.d(TAG, getEmptyNo() + formatClassMethodLineNumber(stack));
			}
		}
	}
	
	/**
	 * 
	 * @param debug 是否打印当前Log
	 * @param msg Log信息
	 */
	public static void d(boolean debug, int flag, String msg) {
		if(debug && debugFlag(flag)) {
			String stack[] = Stack.getClassMethodLindeNumberInfo(Stack.getStackLayer(1));	
			List<String> msgs = trySplitMsg(msg);
			if (null == msgs) {
				Log.d(TAG, getFlag(flag) + getTag(flag) + getNo() + formatClassMethodLineNumber(stack) + format(msg));	
			} else {
				// 第一条还是中规中矩的输出
				Log.d(TAG, getFlag(flag) + getTag(flag) + getNo() + formatClassMethodLineNumber(stack) + format(msgs.get(0)));
				for (int i=1; i<msgs.size(); i++) {
					String str = msgs.get(i);
					Log.d(TAG, getEmptyNo() + formatClassMethodLineNumber(stack) + format(str));
				}
			}
		}
	}
	
	/**
	 * @param debug 是否打印当前Log
	 * @param msg Log信息
	 * @param layer
	 * 		  <=1:仅输出当前Log所处信息
	 * 		  >=2:输出调用当前Log的堆栈层次信息
	 */
	public static void d(boolean debug, int flag, String msg, int layer){
		if(debug && debugFlag(flag)){
			
			/**
			 * 第一行用户输入的信息
			 * 第二行是用户输入信息的DetailInfo
			 */
			String stack[] = Stack.getClassMethodLindeNumberInfo(Stack.getStackLayer(1));	
			// Log.d(TAG, getFlag(flag) + getTag(flag) + getNo() + formatClassMethodLineNumber(stack) + format(msg));	
			// Log.d(TAG, getEmptyNo() + formatClassMethodLineNumber(stack));
			List<String> msgs = trySplitMsg(msg);
			if (null == msgs) {
				Log.d(TAG, getFlag(flag) + getTag(flag) + getNo() + formatClassMethodLineNumber(stack) + format(msg));	
			} else {
				// 第一条还是中规中矩的输出
				Log.d(TAG, getFlag(flag) + getTag(flag) + getNo() + formatClassMethodLineNumber(stack) + format(msgs.get(0)));
				for (int i=1; i<msgs.size(); i++) {
					String str = msgs.get(i);
					Log.d(TAG, getEmptyNo() + formatClassMethodLineNumber(stack) + format(str));
				}
			}
			
			for(int i=2; i<=layer; i++){
				stack = Stack.getClassMethodLindeNumberInfo(Stack.getStackLayer(i));
				if(null == stack) return;	
				Log.d(TAG, getEmptyNo() + formatClassMethodLineNumber(stack));
			}
		}
	}
	/**
	 * 输出回车
	 */
	public static void _n() {
		Log.d(TAG, "\n");
	}
	
	/***
	 * 根据count计数输出多少个回车
	 * @param count
	 */
	public static void _n(int count) {
		if (count <= 1) {
			_n();
		} else {
			for (int i=0; i<count; i++) {
				_n();
			}
		}
	}
	
	private static String sEmptyNoLine = null;
	/**
	 * 获取下面这样Log的   "[1       ][   86]"空格区域
	 * D/xixia-5 (13318): [No      ][ Line][Message            ]
	 * D/xixia-5 (13318): [1       ][   86][2015-01-13 15:20:05]
	 * D/xixia-5 (13318):          ][   86][setup][net.jiawa.debughelper.XLog]
	 * @return
	 */
	public static String getEmptyNoLine(){
		if(sEmptyNoLine != null) return sEmptyNoLine;
		// 首先获取"[1       ]"的长度,"getEmptyNo()"获取的就是这个长度，然后去掉中间的数字和前面的"[",
		// 变成为了"         ]"
		int length1 = getEmptyNo().length();
		// 再获取"[   86]"的长度,加2是该标准化数字的前后的"[]"这个的长度
		int length2 = Stack.format(0).length() + 2;
		String temp = "";
		for(int i=0; i<length1 + length2; i++){
			temp = temp + " ";
		}
		sEmptyNoLine = temp;
		return sEmptyNoLine;
	}
	
	static void log(boolean debug, String tag, String msg) {
		if(debug) {
			Log.i(TAG, tag + " : " + msg);
		}
	}
}
