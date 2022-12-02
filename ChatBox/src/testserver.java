
public class testserver {

	public static void main (String args[]) {
		ConversationManager cm = new ConversationManager();
		cm.setports(1234,1235);
		cm.createconnectionserver();
		cm.initstreamserv();
		cm.printrecvmessage();
		cm.closeconnection();
	}

}
