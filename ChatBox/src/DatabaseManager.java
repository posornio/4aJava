import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
   //TODO : mettre un constructeur avec tous les attributs plutot que des var globales
	//TODO : ID est finalement le hash code int de l'adresse IP (hashCode() de InetAddress 
	static final String url = "jdbc:sqlite:src/test.db";
   private Connection conn = null;
	  

   public void dbinit () {
	   try{		
	    	// create a connection to the database
	    	  Class.forName("org.sqlite.JDBC");
	          this.conn = DriverManager.getConnection(url);
	          
	          System.out.println("Connection to SQLite \"users\" has been established.");
	          
	      } catch (SQLException e) {
	          System.out.println(e.getMessage());
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
   }
      
   
   
   public void createtablemessage () {
	   try {
		   Statement stmt = null;
		   stmt = conn.createStatement();
		   String sql = "CREATE TABLE IF NOT EXISTS message" +
                   "(\n	IDMESSAGE integer PRIMARY KEY,\n" +
                   " 	IDSENDER text NOT NULL,\n" + 
                   " 	IDRECV text NOT NULL,\n" + 
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
					"(\n IDUSERS text PRIMARY KEY,\n" +
					" 	LOGIN text\n)";
			stmt.executeUpdate(sql);
		}
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("TableUsers created successfully");
	}

   public void createtableports() {
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS ports" +
					"(\n PORTUTILISEE integer PRIMARY KEY,\n)";
			stmt.executeUpdate(sql);
		}
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("TablePorts created successfully");
	}
   
   
   
	public int insertport() {
		String getmaxportSql ="SELECT MAX(PORTUTILISEE) as max_ports\n" +
				"FROM ports;";
		int ret=0;
		try ( PreparedStatement pstmt  = conn.prepareStatement(getmaxportSql)){

			// set the value//
			ResultSet rs  = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				ret = rs.getInt("PORTUTILISEE");
			}
		} catch (SQLException e) {

		}
		ret+=1;
		String sql = "INSERT INTO port VALUES(?)";
		//(IDUSERS,LOGIN)
		try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, ret);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	return ret;}
   
   public void insertuser( String idUser,String login) {
		String sql = "INSERT INTO users VALUES(?,?)";
		//(IDUSERS,LOGIN)
		try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, idUser);
			pstmt.setString(2, login);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

   public void insertmessage(int idmessage, String idsender, String idreceiver, String contenu, Timestamp Datetimemessage) {
	   String sql = "INSERT INTO message VALUES(?,?,?,?,?)";

       try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1,idmessage );
           pstmt.setString(2,idsender );
           pstmt.setString(3,idreceiver );
           pstmt.setString(4, contenu);
           pstmt.setTimestamp(5,Datetimemessage);
           pstmt.executeUpdate();
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
   
   public void changerPseudo(String idUser, String newlogin){
		String sql = "UPDATE users SET LOGIN = ? WHERE IDUSERS = ?";
		//, WHERE IDUSERS = ?
		try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// set the corresponding param
			pstmt.setString(1, newlogin);
			pstmt.setString(2, idUser);
			// upDatetime
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
   
   public boolean IdExists(String idUser) {
	   String sql = "SELECT IDUSERS "
				+ "FROM users WHERE IDUSERS = ?";
		try ( PreparedStatement pstmt  = conn.prepareStatement(sql)){

			// set the value
			pstmt.setString(1,idUser);
			//
			ResultSet rs  = pstmt.executeQuery();

	   
			return rs.next();
		}
	    catch (SQLException e) {
			return false;
	    }
   }
   
   public boolean id_login_exists(String idUser,String Pseudo) {
	   String sql = "SELECT LOGIN "
				+ "FROM users WHERE IDUSERS = ? and LOGIN = ?";

		try ( PreparedStatement pstmt  = conn.prepareStatement(sql)){

			// set the value
			pstmt.setString(1,idUser);
			pstmt.setString(2, Pseudo);
			//
			ResultSet rs  = pstmt.executeQuery();

			return rs.next();
		}
	    catch (SQLException e) {
			return false;
	    }
   }
   
   
   public void getIdByLogin(String login){

		String id = getIdbyLoginString(login);
		if (id.equals("")) {
			System.out.println("Ce login n'existe pas dans l'annuaire");
		}
		else System.out.println(id);
	}

   
   public String getIdbyLoginString(String login) {
	   String sql = "SELECT IDUSERS "
				+ "FROM users WHERE LOGIN = ?";

		try ( PreparedStatement pstmt  = conn.prepareStatement(sql)){

			// set the value
			pstmt.setString(1,login);
			//
			ResultSet rs  = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				return rs.getString("IDUSERS");
			}
		} catch (SQLException e) {
			return "";
	}
		return "";
   }
   
   
   public ArrayList<String> getAnnuaireList(){
	   
		String sql = "SELECT LOGIN FROM users WHERE LOGIN <> ''";
		ArrayList<String> result = new ArrayList<String>();
		try (Statement stmt  = conn.createStatement();
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
   
   public int getMaxIdmessage(String myId, String theirID) {
	   String sql = "SELECT IDMESSAGE FROM message WHERE (IDSENDER = ? AND IDRECV = ?) OR (IDSENDER = ? AND IDRECV = ?)";
	   /*pstmt.setInt(4, myId);;
       pstmt.setInt(3, theirID);*/
	   //tetst pour jenkins
	   return 1;
   }
   
   
   
   //PAS BON (que idmessage et que dans un sens sender receiver
   public void selectHistorywithX(String myId, String theirID){
	   
       String sql = "SELECT IDMESSAGE, CONTENU, DATEMESSAGE FROM message WHERE IDSENDER = ? AND IDRECV = ?";
       //idmessage, idsender, idreceiver, contenu, Datetimemessage
       
       try ( PreparedStatement pstmt  = conn.prepareStatement(sql)){
    	   
              // set the value
              pstmt.setString(1, myId);;
              pstmt.setString(2, theirID);
          
              ResultSet rs  = pstmt.executeQuery();
              // loop through the result set
              while (rs.next()) {
                  System.out.println(rs.getInt("IDMESSAGE"));
              }
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }

   }
   
   
   public ArrayList<Message> ArrayHistorywithX(String myId, String theirID){
		ArrayList<Message> result = new ArrayList<Message>();

		String sql = "SELECT IDSENDER, IDRECV, CONTENU, DATEMESSAGE FROM message WHERE (IDSENDER = ? AND IDRECV = ?) OR (IDSENDER = ? AND IDRECV = ?)";
		//idmessage, idsender, idreceiver, contenu, Datetimemessage

		try ( PreparedStatement pstmt  = conn.prepareStatement(sql)){

			// set the value
			pstmt.setString(1, myId);;
			pstmt.setString(2, theirID);
			pstmt.setString(3, theirID);;
			pstmt.setString(4, myId);

			ResultSet rs  = pstmt.executeQuery();
			// loop through the result set
			while (rs.next()) {
				String contenu=rs.getString("CONTENU");
				String sender= rs.getString("IDSENDER");
				String recv = rs.getString("IDRECV");
				Timestamp tsI= rs.getTimestamp("DATEMESSAGE");
				Message messI = new Message(contenu,sender,recv,tsI);
				result.add(messI);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return result;
	}
	public class Message{
		String contenu;
		String idSender;
		String idRecv;
		Timestamp date;

		public Message(String contenu, String idSender, String idRecv, Timestamp date) {
			this.contenu = contenu;
			this.idSender = idSender;
			this.idRecv = idRecv;
			this.date = date;
		}
		
		public String toString() {
			return this.idSender + " " + this.contenu + " " + this.idRecv + " " + this.date; 
		}
	}
   
   public static void main(String[] args) {
	   DatabaseManager Db = new DatabaseManager();
	   //
	   AccountManager Am = new AccountManager();

	   Db.dbinit();  
	   Db.createtableusers();
	   Db.createtablemessage();
	   //System.out.println("Table message created successfully");
	   Timestamp D = new Timestamp(System.currentTimeMillis());
	   Db.insertmessage(1, "5", "6", "Coucou premier message", D);
	   Db.insertuser("5", "xxRaveauxx");
	   Db.insertuser("6", "xxOsornioxx");
	   Db.insertuser("127.0.0.98","");

	   //System.out.println("Datetime OK and message added to DB");
	   System.out.println(Db.ArrayHistorywithX("5", "6"));
	   Db.getAnnuaire();
	   Db.changerPseudo("5", "xxMatthisxx");
	   Db.getAnnuaire();
	   Am.changerpseudo("5", "zebicamarche la !");
	   Db.getAnnuaire();
   }
}