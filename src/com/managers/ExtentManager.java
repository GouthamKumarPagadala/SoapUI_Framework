package com.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports getInstance() throws ParseException, IOException {
		if (extent == null)
			createInstance("test-output","/extent.html");

		return extent;
	}

	public static synchronized ExtentReports createInstance(String Path,String fileName) throws ParseException, IOException {
		if(DataManager.isValidDate()){
			File report_file = new File(Path);
			if(!report_file.isDirectory()){
				report_file.mkdirs();
			}
			
			//System.out.println("Report_Path : "+Path+"//"+fileName);
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Path+"//"+fileName);
			
			htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);

			htmlReporter.config().setCSS(ReadFile("./Resources/CSS.css"));
			htmlReporter.config().setJS(ReadFile("./Resources/JAVASCRIPT.js"));
			
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setDocumentTitle(fileName);
			htmlReporter.config().setEncoding("utf-8");
			htmlReporter.config().setReportName(fileName);
			htmlReporter.setAppendExisting(true);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
		}
		
		return extent;
	}

  public static synchronized ExtentTest creatTest(ExtentReports er,String testName,String description) {
	  return er.createTest(testName,description);
  }
  
  public static String ReadFile(String FileLocation) throws IOException{
	  InputStream is = new FileInputStream(new File(FileLocation));
	  BufferedReader buf = new BufferedReader(new InputStreamReader(is));
	          
	  String line = buf.readLine();
	  StringBuilder sb = new StringBuilder();
	          
	  while(line != null){
	     sb.append(line).append("\n");
	     line = buf.readLine();
	  }
	  buf.close();
	  //System.out.println("Contents : " + sb.toString());
	  return sb.toString();
  }
	
}