import java.sql.*;

public class DatabaseManager {
   //TODO : mettre un constructeur avec tous les attributs plutot que des var globales
	
	static final String url = "jdbc:sqlite:src/test.db";
   Connection conn = null;
	  

   public void connection () {
	   try{		
	    	// create a connection to the database
	    	  Class.forName("org.sqlite.JDBC");
	          conn = DriverManager.getConnection(url);
	          
	          System.out.println("Connection to SQLite has been established.");
	          
	      } catch (SQLException e) {
	          System.out.println(e.getMessage());
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
   }
   
   
   public void createtable () {
	   try {
		   Statement stmt = null;
		   stmt = conn.createStatement();
		   String sql = "CREATE TABLE MESSAGE " +
                   "(IDMESSAGE INT PRIMARY KEY     NOT NULL," +
                   " IDSENDER          INT    NOT NULL, " + 
                   " IDRECV            INT     NOT NULL, " + 
                   " CONTENU        CHAR(50), " + 
                   " DATEMESSAGE         DATE)"; 
		   stmt.executeUpdate(sql);
	   }
	   catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	   }
	      System.out.println("Table created successfully");
   }
   
   public static void main(String[] args) {
	   connection();
	   createtable();
      // Open a connection
	    
	   
	   
      
   }
}