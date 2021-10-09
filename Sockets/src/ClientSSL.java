import java.io.*;
    import javax.net.ssl.*;


public class ClientSSL {

	public static void main(String[] args) {
		
		int port = 12345;
		    String host = "192.168.1.69";
		    
		    try {
		        SSLSocketFactory sslFact =
		            (SSLSocketFactory)SSLSocketFactory.getDefault();
		        SSLSocket s = (SSLSocket)sslFact.createSocket(host, port);
		    
		        OutputStream out = s.getOutputStream();
		        InputStream in = s.getInputStream();
		    
		        // Send messages to the server through
		        // the OutputStream
		        // Receive messages from the server
		        // through the InputStream
		    }
		    
		    catch (IOException e) {
		    }

	}

}
