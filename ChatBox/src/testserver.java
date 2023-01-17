public class testserver {
	
    

	public static void main (String args[]) {
		DatabaseManager Db = new DatabaseManager();
		ConversationManager cm = new ConversationManager();
		ConversationUDP Cu = new ConversationUDP(true);
		ThreadManager T = new ThreadManager();
		//ThreadEcouteConnexionsTCP T2 = new ThreadEcouteConnexionsTCP();
		//System.out.println(getBroadcast2());
		Db.dbinit();
		//T2.start();
		//Cu.receive_annuaire();
		//Db.getAnnuaire(); 
		/*cm.setports(1234,1235);
		cm.createconnectionserver();
		cm.initstreamserv();
		cm.printrecvmessage();
		cm.closeconnection();*/
	}

}
