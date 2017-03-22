package net.jiawa.debughelper;

import java.util.Random;

public class XRandom {

	static Random mRandom = new Random();
	
	public static boolean randomBoolean() {
		// 获得50%的一个随机数
		// 该随机数表示true和false的几率相等都是50%
		return (randomInt(10) + 1) > 5;
	}
	
	/**
	 * 返回[0,n)
	 * n = 7,返回的可能值是 0,1,2,3,4,5,6
	 * @param n
	 * @return
	 */
	public static int randomInt(int n) {
		return mRandom.nextInt(n);
	}	
}
