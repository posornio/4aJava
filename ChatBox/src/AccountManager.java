

public class AccountManager {

	public boolean seconnecter(String MyId, String login) {
		DatabaseManager Db = new DatabaseManager();
		Db.dbinit();
		NetworkManager Nm = new NetworkManager();
		//if unique dans network manager alors : 
		if (Nm.checkunicitepseudo(login)) {
			Db.changerPseudo(MyId,login);
			return true;
		}
		//sinon pas de changement
		else {System.out.println("Pseudo deja pris, choisissez-en un nouveau");
		return false;}
	}
	
	public void changerpseudo(String MyId, String login) {
		DatabaseManager Db = new DatabaseManager();
		Db.dbinit();
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
		Db.dbinit();
		Db.changerPseudo(MyId,"");
	}
	
	public void createaccount(String MyId, String login) {
		DatabaseManager Db = new DatabaseManager();
		Db.dbinit();
		NetworkManager Nm = new NetworkManager();
		//if unique dans network manager alors : 
		if (Nm.checkunicitepseudo(login)) {
			Db.insertuser(MyId,login);
		}
		//sinon pas de changement
		else System.out.println("Pseudo deja pris, choisissez-en un nouveau");
	}
	
}
