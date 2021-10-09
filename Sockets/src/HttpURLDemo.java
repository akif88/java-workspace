import java.net.*;
import java.util.*;


public class HttpURLDemo {

	public static void main(String[] args) throws Exception {
		
		URL hp = new URL("http://www.google.com");
		
		HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
		
		//display request method
		System.out.println("Request method is: " + hpCon.getRequestMethod());
		
		//display response code
		System.out.println("Response Code is: " + hpCon.getResponseCode());
		
		//display response message
		System.out.println("Response message is: " + hpCon.getResponseMessage());
		
		// Get a list of the header fields and a set
		// of the header keys
		Map<String, List<String>> hdrMap = hpCon.getHeaderFields();
		Set<String> hdrField = hdrMap.keySet();
		
		System.out.println("\nHere is the header:");
		
		// Display all header keys and values
		for(String key: hdrField)
			System.out.println("Key: " + key + " --- Value: " + hdrMap.get(key));
	}

}
