	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.net.ServerSocket;
	import java.net.Socket;


public class ConversationManager {

	private ServerSocket servSocket;
	private Socket Sock;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int port_s;
	private int port_c;
	
	
	public void setports(int ps,int pc) {
		this.port_s = ps;
		this.port_c =pc;
	}
	
	public void createconnectionserver() {
		try {
	         this.servSocket = new ServerSocket(this.port_s);
	         System.out.println("Awaiting Connection");
	         this.Sock= servSocket.accept();
		}
		catch (Exception e) {
	         System.out.println ("Error creating connection : " + e);
	     }
	}
	
	public void createconnectionclient() {
		try {
			System.out.println("Trying to build socket");
	        this.Sock= new Socket ("localhost",this.port_c);
		}
		catch (Exception e) {
	         System.out.println ("Error creating connection : " + e);
	     }
	}
	
	public void initstreamserv() {
		try {
			this.in = new ObjectInputStream (this.Sock.getInputStream());
	        this.out = new ObjectOutputStream(this.Sock.getOutputStream());
		}
		catch (Exception e) {
			System.out.println ("Error creating streams : " + e);
		}
	}
	
	public void initstreamclient() {
		try {
			this.out = new ObjectOutputStream(this.Sock.getOutputStream());
			this.in = new ObjectInputStream (this.Sock.getInputStream());  
		}
		catch (Exception e) {
			System.out.println ("Error creating streams : " + e);
		}
	}
	
	public void closeconnection() {
		try {
			 this.in.close();
	         this.out.close();
	         if (this.Sock!= null) {
	        	 this.Sock.close(); 
	         }
	         if (this.servSocket != null) {
		         this.servSocket.close();
	         }
		}
		catch(Exception e) {
			System.out.println ("Error closing connection : " + e);
		}
	}
	      
	public String recvmessage() {
		try {
			return (String)in.readUTF();
		}
		catch(Exception e) {
			System.out.println ("Error receiving message : " + e);
			return "";
		}
	}
	
	public void printrecvmessage() {
		System.out.println(recvmessage());
	}
	
	
	public void sendmessage(String mess) {
		try {
			out.writeUTF(mess);
	        out.flush();
		}
		catch (Exception e) {
			System.out.println ("Error sending message : " + e);
		}	
	}
	    
}

