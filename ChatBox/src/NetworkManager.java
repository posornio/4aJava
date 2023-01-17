
public class NetworkManager {


	public boolean checkunicitepseudo(String login) {
		DatabaseManager Db = new DatabaseManager();
		Db.dbinit();
		if (login.equals("")) {
			return true;
		}
		return (Db.getIdbyLoginString(login).equals(""));
		}

	}
	

