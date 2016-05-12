package com.hhyzoa.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormatTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Double d = Double.valueOf("0.4068340797110826 ");
		Double dd = Double.parseDouble(String.valueOf(d));
		System.out.println("dd = " + dd);
		
		System.out.println("--------------------------------------------------");
		
		BigDecimal bd = new BigDecimal(d);
		BigDecimal bd2 = bd.setScale(2, RoundingMode.UP);
		System.out.println("bd2 = " + bd2.doubleValue());
	}

}
