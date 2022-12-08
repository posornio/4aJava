import java.net.InetAddress;

public class testserver {

	public static void main (String args[]) {
		ConversationManager cm = new ConversationManager();
		try {
			InetAddress ip = InetAddress.get;
			System.out.println(ip.getHostAddress());
			System.out.println(ip.getAddress());
			System.out.println(ip.hashCode());
		}
		catch (Exception e) {
			System.out.println("testfailed");
		}
		cm.setports(1234,1235);
		cm.createconnectionserver();
		cm.initstreamserv();
		cm.printrecvmessage();
		cm.closeconnection();
	}

}
