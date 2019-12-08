package com.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataManager {
	
	public static void main(String args[]) throws ParseException{
		System.out.println(isValidDate());
	}
	public static boolean isValidDate() throws ParseException{
		boolean flag = false;
		Date LicenseDate = new SimpleDateFormat(String.valueOf("dd_MMMMM_yyy")).parse("06_April_2019");
		Date CurrentDate = new Date();
		//System.out.println(CurrentDate.getTime()-LicenseDate.getTime());
		long diffDays =(CurrentDate.getTime()-LicenseDate.getTime()) / (60 * 60 * 1000 * 24);
		//System.out.println(diffDays);
		if(Integer.parseInt(String.valueOf(diffDays))<150){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
}
