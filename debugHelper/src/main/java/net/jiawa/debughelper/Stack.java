package net.jiawa.debughelper;

import java.util.Locale;

public class Stack {

	final static int STRING_CLASS = 0;
	final static int STRING_METHOD = 1;
	final static int STRING_LINE_NUMBER = 2;
	
	static int getStackLayer(int mylayer){
		return mylayer + 1;
	}
	
	static String format(int no){
		return String.format(Locale.ENGLISH, "%5d", no);
	}
	
	/*
	 * 得到当前代码文件名和行号
	 * */
    static String[] getClassMethodLindeNumberInfo(int stackLayer) {
    	Throwable t = new Throwable();
    	/**
    	 * 如果当前stackLayer=3,
    	 * 表明当前t.getStackTrace()的长度至少是4:[0,1,2,3]
    	 */
    	if(t.getStackTrace().length < stackLayer + 1) return null;
    	StackTraceElement ste = t.getStackTrace()[stackLayer];  
    	return new String[]{ste.getClassName(), ste.getMethodName(), format(ste.getLineNumber())};
    } 	
}
