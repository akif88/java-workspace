package ReadCharacters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferReaderRead {

	public static void main(String[] args) throws IOException {
		
		char c;										//converts byte to char
		BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter character, q to quit");
		
		do{
			c=(char) bufRead.read();
			System.out.println(c);
			
		}while(c != 'q');		

	}

}
