	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
	import java.net.Socket;


public class ConversationManager{

	private ServerSocket servSocket;
	private Socket Sock;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int port_s;
	private int port_c;
	
	
	
	//baeldung udp in java 
	//pour le broadcast des databases à la connexion/déconnexion 
	
	public void setps(int ps)
	{
		this.port_s=ps;
	}
	
	public void setpc(int pc)
	{
		this.port_s=pc;
	}
	
	public void setports(int ps,int pc) {
		this.port_s = ps;
		this.port_c =pc;
	}
	
	   public int scanports() {
           System.out.println("Scanning ports");
           boolean found = false;
           int i = 1234;
           while (!found & i<1734){
               try (Socket Sock= new Socket ("localhost",i)){
                   System.out.println("Server is listening on port " + i);
                   found = true;
                   Sock.close();
               } catch (Exception e) {
                   System.out.println("Server is not listening on port " +i);
               } 
               
               i++;  
           }
           if (found) {
        	   return i;
           }
           else return 0;
   }
	
	   public void createconnectionserver(int port) {
			try {
		         this.servSocket = new ServerSocket(port);
		         System.out.println("Awaiting Connection");
		         this.Sock= servSocket.accept();
			}
			catch (Exception e) {
		         System.out.println ("Error creating connection : " + e);
		     }
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
	
	public void createconnectionclient(InetAddress addr) {
		try {
			System.out.println("Trying to build socket");
	        this.Sock= new Socket (addr,this.port_c);
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
			String result = (String)in.readUTF();
			return result;
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

