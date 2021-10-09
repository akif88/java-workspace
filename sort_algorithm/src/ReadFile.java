import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadFile {	
	
	
	private Scanner scanFile;
	private String readLine;
	
	public ReadFile(String fileName){
		
		try{
			//BufferedInputStream is used to read faster.
			scanFile = new Scanner(new BufferedInputStream(new FileInputStream(fileName)));		
			
			
		}
		catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		
	}
	
	//read line in text
	public String readLine(){	
		  String line;
	        try {
	            line = scanFile.nextLine();
	        }
	        catch (NoSuchElementException e) {
	            line = null;
	        }
	        return line;
	}
	
	//control the line for data
	public boolean hasNextLine(){
		return scanFile.hasNextLine();
	}
	

}