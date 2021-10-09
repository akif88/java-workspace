package ReadCharacters;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterDemo {

	public static void main(String[] args) {
		
		String source = "Now is the time for all good men\n to come to the aid of their country\n"
				+ " and pay their due taxes.";

		char[] buffer =  new char[source.length()];
		source.getChars(0, source.length(), buffer, 0);
				
		try(FileWriter fileWrite1 = new FileWriter("/home/akif/file1.txt");
				FileWriter fileWrite2 = new FileWriter("/home/akif/file2.txt");
				FileWriter fileWrite3 = new FileWriter("/home/akif/file3.txt")){
			
			for(int i=0; i < buffer.length; i+=2)
				fileWrite1.write(buffer[i]);
			
			fileWrite2.write(buffer);
			
			fileWrite3.write(buffer, buffer.length-buffer.length/4, buffer.length/4);
						
		}catch (IOException e) {
			System.out.println(e);
		}

	}

}
