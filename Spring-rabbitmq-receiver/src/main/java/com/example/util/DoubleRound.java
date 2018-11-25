package com.example.util;



/**
 * @author zhangkx
 *
 */

public class DoubleRound {

	
	
	public  static double getValeLatterTwo(double value) {
		double returnValue=(double) Math.round((value) * 100) / 100;
		return returnValue;
	}
	
	
	
}
