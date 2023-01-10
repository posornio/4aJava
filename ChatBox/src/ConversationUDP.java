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
    private static int port_recv_UDP = 3456;
    public String pseudo;

    public ConversationUDP(boolean isserver) {
    	try {
    		if (isserver) {
        		socket = new DatagramSocket(port_recv_UDP);

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
    	String addr_s = addr.toString();
 	   	DatabaseManager Db = new DatabaseManager();
 	   	Db.dbinit();
 	   	AccountManager Am = new AccountManager();
 	   	System.out.println(log + " est le log et ca c'est l'addr :" + addr);
 	   	if (Db.id_login_exists(addr_s, log)) {
 	   		System.out.println("entrée déja existante on ne renvoie pas");
 	   		return true;
 	   	}
 	   	else {
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
    	return false;
 	   	}
    }
    public void receive_annuaire() {
    	DatabaseManager Db = new DatabaseManager();
    	while (true) {
    	try {
    		byte[] bufs= new byte[256];
        	DatagramPacket packet  = new DatagramPacket(bufs, bufs.length);
        	socket.receive(packet);
        	InetAddress address = packet.getAddress();
        	// ici on écoute par convention tous sur 3456 et non pas sur le port d'envoi du packet 
            int port = packet.getPort();
    		System.out.println("on a receive");
        	if (address.toString().equals(getownIP())) {
        		System.out.println("c nou");
        		this.pseudo = new String(packet.getData(), 0, packet.getLength());
        		process(this.pseudo,address);
        	}
        	
        	else {
        	System.out.println(getownIP() + " est différent de : " + address.toString());
        	
            DatagramPacket packet_ack = new DatagramPacket(this.pseudo.getBytes(),this.pseudo.length() , address, port_recv_UDP);
            System.out.println("Longueur de getlength est : " + packet.getLength());
            String str = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Longueur est : " + str.length());
            System.out.println("le login a process est :" + str);
            System.out.println("le port est : " + port);
            if (process(str,address)) {
            	//Si le process est bon on peut renvoyer un ack
            	//ici on ne le fait pas par simplification du problème 
            	// et supposotion de non pertes dans notre rézo
                //socket.send(packet_ack);
        		//System.out.println("we sent");
            	System.out.println("on le connaît déja donc lui aussi, on n'envoie pas");
            	Db.dbinit();
            	Db.getAnnuaire();
            }
            else {
            	//ici process pas bon pas de ack mais demande de renvoi 
            	//ici pas traité
            	//byte[] buf2=new byte[256];
            	//DatagramPacket packet2  = new DatagramPacket(buf2, buf2.length);
                //socket.send(packet2);
            	socket.send(packet_ack);
                System.out.println("we sent");
            }
            //Si jamais on update bien dans l'annuaire on renvoie le même paquet que celui reçu
            //Sinon, on envoie un paquet vide signifiant que l'update n'était pas nécessaire ou mauvais
            
            //enfin, il faut renvoyer son propre état pour construire l'annuaire du nouveau connecté :
                       
    	}}
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
        	DatagramPacket DpSend = new DatagramPacket(bufs, bufs.length, ia, port_recv_UDP);
        	socket.send(DpSend);
    	}
    	catch (Exception e)
    	{
    		System.out.println("Could not send annuary with " + e);
    	}
    }
    
    public void send_annuaire(String logz) {
    	try {
    		System.out.println("adresse de broadacast :" + getBroadcast());
    		InetAddress ia = InetAddress.getByName(getBroadcast());
    		this.pseudo = logz;
        	byte[] bufs = logz.getBytes();
        	System.out.println("la longueur est : " + bufs.length);
        	DatagramPacket DpSend = new DatagramPacket(bufs, bufs.length, ia, port_recv_UDP);
        	socket.send(DpSend);
    	}
    	catch (Exception e)
    	{
    		System.out.println("Could not send annuary with " + e);
    	}
    }
    
    public void process_ack() {
    	//ici pas nécessaire finalement
    }
       
    
    
}
    