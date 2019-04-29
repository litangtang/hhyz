package com.hhyzoa.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class FormatUtil {
	/**
	 * 字符串数组转换成整型数组
	 * @param strs
	 * @return
	 */
	public static int[] StringArrayToIntArray(String[] strs) {
		int[] ints = new int[strs.length];
		for(int i=0;i<strs.length;i++) {
			ints[i] = Integer.parseInt(strs[i]);
		}
		return ints;
	}
	
	/**
	 * 四舍五入
	 * @param d 需要四舍五入的double类型的数据
	 * @param scale 保留小数位数
	 * @return
	 */
	public static Double doubleFormat(Double d, int scale) {
		BigDecimal bd = new BigDecimal(d);
		//BigDecimal对象是不可改变的，调用它的方法后，原始对象是不会被修改的
		//setScale返回具有适当标度的BigDecimal对象，但是返回的对象不一定是新分配的
		BigDecimal bd2 = bd.setScale(scale, RoundingMode.HALF_UP);
		return bd2.doubleValue();
	}
	
	/**
	 * 四舍五入
	 * @param d 需要四舍五入的float类型的数据
	 * @param scale 保留小数位数
	 * @return
	 */
	public static Float floatFormat(Float d, int scale) {
		BigDecimal bd = new BigDecimal(d);
		//BigDecimal对象是不可改变的，调用它的方法后，原始对象是不会被修改的
		//setScale返回具有适当标度的BigDecimal对象，但是返回的对象不一定是新分配的
		BigDecimal bd2 = bd.setScale(scale, RoundingMode.HALF_UP);
		return bd2.floatValue();
	}
	
	/**
	 * 根据年份构造星期Map, key为1-52，value为String[], String[0]周的起始日期， String[1]周的结束日期
	 * @author Bill
	 * @param year
	 * @return
	 */
	public static Map<String, String[]> getWeeksOfYear(String yearStr)
	{
		Map<String, String[]> weekMap = new HashMap<String, String[]>();
		int year = Integer.parseInt(yearStr);
		if (year % 4 == 0 && year % 100!=0||year%400==0)
		{
			
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		
		return weekMap;
	}
	
	/**
	 * 转换科学计数位普通数字
	 * 字符串展示,Float展示会自动变成科学计数法 
	 * @param f
	 * @return
	 */
	public static String convertE2Num(String eStr) {
		BigDecimal num = new BigDecimal(eStr);
		DecimalFormat dFormat = new DecimalFormat("0.00");
		return dFormat.format(num);
	}
	
	public static void main(String[] args) {
//		float  f = 517062.1f;
//		System.out.println(FormatUtil.floatFormat(f, 2));

//		long l = Long.MAX_VALUE;
//		double d = l / 1.0;
//		System.out.println(d);
		float f = 0.95F;
		double d = f;
		System.out.println(d);
//		System.out.println(FormatUtil.doubleFormat(f,2));
	}

}
