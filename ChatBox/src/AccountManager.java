

public class AccountManager {

	public void seconnecter(int MyId, String login) {
		DatabaseManager Db = new DatabaseManager();
		Db.changerPseudo(MyId,login);
	}
	
	public void sedeconnecter(int MyId) {
		DatabaseManager Db = new DatabaseManager();
		Db.changerPseudo(MyId,"");
	}
	
	public void createaccount(int MyId, String login) {
		DatabaseManager Db = new DatabaseManager();
		Db.insertuser(MyId, login);
	}
	
}
