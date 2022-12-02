	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.util.Date;
	import java.text.SimpleDateFormat;

public class ConversationManager {

	public void createserver() {
		try {
	         ServerSocket servSocket = new ServerSocket(1234);
	         System.out.println("Awaiting Connection");
	         Socket Sock= servSocket.accept();
	         System.out.println("on est la ");
	         ObjectInputStream in = new ObjectInputStream (Sock.getInputStream());
	         ObjectOutputStream out = new ObjectOutputStream(Sock.getOutputStream());
	            
	         while (true) {
	        	 String input = (String)in.readUTF();
		            System.out.println(input);
		            System.out.println("sending input");
		            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		            Date date = new Date(); 
		            out.writeUTF(formatter.format(date));
		            out.flush();
	         }
	        } catch (Exception e) {
	            System.out.println ("Error");
	        }
	    }
}

