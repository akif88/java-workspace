import java.net.*;
import java.io.*;

// TCP/IP client Sockets
public class WhoIs {

	public static void main(String[] args) throws Exception {
		
		int c;
		
		//create a socket connected to internic.net, port 43
		Socket sckt = new Socket("whois.internic.net", 43);
		
		InputStream  in = sckt.getInputStream();
		OutputStream out = sckt.getOutputStream();
		
		String str = (args.length == 0 ? "MHProfessional.com" : args[0]) + "\n";
		
		byte buf[] = str.getBytes();
		
		//send request
		out.write(buf);
		
		//read and display response
		while((c=in.read()) != -1)
			System.out.print((char)c);
		
		
		sckt.close();
	}

}
