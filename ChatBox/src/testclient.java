import java.net.InetAddress;
import java.net.UnknownHostException;

public class testclient {
	
	public static void main (String args[]) throws UnknownHostException {
		ConversationManager cm = new ConversationManager();
		ConversationUDP Cu = new ConversationUDP(false);
		
		ThreadInitConnexionsTCP T = new ThreadInitConnexionsTCP(InetAddress.getLocalHost());
		T.start();
		
		
		
		//allo
		//System.out.println(Cu.getownIP());
		//Cu.update_self("on change presquerien");
		//Cu.pseudo="testfinaldelamortquitue";
		//Cu.send_annuaire();
		/*cm.setports(1235,1234);
		cm.createconnectionclient();
		cm.initstreamclient();
		System.out.println("sending message");
		cm.sendmessage("test1234");
		cm.closeconnection();*/
	}
	
}
