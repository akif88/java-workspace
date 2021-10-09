import java.net.*;
import java.io.*;
import java.util.Date;

public class URLConnectionDemo {

	public static void main(String[] args) throws MalformedURLException, IOException {
		int c;
		
		URL hp = new URL("http://www.internic.net");
		URLConnection hpConnection = hp.openConnection();
		
		//get date
		long d = hpConnection.getDate();
		if(d == 0)
			System.out.println("No date information");
		else
			System.out.println("Date: " + new Date(d));
		
		//get content type
		System.out.println("Content-Type: " + hpConnection.getContentType());
		
		//get expiration date
		d = hpConnection.getExpiration(); 
		if(d == 0)
			System.out.println("No expiration information");
		else
			System.out.println("Expires: " + new Date(d));
		
		//get last modified day
		d = hpConnection.getLastModified();
		if(d == 0)
			System.out.println("No Last-Modified information");
		else
			System.out.println("Last-Modified: " + new Date(d));

		//get content length
		long len = hpConnection.getContentLength();
		if(len == -1)
			System.out.println("Content length unavailable.");
		else
			System.out.println("Content-Length: " + len);
		
		
		if(len != 0){
			System.out.println("===Content===");
			InputStream input = hpConnection.getInputStream();
			
			while((c = input.read()) != -1)
				System.out.print((char) c);
			
			input.close();
			
		} else 
			System.out.println("No Content available");
		
		
		
		
		
	}

}
