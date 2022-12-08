import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ConversationUDP {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    private String pseudo;

    public ConversationUDP(boolean isserver) {
    	try {
    		if (isserver) {
        		socket = new DatagramSocket(3456);

    		}
    		else
    		{
    			socket = new DatagramSocket();
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Couldn't build DatagramSocket with " + e);
    	}
    }

    public void update_self(String login) {
    	this.pseudo=login;
    }
    
    
    //Process va mettre à jour son annuaire avec le login et ip reçu et va renvoyer un boolean 
    //qui dit si oui ou non l'update a fonctionné
    public boolean process(String log,InetAddress addr) {
    	System.out.println("we process");
    	String addr_s = addr.toString();
 	   	DatabaseManager Db = new DatabaseManager();
 	   	AccountManager Am = new AccountManager();
		System.out.println("we're here");
    	if (Db.IdExists(addr_s)) {
    		System.out.println("we're here");

    		//il s'agit d'une connecxion/changement pseudo c pareil
    		Am.seconnecter(addr_s, log);
    	}
    	else {
    		//il s'agit d'un nouveau compte
    		Am.createaccount(addr_s, log);
    	}
    	return Db.id_login_exists(addr_s, log);
    }
    
    public void receive_annuraire() {
    	
    	running = true;

    	try {
    	
        while (running) {
        	DatagramPacket packet  = new DatagramPacket(buf, buf.length);
        	socket.receive(packet);
        	InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String login_to_process = new String(packet.getData(), 0, packet.getLength());
            if (process(login_to_process,address)) {
        		System.out.println("we're here");
                socket.send(packet);
        		System.out.println("we sent");

            }
            else {
            	byte[] buf2=new byte[256];
            	DatagramPacket packet2  = new DatagramPacket(buf2, buf2.length);
                socket.send(packet2);
            }
            
            //Si jamais on update bien dans l'annuaire on renvoie le même paquet que celui reçu
            //Sinon, on envoie un paquet vide signifiant que l'update n'était pas nécessaire ou mauvais
        }
    	}
    	catch (Exception e) {
    		System.out.println("Could not receive Annuary with " + e);
    	}
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
    
    public void send_annuaire() {
    	try {
    		System.out.println(getBroadcast());
    		InetAddress ia = InetAddress.getByName(getBroadcast());
    		System.out.println("we're here2");
        	buf = pseudo.getBytes();
    		System.out.println("we're here3");
        	DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ia, 3456);
    		System.out.println("we're here4");
        	socket.send(DpSend);
    	}
    	catch (Exception e)
    	{
    		System.out.println("Could not send annuary with " + e);
    	}
    }
    
       
    
    
}
    