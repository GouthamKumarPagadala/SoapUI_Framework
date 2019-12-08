package com.driver;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.managers.ExecutionManager;
import com.managers.ExtentManager;

public class Run {
	private static ExtentReports extent= null;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static void main(String args[]) throws IOException, FilloException, ParseException{
		/*Thread[] threads = new Thread[3];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Sample("TC_00"+(i+1),"SIT"));
		}
		for (int i = 0; i < threads.length; i++) {
			 threads[i].start();
		}*/
		String SOAPUI_LOCATION = null;
		String PROJECT_LOCATION = null;
		String SUITE_NAME = null;
		String API_NAME =null;
		String ENVIRONMENT = null;
		String EXECUTION_MODE = null;
		boolean  manual = false;
		if(manual){
			SOAPUI_LOCATION = "D:\\Goutham\\SoapUI\\bin";
			PROJECT_LOCATION = "C:\\Users\\Jeevan\\Desktop\\Soapui\\Project_File";
			SUITE_NAME = "AISP";
			API_NAME ="Accounts";
			ENVIRONMENT = "SIT";
			EXECUTION_MODE = "Swagger_Validations";
		}else{
			SOAPUI_LOCATION = args[0];
			PROJECT_LOCATION = args[1];
			SUITE_NAME = args[2];
			API_NAME =args[3];
			ENVIRONMENT = args[4];
			EXECUTION_MODE = args[5];
		}
		extent = ExtentManager.createInstance(PROJECT_LOCATION+"//..//Results//"+new SimpleDateFormat("dd_MMMMM_yyy").format(new Date())+"//"+API_NAME , "Overall_Summary_Report"+".html");
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(PROJECT_LOCATION+"\\..\\Database\\"+SUITE_NAME+".xlsx");
		String Data_Query = "SELECT * FROM " + API_NAME + " Where Control='1' and "+EXECUTION_MODE+"='Yes'";
		Recordset Data_RS = connection.executeQuery(Data_Query);
		while(Data_RS.next()){
			ExtentTest parent = extent.createTest("<b>API Journey:</b> " +SUITE_NAME +"</br><b>API Name:</b> "+API_NAME+"</br><b>Test Case Id:</b> "+Data_RS.getField("TestCase_ID"),"TestCase Name : " + Data_RS.getField("TestCase_Name")); 
	        parentTest.set(parent);
			ExecutionManager.Execute(SOAPUI_LOCATION, SUITE_NAME, API_NAME, PROJECT_LOCATION+"\\"+SUITE_NAME+".xml", Data_RS.getField("TestCase_ID"), ENVIRONMENT,parentTest);
			extent.flush();
		}
		Data_RS.close();
		connection.close();
		
	}
}
