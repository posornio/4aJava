

public class AccountManager {

	public void seconnecter(String MyId, String login) {
		DatabaseManager Db = new DatabaseManager();
		NetworkManager Nm = new NetworkManager();
		//if unique dans network manager alors : 
		if (Nm.checkunicitepseudo(login)) {
			Db.changerPseudo(MyId,login);
		}
		//sinon pas de changement
		else System.out.println("Pseudo deja pris, choisissez-en un nouveau");		
	}
	
	public void sedeconnecter(String MyId) {
		DatabaseManager Db = new DatabaseManager();
		Db.changerPseudo(MyId,"");
	}
	
	public void createaccount(String MyId, String login) {
		DatabaseManager Db = new DatabaseManager();
		NetworkManager Nm = new NetworkManager();
		//if unique dans network manager alors : 
		if (Nm.checkunicitepseudo(login)) {
			Db.insertuser(MyId,login);
		}
		//sinon pas de changement
		else System.out.println("Pseudo deja pris, choisissez-en un nouveau");
	}
	
}
