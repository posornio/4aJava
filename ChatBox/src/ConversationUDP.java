import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
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
    	DatabaseManager Db = new DatabaseManager();
    	AccountManager am = new AccountManager();
    	Db.dbinit();
    	am.changerpseudo(getownIP(), login);
    	Db.getAnnuaire();
    	if (Db.id_login_exists(Db.getIdbyLoginString(login),login)) {
        	this.pseudo=login;
    	}
    	else {
    		am.createaccount(getownIP(), login);
    		this.pseudo=login;
    		Db.getAnnuaire();
    	}
    }
    
    
    //Process va mettre à jour son annuaire avec le login et ip reçu et va renvoyer un boolean 
    //qui dit si oui ou non l'update a fonctionné
    public boolean process(String log,InetAddress addr) {
    	if (log.equals("all0oo")) {
    		System.out.println("we process");
    	}
    	String addr_s = addr.toString();
 	   	DatabaseManager Db = new DatabaseManager();
 	   	Db.dbinit();
 	   	AccountManager Am = new AccountManager();
 	   	System.out.println(log + " est le log et ca c'est l'addr :");
 	   	System.out.println(addr);
    	if (Db.IdExists(addr_s)) {

    		//il s'agit d'une connecxion/changement pseudo c pareil
    		System.out.println("on est dans le idexists de process ");
    		Am.seconnecter(addr_s, log);
    	}
    	else {
    		//il s'agit d'un nouveau compte
    		System.out.println("test");
    		Am.createaccount(addr_s, log);
    	}
    	Db.getAnnuaire();
    	return Db.id_login_exists(addr_s, log);
    }
    
    public void receive_annuraire() {

    	while (true) {
    	try {
    		byte[] bufs= new byte[256];
        	DatagramPacket packet  = new DatagramPacket(bufs, bufs.length);
        	socket.receive(packet);
        	InetAddress address = packet.getAddress();
            int port = packet.getPort();
            
            DatagramPacket packet_ack = new DatagramPacket(packet.getData(), packet.getLength(), address, port);
            System.out.println("Longueur de getlength est : " + packet.getLength());
            String str = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Longueur est : " + str.length());
            System.out.println("le login a process est :" + str);
            if (process(str,address)) {
                socket.send(packet_ack);
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
    	catch (Exception e) {
    		System.out.println("Could not receive Annuary with " + e);
    	}
    	}
    }
    
    public String getownIP() {
    	try {
    		Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        	while(e.hasMoreElements())
        	{
        	    NetworkInterface n = (NetworkInterface) e.nextElement();
        	    Enumeration<InetAddress> ee = n.getInetAddresses();
        	    int ii = 0;
        	    while (ee.hasMoreElements())
        	    {
        	    	if (ii==1) {
        	    		return ((InetAddress) ee.nextElement()).toString();
        	    	}
        	        InetAddress i = (InetAddress) ee.nextElement();
        	        ii++;
        	    }
        	}
    	}
    	catch (Exception e )
    	{
    		return "Error with :" + e;
    	}
    	return "on a test";
    }
    
    public static String getBroadcast(){
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
    
    public void send_annuaire() {
    	try {
    		System.out.println("adresse de broadacast :" + getBroadcast());
    		InetAddress ia = InetAddress.getByName(getBroadcast());
        	byte[] bufs = pseudo.getBytes();
        	System.out.println("la longueur est : " + bufs.length);
        	DatagramPacket DpSend = new DatagramPacket(bufs, bufs.length, ia, 3456);
        	socket.send(DpSend);
    	}
    	catch (Exception e)
    	{
    		System.out.println("Could not send annuary with " + e);
    	}
    }
    
       
    
    
}
    