import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.xml.bind.DatatypeConverter;


public class Verification {
	
	private File file;	
	private PrintWriter out;
	private File fileLog;	
	private PrintWriter outLog;
	
	
	public void openWriter(String path) throws FileNotFoundException{
		file = new File(path);
		out = new PrintWriter(file);
	}
	
	
	public void openLog(String path) throws FileNotFoundException{
		fileLog = new File(path);
		outLog = new PrintWriter(fileLog);
	}	
	
	public List<File> listFiles(String folderPath) {
		File dir = new File(folderPath);
		
		List<File> fileList = new ArrayList<File>();
		List<File> list = new ArrayList<File>();
		
		// given file. specified path with -p parameter
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
	
	
	public void printFile(String path, String msg, byte[] signHash, String fileType) throws FileNotFoundException {	

		// write the registry file or the log file
		if(fileType.equals("registery")){
			out.write(path + " ");
			out.write(msg+ "\n");
			out.flush();
		}
		else if(fileType.equals("log")){	
			SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Timestamp stamp = new Timestamp(System.currentTimeMillis());
			outLog.write(timeFormat.format(stamp)+" ");
			outLog.write(msg + "\n");
			outLog.flush();
		}
		else if(fileType.equals("sign")){
			out.write("##signature: ");
			out.write(Base64.getEncoder().encodeToString(signHash));
			
		}
	}
	
	
	public void closeWriter(){
		out.close();
	}
	
	public void closeLog(){
		outLog.close();
	}	
	
	public String hashFile(String filePath, String hashFunction) throws NoSuchAlgorithmException, IOException	{
		
		// Hash Cryptographic Function
		MessageDigest hash = MessageDigest.getInstance(hashFunction);			

		// read given file
		InputStream readFile = new FileInputStream(filePath);		
		
		// for read byte and hashed file
		DigestInputStream digestFile = new DigestInputStream(readFile, hash);
		int a = digestFile.read();
		while(a != -1) // read all the contents of given file 
			a = digestFile.read();
		
		// hashed the file
		byte[] msgdigest = hash.digest();
		String strHex = DatatypeConverter.printHexBinary(msgdigest).toLowerCase();  
		
		System.out.println(strHex);		
		return strHex;
		
	}
	
	
	public void generatePubPriKey() throws NoSuchAlgorithmException, IOException {		
		//ref: https://www.novixys.com/blog/how-to-generate-rsa-keys-java/
		// generate private key and public key for RSA-2048
		KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
		keys.initialize(2048);
		
		KeyPair pairKeys = keys.generateKeyPair();
		
		Key pubKey = pairKeys.getPublic();
		Key priKey = pairKeys.getPrivate();
		
		// write private key
		out= new PrintWriter("private.key");
		out.write(Base64.getEncoder().encodeToString(priKey.getEncoded()));
		closeWriter();
		
		// write public key
		out = new PrintWriter("public.pub");
		out.write(Base64.getEncoder().encodeToString(pubKey.getEncoded()));		
		closeWriter();	
	}
	
	
	
	public byte[] signFile(String privateKey, String registryFile, String hashFunction) throws Exception{
		// ref: https://www.mkyong.com/java/java-digital-signatures-example/
		// ref: https://niels.nu/blog/2016/java-rsa.html
		
		// read private key file and convert String to PrivateKey Object
		file = new File(privateKey);
		byte[] readPriKey = Files.readAllBytes(file.toPath());
		byte[] decodePriKey = Base64.getDecoder().decode(readPriKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodePriKey); // use private key file
		PrivateKey key = kf.generatePrivate(keySpec); // convert available private key to PrivateKey object  
		
		// sign registry file
		file = new File(registryFile);
		byte[] readRegFile = Files.readAllBytes(file.toPath());
		Signature sign = Signature.getInstance(hashFunction+"withRSA");
		sign.initSign(key);
		sign.update(readRegFile); 
		byte[] ssss = sign.sign();
		
		return ssss;
		
	}
	
	
	public boolean verifyFile(String publicKey, String registryFile, String hashFunction, byte[] privateKey) throws Exception{
		// ref: https://www.mkyong.com/java/java-digital-signatures-example/
		// ref: https://niels.nu/blog/2016/java-rsa.html
		
		// read public key file and convert String to PublicKey Object
		file = new File(publicKey);
		byte[] readPubKey = Files.readAllBytes(file.toPath());
		byte[] decodePubKey = Base64.getDecoder().decode(readPubKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodePubKey); // use public key file
		PublicKey key = kf.generatePublic(keySpec); // convert available public key to PublicKey object 
		
		// verify registry file
		file = new File(registryFile);
		byte[] readRegFile = Files.readAllBytes(file.toPath());
		System.out.println(readRegFile);
		Signature verify = Signature.getInstance(hashFunction+"withRSA");
		verify.initVerify(key);
		verify.update(readRegFile);
		boolean verifyFileSign = verify.verify(privateKey);
		
		return verifyFileSign;
	}
		
}
