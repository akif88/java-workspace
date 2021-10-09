import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Client extends Thread{

	BufferedReader in;
    PrintWriter out;
    Socket socket;
    Gui gui;
    Thread t; 
    
    KeyGenerator keygenAES;
    KeyGenerator keygenDES;
    SecretKeySpec aesKey;
    SecretKeySpec desKey;
    byte[] ivAES = { 0, 0, 0, 1, 3, 0, 8, 0, 0, 4, 0, 0, 0, 1, 0, 0 };
    byte[] ivDES = { 0, 1, 0, 3, 0, 0, 6, 0};
    IvParameterSpec ivspecAES;
    IvParameterSpec ivspecDES;
    Cipher cipher;
    String method;
    String mode;
    
    public Client(){
    	
    	gui = new Gui(this);
        gui.setVisible(true);
        
        try {
	        //keygenAES = KeyGenerator.getInstance("AES");
	    	//aesKey = keygenAES.generateKey();
	    	//keygenDES = KeyGenerator.getInstance("DES");
	    	//desKey = keygenDES.generateKey();
        	
	    	method = "AES";
	        mode = "CBC";
	        
	        byte[] key = ("asdasbaskasdasdaga").getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			
			key = sha.digest(key);
			
			aesKey= new SecretKeySpec(Arrays.copyOf(key,  16), "AES");
			desKey= new SecretKeySpec(Arrays.copyOf(key,  8), "DES");
			
			System.out.println(aesKey);
	        System.out.println(desKey);
	        
		}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    public void initCrypto() {
    	
    	try {
			cipher = Cipher.getInstance(method+ "/" + mode + "/PKCS5Padding");
			System.out.print(method+ "/" + mode + "/PKCS5Padding");
			if(method=="AES") {
				ivspecAES = new IvParameterSpec(ivAES);
				cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivspecAES);
			}else if (method=="DES") {
				ivspecDES = new IvParameterSpec(ivDES);
				cipher.init(Cipher.ENCRYPT_MODE, desKey, ivspecDES);
			}
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    }
    
    public String Encrypt(String message) {
    	
    	try {
    		
    		initCrypto();
    		
    		byte[] plaintext = message.getBytes();
			byte[] ciphertext = cipher.doFinal(plaintext); 
			String cipherTextBase64 = Base64.getEncoder().encodeToString(ciphertext);
			
			gui.jTextArea2.setText(method + " " + mode + "\n" + cipherTextBase64);	        
			
	        return cipherTextBase64;
	        
	        
        }catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	
    	return "";
    }
    
    public String Decrypt(String message) {
    	
    	try {
    		byte[] message_byte = Base64.getDecoder().decode(message.getBytes());
    		
    		if(method=="AES") {
				cipher.init(Cipher.DECRYPT_MODE, aesKey, ivspecAES);
    		}else if (method=="DES") {
				cipher.init(Cipher.DECRYPT_MODE, desKey, ivspecDES);
    		}
    		
			byte[] plaintext = cipher.doFinal(message_byte);
			String plaintextStr = new String(plaintext, "UTF-8");
			return plaintextStr;

			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    	return null;
    	
    }
    
    
    
    public void Send(String message){
        message = Encrypt(message);  
        out.println(message);
    }
    
    public void connect(String serverAddress, String name) {
    	
    	try {
	    	System.out.println(serverAddress);
	    	System.out.println(name);

	    	socket = new Socket(serverAddress, 9001);
	        in = new BufferedReader(new InputStreamReader(
	            socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream(), true);          
	        
	        t = new Thread(new Runnable() {
	        	
	        	public void run() {
	        		
	        		try {
		          		while (true) {
		    	            String line = in.readLine();
		    	            System.out.println(line);
		    	            if (line.startsWith("SUBMITNAME")) {
		    	                out.println(name);
		    	            } else if (line.startsWith("NAMEACCEPTED")) {
		    	            	 gui.jLabel2.setText("   " + serverAddress);    	
		    	            } else if (line.startsWith("MESSAGE")) {
		    	            	String[] mes1 = line.split(" ");
		    	            	System.out.println(mes1[2]);
		    	            	String mes = Decrypt(mes1[2]);
		    	            	gui.jTextArea1.append(mes1[1] + mes + '\n');
		    	            }
		    	        }
		          		
	        		}catch(IOException e) {
	            		e.printStackTrace();
	        		}
	        	}
	        });      
	        
	        t.start();
    	}catch(IOException e) {
    		e.printStackTrace();
    		
    	}
    		
    }  
    
    public static void main(String args[]) {
        
    	Client client  = new Client(); 
    	Thread t = client;
    	t.start();
    		
    	      
    }
		
}
