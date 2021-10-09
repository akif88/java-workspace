import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Main {

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
		
		System.out.println(folderPath + " " + registryPath + " " + logFile + " " + hashFunction + " " +
				privateKey + " " + publicKey  + " " + intervalTime);
		
		Integrity integrity = new Integrity();
		
		
		try 
		{
				// list given file and write with path
				List<File> files = new ArrayList<File>();
				files = integrity.listFiles(folderPath);
				
				// generate key pair
				integrity.generatePubPriKey();				
		
				// open registery file
				integrity.openWriter(registryPath);
				for(File f:files){
					System.out.println(f);
					String writeHash = integrity.hashFile(f.toString(), hashFunction);
					
					// write registry file
					integrity.printFile(f.toString(), writeHash, null,"registery");
				}
				// sign registry file and write sign hash
				byte[] signHash=integrity.signFile(privateKey, registryPath, hashFunction);
				
				// verify registry file
				boolean verifyFile = integrity.verifyFile(publicKey, registryPath, hashFunction, signHash);
				System.out.println(verifyFile);
				
				integrity.printFile(registryPath, "", signHash, "sign");				
				integrity.closeWriter();								
				
				// write log file
				integrity.printFile(logFile, "", null,"log");			
				
				
		}
		catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}// end try-catch

	}// end main

}
