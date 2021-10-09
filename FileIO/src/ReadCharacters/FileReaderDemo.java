package ReadCharacters;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderDemo {

	public static void main(String[] args) {
		
		try(FileReader fileRead = new FileReader("/home/akif/hello_c.c")){
			
			int c;
			
			while((c = fileRead.read())!= -1) 
					System.out.print((char) c);
			
		}catch (IOException e) {
			System.out.println(e);
		}

	}

}
