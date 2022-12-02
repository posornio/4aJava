
public class testclient {
	
	public static void main (String args[]) {
		ConversationManager cm = new ConversationManager();
		cm.setports(1235,1234);
		cm.createconnectionclient();
		cm.initstreamclient();
		System.out.println("sending message");
		cm.sendmessage("test1234");
		cm.closeconnection();
	}
	
}
