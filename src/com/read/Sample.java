package com.read;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import com.aventstack.extentreports.ExtentTest;
import com.codoid.products.exception.FilloException;
import com.managers.ExecutionManager;

public class Sample implements Runnable {

	String Key = null;
	String Environment = null;
	ThreadLocal<ExtentTest> parentTest = null;
	public Sample(String Key,String Environment,ThreadLocal<ExtentTest> parentTest) throws IOException{
		this.Key=Key;
		this.Environment=Environment;
		this.parentTest = parentTest;
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException, FilloException, IOException{
		/*
		String SoapUI_Location = args[0];
		String TestSuite_Name = args[1];
		String API_Name = args[2];
		String ProjectLocation = args[3];
		String API_Key = args[4];
		String Environment = args[5];
		*/
		//Sample s = new Sample();
		//s.Execute("D:\\Goutham\\SoapUI\\bin", "TestData", "Test1", "C:\\Users\\Jeevan\\Desktop\\Soapui\\Sample.xml","TC_001","SIT");
		//s.Execute(SoapUI_Location, TestSuite_Name, API_Name, ProjectLocation,API_Key,Environment);
	
	}
	
	
	@Override
	public void run() {
		try {
			ExecutionManager.Execute("D:\\Goutham\\SoapUI\\bin", "TestData", "Test1", "C:\\Users\\Jeevan\\Desktop\\Soapui\\Sample.xml",Key,Environment,parentTest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
