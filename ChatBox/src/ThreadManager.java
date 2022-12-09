import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadManager {


    public static void main(String[] args) {

    }
}


class ThreadEcoute extends Thread {
    final DataInputStream dis;
    final Socket s;

    public ThreadEcoute(DataInputStream dis,Socket s){
        this.s = s;
        this.dis = dis;
    }
    public void run(){

        while (true){
            try{

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    }
