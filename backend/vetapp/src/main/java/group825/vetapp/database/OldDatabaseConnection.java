package group825.vetapp2.database;
import java.sql.*;
import java.util.ArrayList;

import group825.vetapp2.animal.DictionaryLetter;

public class OldDatabaseConnection {
	
	/**
	 * url to the location of the MySQL database
	 */
	String url = "jdbc:mysql://localhost:3306/vetapp";
	
	/**
	 * User username with access to MySQL database 
	 */
	String uname = "dummyRootUser";
	
	/**
	 * User password 
	 */
	String pass = "password";
	
	
	/**
	 * Connection to MySQL database
	 */
	Connection con;
	
	/**
	 * placeholder string which separates every value within every tuple returned from the database
	 */
	String splitPlaceholder = "--break-- ";
	
	
	/**
	 * @param query = SQL query to retrieve data from database 
	 * @return String containing all the tuples returned where each tuple is separated by "splitPlaceholder" variable
	 * @throws Exception SQL Exception
	 */
	public String getRows(String query) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, uname, pass);
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		String allRows = "";
		
		while(rs.next()) {
			
			String rowData = "";
			 ResultSetMetaData metadata = rs.getMetaData();
			 int columnCount = metadata.getColumnCount(); 
//			 System.out.println("columnCount:: "+columnCount);
			 
			 for (int i = 1; i <= columnCount; i++) {
				 rowData = rowData + splitPlaceholder + rs.getString(i) ;
			    }
			 rowData = rowData.substring(splitPlaceholder.length());
			 
//			 System.out.println(rowData);
			 allRows = allRows + rowData +"\n";
		}		
//		System.out.println(">>>>>>allRows: "+allRows);
//		System.out.println("\n\n\n\n\n");
		
		st.close();
		con.close();
		
		return allRows;
	}
	
	/**
	 * @param query = SQL query to retrieve data from database 
	 * @return ArrayList<String> where every item in the ArrayList is a String containing the data from one tuple 
	 * 				returned that is separated by "splitPlaceholder" variable
	 * @throws Exception = SQL Exception
	 */
	public ArrayList<String> getResponseArrayList(String query) throws Exception{
		String allRows = getRows(query);
		ArrayList<String> results = new ArrayList<String>();
		for (String row: allRows.split("\n")) {results.add(row);}
		return results;
	}
	
	
	/**
	 * @param query = SQL query to update data from database
	 * @return int = number of rows affected by the SQL query
	 * @throws Exception = SQL Exception
	 */
	public int manipulateRows(String query) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, uname, pass);
		
		Statement st = con.createStatement();
		int numRowsAffected = st.executeUpdate(query);
		
		st.close();
		con.close();
		
		return numRowsAffected;
	}
	
	/**
	 * @param returnedDataStr = String containing all the tuples returned from the database 
	 * 					where every tuple is separated by \n in string and every data member
	 * 					is separated by the "splitPlaceholder" variable
	 * @param column = int index of the attribute that is desired
	 * @return ArrayList<String> = ArrayList where every String is the value for one attribute for one tuple
	 */
	public ArrayList<String> parseInfoReturned(String returnedDataStr, int column) {
		ArrayList<String> temp = new ArrayList<String>();
		for(String row: returnedDataStr.split("\n")) {
//			System.out.println(row);
			for (String individual: row.split(this.splitPlaceholder))
			{System.out.println(individual);}		
			String cell = row.split(this.splitPlaceholder)[column];
//			System.out.println(cell);
			temp.add(cell.toLowerCase());
		}
		return temp;
	}


	/**
	 * @return the splitPlaceholder variable used to separate every attribute within one tuple
	 */
	public String getSplitPlaceholder() {
		return splitPlaceholder;
	}
	
	
}
