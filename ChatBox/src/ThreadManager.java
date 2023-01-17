
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Scanner;

public class ThreadManager {}

class ThreadInitConnexionsTCP extends Thread {

    ConversationManager cm = new ConversationManager();
    DatabaseManager dbm = new DatabaseManager();
    MainForm mf;
    int portD = 0;
    InetAddress addr;
    private static int port_recv_TCP = 2000;

    public ThreadInitConnexionsTCP (InetAddress a,int p,MainForm mf) {
        this.portD = p;
        this.addr = a;
        this.mf = mf;
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
            cm.closeconnection();
            cm.createconnectionserver(p);
            cm.initstreamclient();
            System.out.println("Connexion ok");
            int port = Integer.parseInt(cm.recvmessage());
            this.portD = port;
            System.out.println("le port est : " + portD);
            cm.setpc(portD);
            cm.createconnectionclient(this.addr);
            //cm.initstreamin();
            cm.sendmessage("");
            System.out.println("Connexion établie entre nous port " + po + " et entre " + this.addr.getHostAddress() + " sur le port " + port);
            //TODO Il faut ajouter une table de conversations manager que peut utliser le GUI



            /*ThreadEnvoiTCP TE = new ThreadEnvoiTCP(cm);
            ThreadReceptionTCP TR = new ThreadReceptionTCP(cm);
            System.out.println("Thread créés");
            TR.start();
            System.out.println("TR OK !!!!");
            TE.start();
            System.out.println("TE OK !!!!");
			*/
        }
        else {
            cm.setpc(portD);
            cm.createconnectionclient(this.addr);
            //cm.initstream();
            int p = cm.scanports();
            String po = Integer.toString(p);
            System.out.println("ON ATTEINT BIEN CA AVEC P = " + p);
            cm.initstreamout();
            cm.sendmessage(po);
            System.out.println("message sent");
            cm.initstreamin();
            cm.createconnectionserver(p);
            System.out.println("Connexion établie entre nous port " + po + " et entre " + this.addr.getHostAddress() + " sur le port " + portD);
            ThreadReceptionTCP trTCP = new ThreadReceptionTCP(cm,mf);
            trTCP.start();
            mf.getMapCM().put(this.addr.getHostAddress(),cm);
            //cm.initstreamclient();
            //System.out.println("Streams créés");

            /*ThreadEnvoiTCP TE = new ThreadEnvoiTCP(cm);
            ThreadReceptionTCP TR = new ThreadReceptionTCP(cm);
            System.out.println("Thread créés");
            TR.start();
            System.out.println("TR OK !!!!");
            TE.start();
            System.out.println("TE OK !!!!");*/


        }
        //while (true)
        //{
        //System.out.println("On est dans le while TRUE !!!");
        //cm.printrecvmessage();
        //String tosend ="on send pas un truc vide cette fois lol";
        //cm.sendmessage(tosend);
        // Exiting from a while loop should be done when a client gives an exit message.

        //}
    }

    public ConversationManager getcm() {
        return this.cm;    }
}

class ThreadEnvoiTCP extends Thread {

    ConversationManager cm;


    public ThreadEnvoiTCP(ConversationManager c) {
        this.cm=c;
    }

    public void run(){
        int i =0;
        while (true){
            if (cm.isClosed()) {
                break;
            }
            System.out.println("Thread envoi ligne 1 ");
            if (i == 2) {
                cm.sendmessage("**ExitClavardage**");
                cm.closeconnection();
                break;
            }
            else {
                Scanner sc= new Scanner(System.in);
                String str= sc.nextLine();
                cm.sendmessage(str + " et i=" + i);
                //ici on rajoute a la database le message envoyé et on
                //print en frontend
                i++;
            }
        }
        System.out.println("closing TE");
    }

    //public void handlerRecepMess(){    }
    public ConversationManager getcm() {
        return this.cm;    }

}


class ThreadReceptionTCP extends Thread {

    ConversationManager cm;
    MainForm mf;

    public ThreadReceptionTCP(ConversationManager c, MainForm mf) {
        this.cm =c;
        this.mf = mf;
    }

    public void run(){
        String received="";
        System.out.println("TR Running" );
        while (true){
            if (cm.isClosed()) {
                break;
            }
            received = cm.recvmessage();
            //ici on rajoute a la database le message reçu et on
            //print en frontend
            System.out.println("received : " + received);
            if(received.equals("**ExitClavardage**"))
            {
                cm.closeconnectionenvoi();
                System.out.println("Connection Closed");
                break;
            }
            if (!received.equals("")){
                mf.handlerMR(received,cm.getaddr());
            }
        }
        System.out.println("closing TR");


    }


    //public void handlerRecepMess(){    }

}




class ThreadEcouteConnexionsTCP extends Thread {

    ConversationManager cm = new ConversationManager();
    DatabaseManager dbm = new DatabaseManager();
    MainForm mf;
    private static int port_recv_TCP = 2000;

    public ThreadEcouteConnexionsTCP(MainForm mf) {
        this.mf = mf;
    }

    public void run(){
        dbm.dbinit();
        while (true){
            //handlerRecepMess();
            cm.createconnectionserver(port_recv_TCP);
            System.out.println("Connexion ok");
            cm.initstreamclient();
            String port = cm.recvmessage();
            ThreadInitConnexionsTCP T= new ThreadInitConnexionsTCP(cm.getaddr(),Integer.parseInt(port),mf);
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





