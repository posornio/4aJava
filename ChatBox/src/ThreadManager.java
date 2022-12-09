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
    ConversationManager cm = new ConversationManager();
    DatabaseManager dbm = new DatabaseManager();
    final DatagramPacket dis=null;
    InetAddress addrSender=null;
    final DatagramSocket s;

    public ThreadEcouteConnexions(DatagramSocket s) throws IOException {
        this.s = s;
    }
    public void run(){
        dbm.dbinit();
        while (true){
                handlerRcepCon();
                    //Handler reception
        }
        }

    public void handlerRcepCon(){
        try {
            s.receive(dis);
            addrSender=dis.getAddress();
            String loginSender = "";
            dbm.insertuser(Integer.parseInt(String.valueOf(addrSender)),loginSender);
            String portDispo = String.valueOf(dbm.insertport());
            DatagramPacket sendPacket = new DatagramPacket(portDispo.getBytes(), portDispo.length(),
                    dis.getAddress(), dis.getPort());
            s.send(sendPacket);
            //Envoyer paquet TCP avec le port utilisee


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
