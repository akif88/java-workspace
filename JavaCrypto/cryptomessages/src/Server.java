import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


public class Server {

	private static final int port = 9001;
	
	private static HashSet<String> names = new HashSet<String>();
	
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); 
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running");
		ServerSocket listener = new ServerSocket(port);
		try{
			while(true){
				new Handler(listener.accept()).start();
			}
		}finally {
			listener.close();
		}
	}//end main
	
	
	private static class Handler extends Thread{
		
		 private String name;
	     private Socket socket;
	     private BufferedReader in;
	     private PrintWriter out;
	     
	     public Handler(Socket socket) {
			this.socket = socket;
		}
	     
	     @Override
	    public void run() {
	    	 try{
	    		 
	    		 
	    		 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    		 out = new PrintWriter(socket.getOutputStream(), true);
	    		 
	    		 while(true){
	    			 out.println("SUBMITNAME");
	    			 name = in.readLine();
	    			 if(name == null)
	    				 return;
	    			 
	    			 synchronized(names){
	    				 if(!names.contains(name))
	    				 {
	    					 names.add(name);
	    					 break;
	    				 }	
	    			 }
	    			 
	    		 }//end while
	    		 
	    		 
	    		 out.println("NAMEACCEPTED");
	    		 writers.add(out);
	    		 
	    		 
	    		 while(true){
	    			 String input = in.readLine();
	    			 System.out.println("MESSAGE " + name + ": " + input);
	    			 if(input == null)
	    				 return;
	    			 
	    			 for(PrintWriter writer: writers)
	    				 writer.println("MESSAGE " + name + ": " + input);
	    		 }//end while
	    		 
	    		 
	    	 }catch (IOException e) {
				System.out.println(e);
			}finally {
				
				if(name != null)
					names.remove(name);
				
				if(out != null)
					writers.remove(out);
				
				try {
					socket.close();
					
				} catch (IOException e2) {
					System.out.println(e2);
				}
				
			}//end try-catch-finally blocks
	    	 
	    }//end run method
	     

	}//end private class
	
}
