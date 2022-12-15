
public class testclient {
	
	public static void main (String args[]) {
		ConversationManager cm = new ConversationManager();
		ConversationUDP Cu = new ConversationUDP(false);

		//allo
		System.out.println(Cu.getownIP());
		Cu.update_self("12345");
		Cu.send_annuaire();
		/*cm.setports(1235,1234);
		cm.createconnectionclient();
		cm.initstreamclient();
		System.out.println("sending message");
		cm.sendmessage("test1234");
		cm.closeconnection();*/
	}
	
}
