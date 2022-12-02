
public class NetworkManager {

	public boolean checkunicitepseudo(String login) {
		DatabaseManager Db = new DatabaseManager();
		return (Db.getIdbyLoginInt(login)<0);
	}
	
}
