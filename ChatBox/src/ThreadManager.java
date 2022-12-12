import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ThreadManager {



    public static void main(String[] args) {
        DatabaseManager Db = new DatabaseManager();

        //Connection connection = Db.conn;
        Db.dbinit();
        System.out.println("");
    }
}


class ThreadEcouteMessage extends Thread {
    //TODO gerer  ports
    ConversationManager cm = new ConversationManager();
    DatabaseManager dbm = new DatabaseManager();
    final ObjectInputStream dis;
    //port arbitraire
    final Socket s;

    public ThreadEcouteMessage(Socket s) throws IOException {
        this.s = s;
        this.dis = new ObjectInputStream(s.getInputStream()) ;
    }
    public void run(){
        dbm.dbinit();
        while (true){
            handlerRecepMess();
        }
    }

    public void handlerRecepMess(){
        try {
            DatabaseManager.Message received = (DatabaseManager.Message) dis.readObject();
            dbm.insertmessage(dbm.getMaxIdmessage(received.idRecv, received.idSender),
                    received.idSender,received.idRecv,received.contenu,received.date);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    }

class ThreadEcouteConnexions extends Thread {
    ConversationUDP cu = new ConversationUDP(true);
    DatabaseManager dbm = new DatabaseManager();

    public void run(){
        dbm.dbinit();
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
