import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class integrity {

	public static Verification verification = new Verification();
	public static ScheduledExecutorService period = Executors.newScheduledThreadPool(1);
	
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
			if (args[i].equals("stop")) {
				try{
					period.shutdown();
					verification.closeWriter();
					verification.closeLog();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//System.out.println(folderPath + " " + registryPath + " " + logFile + " " + hashFunction + " " +
			//	privateKey + " " + publicKey  + " " + intervalTime);
		
		
		try 
		{
				// list given file and write with path
				List<File> files = new ArrayList<File>();
				files = verification.listFiles(folderPath);
				
				// generate key pair
				verification.generatePubPriKey();				
		
				// open registery file
				verification.openWriter(registryPath);
				for(File f:files){
					System.out.println(f);
					String writeHash = verification.hashFile(f.toString(), hashFunction);
					
					// write registry file
					verification.printFile(f.toString(), writeHash, null,"registery");
				}
				// sign registry file and write sign hash
				byte[] signHash=verification.signFile(privateKey, registryPath, hashFunction);				
				
				// verify registry file
				verification.openLog(logFile);
				periodicalVerify(intervalTime, publicKey, registryPath, hashFunction, signHash, logFile);				
				
				verification.printFile(registryPath, "", signHash, "sign");			
				
		}
		catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}// end try-catch

	}// end main
	
	
	public static void periodicalVerify(long intervalTime, String publicKey, String registryPath, String hashFunction, byte[] signHash, String logFile){
		//ref: https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ScheduledExecutorService.html
		Runnable verifyPeriod = new Runnable() {
			
			@Override
			public void run() {
				// verify registry file
				boolean verifyFile;
				try {
					verifyFile = verification.verifyFile(publicKey, registryPath, hashFunction, signHash);
					System.out.println(verifyFile);
					
					// write log file					
					if(verifyFile == true)
						verification.printFile(logFile, "verification successfull", null, "log");
					else
						verification.printFile(logFile, "verification failed", null, "log");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		};
		
		// (runnable, initialdelay, period after initialdelay, period time unit)
		period.scheduleAtFixedRate(verifyPeriod, 0, intervalTime, TimeUnit.SECONDS);
		
	}

}
