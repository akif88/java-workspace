
import java.io.File;

public class FileExample {

	public static void main(String[] args) throws Exception {
			
		File path = new File("/home/akif/workspace");
		
		
		if(!path.exists())
			path.createNewFile();
		else if(path.isDirectory()){
			String[] contentOfDirectory = path.list();
			
			for(String fileName: contentOfDirectory)
				System.out.println(fileName);
		}
		else
			System.out.println("Read: " + path.canRead() + " Write: " + path.canWrite()
			+ " Hidden: " + path.isHidden());
		
			
	
	
	
	}

}
