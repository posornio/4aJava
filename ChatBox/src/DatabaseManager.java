import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
   //TODO : mettre un constructeur avec tous les attributs plutot que des var globales
	
	static final String url = "jdbc:sqlite:src/test.db";
   Connection conn = null;
	  

   public Connection connectionusers () {
	   try{		
	    	// create a connection to the database
	    	  Class.forName("org.sqlite.JDBC");
	          conn = DriverManager.getConnection(url);
	          
	          System.out.println("Connection to SQLite \"users\" has been established.");
	          
	      } catch (SQLException e) {
	          System.out.println(e.getMessage());
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   return conn;
   }
   
   public Connection connectionmessage () {
	   try{		
	    	// create a connection to the database
	    	  Class.forName("org.sqlite.JDBC");
	          conn = DriverManager.getConnection(url);
	          
	          System.out.println("Connection to SQLite \"message\" has been established.");
	          
	      } catch (SQLException e) {
	          System.out.println(e.getMessage());
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   return conn;
   }
   
   
   
   public void createtablemessage () {
	   try {
		   Statement stmt = null;
		   stmt = conn.createStatement();
		   String sql = "CREATE TABLE IF NOT EXISTS message" +
                   "(\n	IDMESSAGE integer PRIMARY KEY,\n" +
                   " 	IDSENDER integer NOT NULL,\n" + 
                   " 	IDRECV integer NOT NULL,\n" + 
                   " 	CONTENU  text,\n" + 
                   "	DATEMESSAGE Timestamp\n)"; 
		   stmt.executeUpdate(sql);
	   }
	   catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	   }
		System.out.println("TableMessage created successfully");

   }
   
   public void createtableusers() {
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS users" +
					"(\n IDUSERS integer PRIMARY KEY,\n" +
					" 	LOGIN text\n)";
			stmt.executeUpdate(sql);
		}
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("TableUsers created successfully");
	}

  
   
   public void insertuser( int idUser,String login) {
		String sql = "INSERT INTO users VALUES(?,?)";
		//(IDUSERS,LOGIN)
		try (Connection conn = this.connectionusers();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idUser);
			pstmt.setString(2, login);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

   public void insertmessage(int idmessage, int idsender, int idreceiver, String contenu, Timestamp Datetimemessage) {
	   String sql = "INSERT INTO message VALUES(?,?,?,?,?)";

       try (Connection conn = this.connectionmessage();
               PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1,idmessage );
           pstmt.setInt(2,idsender );
           pstmt.setInt(3,idreceiver );
           pstmt.setString(4, contenu);
           pstmt.setTimestamp(5,Datetimemessage);
           pstmt.executeUpdate();
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
   
   public void changerPseudo(int idUser, String newlogin){
		String sql = "UPDATE users SET LOGIN = ? WHERE IDUSERS = ?";
		//, WHERE IDUSERS = ?
		try (Connection conn = this.connectionusers();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// set the corresponding param
			pstmt.setString(1, newlogin);
			pstmt.setInt(2, idUser);
			// upDatetime
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
   
   public void getIdByLogin(String login){

		int id = getIdbyLoginInt(login);
		if (id <0 ) {
			System.out.println("Ce login n'existe pas dans l'annuaire");
		}
		else System.out.println(id);
	}

   
   public int getIdbyLoginInt(String login) {
	   String sql = "SELECT IDUSERS "
				+ "FROM users WHERE LOGIN = ?";

		try (Connection conn = this.connectionusers();
			 PreparedStatement pstmt  = conn.prepareStatement(sql)){

			// set the value
			pstmt.setString(1,login);
			//
			ResultSet rs  = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				return rs.getInt("IDUSERS");
			}
		} catch (SQLException e) {
			return -1;
	}
		return -1;
   }
   
   
   public ArrayList<String> getAnnuaireList(){
	   
		String sql = "SELECT LOGIN FROM users";
		ArrayList<String> result = new ArrayList<String>();
		try (Connection conn = this.connectionusers();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql)){

			// loop through the result set
		while (rs.next()) {
			result.add(rs.getString("LOGIN"));
			}
		} 
		catch (SQLException e) {
		}
		return result;
   }
   
   public void getAnnuaire(){
	   if (getAnnuaireList().isEmpty()) {
			System.out.println("Annuaire Vide.");
	   }
	   else	System.out.println(getAnnuaireList());
	}
   

   
   
   
   public void selectHistorywithX(int myId, int theirID){
	   
       String sql = "SELECT IDMESSAGE, CONTENU, DATEMESSAGE FROM message WHERE IDSENDER = ? AND IDRECV = ?";
       //idmessage, idsender, idreceiver, contenu, Datetimemessage
       
       try (Connection conn = this.connectionmessage();
               PreparedStatement pstmt  = conn.prepareStatement(sql)){
    	   
              // set the value
              pstmt.setInt(1, myId);;
              pstmt.setInt(2, theirID);
              ResultSet rs  = pstmt.executeQuery();
              // loop through the result set
              while (rs.next()) {
                  System.out.println(rs.getInt("IDMESSAGE") +  "\t" + 
                                     rs.getString("CONTENU") + "\t" +
                                     rs.getTimestamp("DATEMESSAGE"));
              }
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }

   }
   
   public static void main(String[] args) {
	   DatabaseManager Db = new DatabaseManager();
	   //
	   Db.connectionusers();
	   Db.connectionmessage();  
	   Db.createtableusers();
	   Db.createtablemessage();
	   //Db.createtableusers();
	   //System.out.println("Table message created successfully");
	   Timestamp D = new Timestamp(System.currentTimeMillis());
	   System.out.println(D);
	   Db.insertmessage(1, 1, 2, "Coucou premier message", D);
	   //Db.insertuser(3, "xxRaveauxx");
	   //Db.insertuser(4, "xxOsornioxx");

	   //System.out.println("Datetime OK and message added to DB");
	   Db.selectHistorywithX(1, 2);
	   Db.getAnnuaire();
	   Db.changerPseudo(1, "xxMatthosxx");
	   Db.getAnnuaire();
	   Db.getIdByLogin("xxMatthisxx");
	   Db.getAnnuaire();
   }
}