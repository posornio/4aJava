import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ThreadManager {

	private static int port_recv_TCP = 7000;
	

class ThreadEcouteConnexionsTCP extends Thread {
    //TODO gerer  ports
    ConversationManager cm = new ConversationManager();
    DatabaseManager dbm = new DatabaseManager();


    public void run(){
        dbm.dbinit();
        while (true){
            handlerRecepMess();
        }
    }

    public void handlerRecepMess(){
    	cm.createconnectionserver(port_recv_TCP);
    }

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

    public static void main(String[] args) {
        DatabaseManager Db = new DatabaseManager();

        //Connection connection = Db.conn;
        Db.dbinit();
        System.out.println("");
    }
}






