import java.sql.*;

public class DatabaseManager {
	//TODO : mettre un constructeur avec tous les attributs plutot que des var globales

	static final String urlusers = "jdbc:sqlite:src/testusers.db";
	static final String urlmessage = "jdbc:sqlite:src/testmessage.db";
	Connection conn = null;


	private Connection connectionusers () {
		try{
			// create a connection to the database
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(urlusers);

			System.out.println("Connection to SQLite \"users\" has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	private Connection connectionmessage () {
		try{
			// create a connection to the database
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(urlmessage);

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
		System.out.println("Tablemessage created successfully");
	}


	public void createtableusers() {
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			String sql = "CREATE TABLE Users " +
					"(IdUsers INT PRIMARY KEY     NOT NULL," +
					" Login           CHAR(50)    NOT NULL, " +;
			stmt.executeUpdate(sql);
		}
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("TableUsers created successfully");
	}


	public void insertuser( int idUser,String login) {
		String sql = "INSERT INTO Users(name,idUser) VALUES(?,?)";

		try (Connection conn = this.connectionusers();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, idUser);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void changerPseudo(int idUser, String newlogin){
		String sql = "UPDATE Users SET Login = ? , "
				+ "WHERE idUsers = ?";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// set the corresponding param
			pstmt.setString(1, newlogin);
			pstmt.setInt(2, idUser);
			// update
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void getIdByLogin(String login){
		String sql = "SELECT Login "
				+ "FROM Users WHERE idUsers = ?";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt  = conn.prepareStatement(sql)){

			// set the value
			pstmt.setString(1,login);
			//
			ResultSet rs  = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("Login"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());

	}}

	public void getAnnuaire(){
		String sql = "SELECT Login FROM Users";

		try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
		ResultSet rs    = stmt.executeQuery(sql)){

		// loop through the result set
		while (rs.next()) {
		System.out.println(
		rs.getString("Login") );
		}
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		}


	public static void main(String[] args) {
		DatabaseManager Db = new DatabaseManager();
		Db.connectionusers();
		Db.createtableusers();
		// Open a connection




	}
}