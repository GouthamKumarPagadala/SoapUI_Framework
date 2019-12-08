package com.read;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.managers.DataManager;

public class TestData {
	public static Hashtable<String, String> data_values = new Hashtable<String, String>();
	public static Hashtable<String,String> dynamic_values = new Hashtable<String,String>();

	public static Hashtable<String, String> getDynamic_values() {
		return dynamic_values;
	}
	
	public static String Retrieve(String key) {
		return dynamic_values.get(key);
	}

	public static void Store(String key, String value) {
		dynamic_values.put(key, value);
	}
	
	public Hashtable<String, String> getCommon_Data(String ExcelFile_Location,String DataBindingKey,String DataBindingValue, String ScreenName)
			throws ClassNotFoundException, SQLException, FilloException, ParseException {
		if(DataManager.isValidDate()){
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(ExcelFile_Location);
			String Data_Query = "SELECT * FROM " + ScreenName + " Where "+DataBindingKey+"='" + DataBindingValue +"'";
			Recordset Data_RS = connection.executeQuery(Data_Query);
			List<String> cloumns = new ArrayList<String>();
			cloumns = Data_RS.getFieldNames();
			for (int i = 1; i < cloumns.size(); i++) {
				Data_RS.next();
				String columnName = cloumns.get(i);
				String columnValue = Data_RS.getField(columnName);
				if (!(columnValue == "")) {
					data_values.put(columnName, columnValue);
				}
			}
			Data_RS.close();
			connection.close();
			
		}
		return data_values;
	}

	public Hashtable<String, String> getData(String ExcelFile_Location,String DataBindingKey,String DataBindingValue, String ScreenName)
			throws ClassNotFoundException, SQLException, FilloException, ParseException {
		if(DataManager.isValidDate()){
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(ExcelFile_Location);
			String Data_Query = "SELECT * FROM " + ScreenName + " Where "+DataBindingKey+"='" + DataBindingValue + "' and Control='1'";
			Recordset Data_RS = connection.executeQuery(Data_Query);
			List<String> cloumns = new ArrayList<String>();
			cloumns = Data_RS.getFieldNames();
			for (int i = 1; i < cloumns.size(); i++) {
				Data_RS.next();
				String columnName = cloumns.get(i);
				String columnValue = Data_RS.getField(columnName);
				if (!(columnValue == "")) {
					data_values.put(columnName, columnValue);
				}
			}
			Data_RS.close();
			connection.close();
	
		}
		return data_values;
	}

	public  Hashtable<String, String> getController_Steps(String ExcelFile_Location,String DataBinder, String ScreenName)
			throws ClassNotFoundException, SQLException, FilloException, ParseException {
		if(DataManager.isValidDate()){
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(ExcelFile_Location);
			String Data_Query = "SELECT * FROM " + ScreenName + " Where API_Name='" + DataBinder + "' and Control='1'";
			Recordset Data_RS = connection.executeQuery(Data_Query);
			List<String> cloumns = new ArrayList<String>();
			cloumns = Data_RS.getFieldNames();
			for (int i = 1; i < cloumns.size(); i++) {
				Data_RS.next();
				String columnName = cloumns.get(i);
				String columnValue = Data_RS.getField(columnName);
				if (!(columnValue == "")) {
					data_values.put(columnName, columnValue);
				}
			}
			Data_RS.close();
			connection.close();
	
		}
		return data_values;
	}

	public Integer getRowCount(String ExcelFile_Location, String ScreenName) throws FilloException, ParseException{
		int j = 0;
		if(DataManager.isValidDate()){
			
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(ExcelFile_Location);
			String Data_Query = "SELECT * FROM " + ScreenName + " Where Control='1'";
			Recordset Data_RS = connection.executeQuery(Data_Query);
			while(Data_RS.next()){
				j=j+1;
			}
			Data_RS.close();
			connection.close();
		}
		
		return j;
		
	}

 	public static synchronized Hashtable<String, String> getDriverDBData(String ColumnName,String DataBinder, String ScreenName)
			throws ClassNotFoundException, SQLException, FilloException, ParseException {
 		if(DataManager.isValidDate()){
 			String Current_Directory = System.getProperty("user.dir");
 			Fillo fillo = new Fillo();
 			Connection connection = fillo.getConnection(Current_Directory + "\\Database\\Driver-DB.xlsx");
 			String Data_Query = "SELECT * FROM " + ScreenName + " Where "+ColumnName+"='" + DataBinder + "'";
 			// System.out.println(Data_Query);
 			Recordset Data_RS = connection.executeQuery(Data_Query);
 			List<String> cloumns = new ArrayList<String>();

 			cloumns = Data_RS.getFieldNames();

 			for (int i = 1; i < cloumns.size(); i++) {
 				Data_RS.next();
 				String columnName = cloumns.get(i);
 				String columnValue = Data_RS.getField(columnName);
 				// System.out.println("Store Data "+ columnName + " : " +
 				// columnValue );
 				if (!(columnValue == "")) {
 					data_values.put(columnName, columnValue);
 				}

 			}
 			connection.close();
 		}
		return data_values;
	}

	
	public static synchronized void  Write(String sheetname,String setcolname,String setvalue,String col,String val) throws Exception
	{
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection("./Output.xlsx");
		String strQuery="Update "+ sheetname +" Set "+ setcolname +" ='"+ setvalue +"' Where "+ col +" ='"+ val+"'" ;
		 
		connection.executeUpdate(strQuery);
		 
		connection.close();
		
		
	}
	
	public static synchronized void  Write_Execution_Status(String Update_TO_sheetname,String To_Column,String To_Column_with_value,String Where_Column,String Where_Key) throws Exception
	{
		
		if(!(new File(".//Result//"+new SimpleDateFormat("dd_MMMMM_yyy").format(new Date())+"//Execution_Status.xlsx").exists())) {
			FileUtils.copyFile(new File(System.getProperty("user.dir")+"//Database//Driver-DB.xlsx"), new File("./Result/"+new SimpleDateFormat("dd_MMMMM_yyy").format(new Date())+"//Execution_status.xlsx"));
		}
		
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(".//Result//"+new SimpleDateFormat("dd_MMMMM_yyy").format(new Date())+"//Execution_Status.xlsx");
		String strQuery="Update "+ Update_TO_sheetname +" Set "+ To_Column +" ='"+ To_Column_with_value +"' Where "+ Where_Column +" ='"+ Where_Key+"'" ;
		connection.executeUpdate(strQuery);
		connection.close();
	}
	
	public static synchronized void  Write_Execution_Status(String Update_TO_sheetname,String To_Column,String To_Column_with_value,String Where_Column_0NE,String Where_Key_ONE,String Where_Column_Two,String Where_Key_Two) throws Exception
	{
		if(!(new File(".//Result//"+new SimpleDateFormat("dd_MMMMM_yyy").format(new Date())+"//Execution_Status.xlsx").exists())) {
			FileUtils.copyFile(new File(System.getProperty("user.dir")+"//Database//Driver-DB.xlsx"), new File("./Result/"+new SimpleDateFormat("dd_MMMMM_yyy").format(new Date())+"//Execution_status.xlsx"));
		}
		
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(".//Result//"+new SimpleDateFormat("dd_MMMMM_yyy").format(new Date())+"//Execution_Status.xlsx");
		String strQuery="Update "+ Update_TO_sheetname +" Set "+ To_Column +" ='"+ To_Column_with_value +"' Where "+ Where_Column_0NE +" ='"+ Where_Key_ONE+"' AND "+Where_Column_Two+ "='"+Where_Key_Two+"'" ;
		 
		connection.executeUpdate(strQuery);
		 
		connection.close();
		
		
	}

}
