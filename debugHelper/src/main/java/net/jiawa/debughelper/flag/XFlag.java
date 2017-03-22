package net.jiawa.debughelper.flag;

public class XFlag {
	
	final int index;
	final boolean debug;
	final String tag;

	public XFlag(int i, boolean d, String t) {
		index = i;
		debug = d;
		tag = t;
	}
	
	public boolean Debug() {
		return debug;
	}
	
	public String Tag() {
		return tag;
	}
	
	public int Index() {
		return index;
	}
}
