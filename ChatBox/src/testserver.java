import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class testserver {
	
    public static String getBroadcast2(){
    InetAddress found_bcast_address=null;
     System.setProperty("java.net.preferIPv4Stack", "true"); 
        try
        {
          Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces();
          while (niEnum.hasMoreElements())
          {
            NetworkInterface ni = niEnum.nextElement();
            if(!ni.isLoopback()){
                for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses())
                {

                  found_bcast_address = interfaceAddress.getBroadcast();
                  //found_bcast_address = found_bcast_address.substring(1);

                }
            }
          }
        }  
        catch (SocketException e)
        {
          e.printStackTrace();
        }
        String bcast = found_bcast_address.toString();
        bcast = bcast.substring(1);
        return bcast;
}
    
    public static String getBroadcast(){
    String found_bcast_address=null;
     System.setProperty("java.net.preferIPv4Stack", "true"); 
        try
        {
          Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces();
          while (niEnum.hasMoreElements())
          {
            NetworkInterface ni = niEnum.nextElement();
            if(!ni.isLoopback()){
                for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses())
                {

                  found_bcast_address = interfaceAddress.getBroadcast().toString();
                  found_bcast_address = found_bcast_address.substring(1);

                }
            }
          }
        }
        catch (SocketException e)
        {
          e.printStackTrace();
        }

        return found_bcast_address;
    }

	public static void main (String args[]) {
		ConversationManager cm = new ConversationManager();
		ConversationUDP Cu = new ConversationUDP(true);
		System.out.println(getBroadcast2());
		Cu.receive_annuraire();
		cm.setports(1234,1235);
		cm.createconnectionserver();
		cm.initstreamserv();
		cm.printrecvmessage();
		cm.closeconnection();
	}

}
