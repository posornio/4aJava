import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class testserver {
	
    

	public static void main (String args[]) {
		DatabaseManager Db = new DatabaseManager();
		ConversationManager cm = new ConversationManager();
		ConversationUDP Cu = new ConversationUDP(true);
		//System.out.println(getBroadcast2());
		Db.dbinit();
		Cu.receive_annuraire();
		Db.getAnnuaire(); 
		/*cm.setports(1234,1235);
		cm.createconnectionserver();
		cm.initstreamserv();
		cm.printrecvmessage();
		cm.closeconnection();*/
	}

}
