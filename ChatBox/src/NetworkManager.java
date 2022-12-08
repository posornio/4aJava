
public class NetworkManager {

	public boolean checkunicitepseudo(String login) {
		DatabaseManager Db = new DatabaseManager();
		return (Db.getIdbyLoginString(login).equals(""));
	}
	
}
