package net.jiawa.debughelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Time {

	public static String Now(){
		return dateToString(new Date());
	}
	
	public static String dateToString(Date time){ 
	    SimpleDateFormat formatter; 
	    formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
	    String ctime = formatter.format(time); 

	    return String.format(Locale.ENGLISH, "%s", ctime); 
	} 
	
	public final static String getTimeStamp() {
		Long tsLong = System.currentTimeMillis()/1000;
		String ts = tsLong.toString();
		return ts;
	}
}
