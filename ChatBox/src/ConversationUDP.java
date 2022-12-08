import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ConversationUDP {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    private String pseudo;

    public ConversationUDP() {
    	try {
    		socket = new DatagramSocket(3456);
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
    	String addr_s = addr.toString();
    	
    	
    	
    	
    	
    	return true;
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
                socket.send(packet);
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
    
    
    public void send_annuaire() {
    	
    
    }
    
    
}
    