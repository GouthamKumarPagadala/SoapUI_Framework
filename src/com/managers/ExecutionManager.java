package com.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import com.aventstack.extentreports.ExtentTest;

public class ExecutionManager {

	public static void Execute(String SoapUILocation, String Testsuite, String TestCase ,String ProjectFile,String API_Key, String Environment,ThreadLocal<ExtentTest> parentTest) throws IOException, ParseException{
		 String cmd = SoapUILocation+"\\testrunner.bat -s"+Testsuite+" -c"+TestCase+" -I "+ProjectFile+ " -PAPI_Name="+TestCase +" -PAPI_Key="+API_Key+ " -PEnvironment="+Environment;
		 System.out.println(cmd);
		 if(DataManager.isValidDate()){
			 Process p = Runtime.getRuntime().exec(cmd);
			 BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			 BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	         String s = null;
		     // read the output from the command
		     System.out.println("Here is the standard output of the command:\n");
		     
		     while ((s = stdInput.readLine()) != null) {
		         System.out.println(s);
		         if(s.contains("[log]")){
		        	 if(s.contains("API Name :")){
		        		 parentTest.get().info(s);
		        	 }
		        	 if(s.contains("TestCaseStatus :")){
		        		 String Status = s.split("TestCaseStatus :")[1];
		        		 if(Status.contains("Passed")){
		        			 System.out.println("Pass");
		        		 }else if(Status.contains("Passed")){
		        			 System.out.println("Fail");
		        		 }
		        	 }
		        	 if(s.contains("Test Step : ")){
		        		 if(s.contains("[VALID]")){
		        			 parentTest.get().pass("Test Step : " + s.split("Test Step : ")[1]);
		        		 }
		        		 if(s.contains("[FAILED]")){
		        			 parentTest.get().fail("Test Step : " + s.split("Test Step : ")[1]);
		        		 }
		        	 }
		        	 if(s.contains("Log_file :")){
		        	 	 String Path = s.split("Log_file :")[1];
			        	 System.out.println(Path);
			        	 parentTest.get().info("<a href ='"+Path+"'>Detailed Log with request and respose");
		        	 }
		        	 if(s.contains("Log_screenshot :")){
		        		 String Path = s.split("Log_screenshot :")[1];
			        	 System.out.println(Path);
			        	 parentTest.get().addScreenCaptureFromPath("./Screenshots/"+Path);
		        	 }
		         }
		         if(s.contains("ERROR [SoapUI]")){
		        	 System.out.println(s);
		         }
		     }
		     // read any errors from the attempted command
		     System.out.println("Here is the standard error of the command (if any):\n");
		     while ((s = stdError.readLine()) != null) {
		         System.out.println(s);
		     }
	 
		 }else{
			 System.exit(0);
		 }
		 	     //
	}

}
