package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Stellt Verbindung zur Datenbank her
 * @author pascal
 *
 */
public class SimpleQuery { 
	  
	 public static Connection connect(){ 

	     // Diese Eintraege werden zum 
	     // Verbindungsaufbau benoetigt. 
	     final String hostname = "localhost"; 
	     final String port = "3306"; 
	     final String dbname = "Medienverleih"; 
	     final String user = "pascal"; 
	     final String password = ""; 
		
	     Connection con = null; 
	     
	     try {  
		    String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname; 
		    con = DriverManager.getConnection(url, user, password); 
		   
		    
	     } 
	     catch (SQLException sqle) { 
	         System.out.println("SQLException: " + sqle.getMessage()); 
	         System.out.println("SQLState: " + sqle.getSQLState()); 
	         System.out.println("VendorError: " + sqle.getErrorCode()); 
	         sqle.printStackTrace(); 
	     } 
	     return con;
			
	  } // ende: public static void main() 
	 
	} // ende: public class SimpleQuery 
