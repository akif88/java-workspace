import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Client extends Thread{

	BufferedReader in;
    PrintWriter out;
    Socket socket;
    Gui gui;
    
    KeyGenerator keygenAES;
    KeyGenerator keygenDES;
    SecretKey aesKey;
    SecretKey desKey;
    byte[] ivAES = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    byte[] ivDES = { 0, 0, 0, 0, 0, 0, 0, 0};
    IvParameterSpec ivspec;
    Cipher cipher;
    String method;
    String mode;
    
    public Client() {
    	
    	gui = new Gui(this);
        gui.setVisible(true);
        
        try {
	        keygenAES = KeyGenerator.getInstance("AES");
	    	aesKey = keygenAES.generateKey();
	    	keygenDES = KeyGenerator.getInstance("DES");
	    	desKey = keygenDES.generateKey();
	    	method = "AES";
	        mode = "CBC";
	        
		}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    public void Encrypt(String message) {
    	
    	try {
    		
    		cipher = Cipher.getInstance(method+ "/" + mode + "/PKCS5Padding");
			System.out.print(method+ "/" + mode + "/PKCS5Padding");
    		if(method=="AES") {
    			ivspec = new IvParameterSpec(ivAES);
				cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivspec);
    		}else if (method=="DES") {
    			ivspec = new IvParameterSpec(ivDES);
				cipher.init(Cipher.ENCRYPT_MODE, desKey, ivspec);
    		}
    		
    		byte[] plaintext = message.getBytes();
			byte[] ciphertext = cipher.doFinal(plaintext); 
			
	        gui.jTextArea2.setText(method + " " + mode + "\n" + Base64.getEncoder().encodeToString(ciphertext));
	        
        }catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
    	}catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void Decrypt(String message, String method, String mode) {
    	//cipher.init(Cipher.DECRYPT_MODE, aesKey, ivspec);
		//byte[] plaintext = cipher.doFinal(ciphertext);
		//System.out.println(plaintext.toString());
		// byte to String
		//String plaintextStr = new String(plaintext, "UTF-8");
		//System.out.println(plaintextStr);
    }
    
    public void Send(String message){
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
	        
	        Thread t = new Thread(new Runnable() {
	        	
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
		    	            	 gui.jTextArea1.append(line.substring(8) + "\n");
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
