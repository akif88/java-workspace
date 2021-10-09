import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Integrity {
	
	public static List<File> listFiles(String folderPath) {
		File dir = new File(folderPath);
		
		List<File> fileList = new ArrayList<File>();
		List<File> list = new ArrayList<File>();
		
		for(File f: dir.listFiles())
			list.add(f);
				
		for( File f: list) {
			if (f.isFile()) {
				//System.out.println("File: " + f);
				fileList.add(f);
			}else if(f.isDirectory()) {
				//System.out.println("Directory: " + f);
				fileList.addAll(listFiles(f.getPath()));
			}
		}
		
		return fileList;
	}
	
	public static void main(String[] args) {
		
		String folderPath = "";
		String registryPath = "";
		String logFile = "";
		String hashFunction = "";
		String privateKey = "";
		String publicKey = "";
		int intervalTime = 0;
		
		for ( int i=0; i<args.length; i++) {
			if (args[i].equals("-p")) {
				folderPath = args[i + 1];
			}
			if (args[i].equals("-r")) {
				registryPath = args[i + 1];
			}
			if (args[i].equals("-l")) {
				logFile = args[i + 1];
			}
			if (args[i].equals("-h")) {
				hashFunction = args[i + 1];
			}
			if (args[i].equals("-k")) {
				privateKey = args[i + 1];
				publicKey = args[i + 2];
			}
			if (args[i].equals("-i")) {
				intervalTime = Integer.parseInt(args[i + 1]);
			}
		}
		
		System.out.println(folderPath + " " + registryPath + " " + logFile + " " + hashFunction + " " + privateKey + " " + publicKey  + " " + intervalTime);
		
		File registeryFile = new File(registryPath);
		
		List<File> files = new ArrayList<File>();
		files = listFiles(folderPath);
		
		for(File f:files)
			System.out.println(f);
		
	}
	
}
