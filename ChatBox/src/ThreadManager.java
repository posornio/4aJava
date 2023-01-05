
import java.net.InetAddress;

public class ThreadManager {}

class ThreadInitConnexionsTCP extends Thread {
	   
    ConversationManager cm = new ConversationManager();
    DatabaseManager dbm = new DatabaseManager();
    int portD = 0;
    InetAddress addr;
    private static int port_recv_TCP = 2000;
    
    public ThreadInitConnexionsTCP (InetAddress a,int p) {
    	this.portD = p;
    	this.addr = a;
    }
    
    public ThreadInitConnexionsTCP (InetAddress a) {
    	this.addr = a;
    }
    
    public void run(){
        dbm.dbinit();
        if (portD==0) {
        	this.portD = port_recv_TCP;
        	cm.setpc(portD);
            cm.createconnectionclient(this.addr);
            System.out.println("Socket ok");
            cm.initstreamclient();
            System.out.println("Streams ok ");
            int p = cm.scanports();
            System.out.println("Scanports ok with " + p);
            String po = Integer.toString(p);
            System.out.println("Sending message");
            cm.sendmessage(po);
            //il faut close conn client 
            cm.createconnectionserver(p);
            System.out.println("Connexion ok");
            int port = Integer.parseInt(cm.recvmessage());
            this.portD = port;
        	cm.setpc(portD);
            cm.createconnectionclient(this.addr);
            System.out.println("Connexion établie entre nous port" + po + " et entre " + this.addr + " sur le port " + port);
        }
        else {
        	cm.setpc(portD);
            cm.createconnectionclient(this.addr);
            //cm.initstream();
            int p = cm.scanports();
            String po = Integer.toString(p);
            System.out.println("ON ATTEINT BIEN CA AVEC P = " + p);
            //TODO : probleme ici ca send pas le message probablement porbleme de streams 
            cm.sendmessage(po);
            cm.createconnectionserver(p);
        }
        while (true)
        {
           	cm.printrecvmessage();
           	String tosend ="on send pas un truc vide cette fois lol";
           	cm.sendmessage(tosend);
            // Exiting from a while loop should be done when a client gives an exit message.
            if(tosend.equals("ExitClavardage"))
            {
            	cm.closeconnection();
                System.out.println("Connection Closed");
            }
        }
    }
}

class ThreadEcouteConnexionsTCP extends Thread {
    
    ConversationManager cm = new ConversationManager();
    DatabaseManager dbm = new DatabaseManager();
    private static int port_recv_TCP = 2000;
	
    public ThreadEcouteConnexionsTCP() {
    }

    public void run(){
        dbm.dbinit();
        while (true){
            //handlerRecepMess(); 
        	cm.createconnectionserver(port_recv_TCP);
        	System.out.println("Connexion ok");
        	cm.initstream();
        	String port = cm.recvmessage();
        	ThreadInitConnexionsTCP T= new ThreadInitConnexionsTCP(cm.getaddr(),Integer.parseInt(port));
        	cm.closeconnection();
        	T.start();
        }
    }

    //public void handlerRecepMess(){    }

}

class ThreadEcouteConnexionsUDP extends Thread {
    ConversationUDP cu = new ConversationUDP(true);
    DatabaseManager dbm = new DatabaseManager();

    public void run(){
        dbm.dbinit();
        //while true dans receive_annuaire
        cu.receive_annuaire();
    }
}

class ThreadEnvoiAnnuaire extends Thread {
    ConversationUDP cu = new ConversationUDP(false);
    DatabaseManager dbm = new DatabaseManager();
    String login;
    
    public ThreadEnvoiAnnuaire(String log) {
    	this.login = log;
    }
    
    public void run(){
        dbm.dbinit();
        cu.send_annuaire(login);
    }
}






