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

public class SymmetricCrypto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		// AES Crypto System
		try {
			// generate AES key
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecretKey aesKey = keygen.generateKey();
					
			// reference:
			// https://stackoverflow.com/questions/6669181/why-does-my-aes-encryption-throws-an-invalidkeyexception
			// build the initialization vector.  This example is all zeros, but it 
		        // could be any value or generated using a random number generator.
			// The contents of the buffer are copied to protect against subsequent modification.
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv); // initialization vector (iv)

					
			// reference:
			// https://stackoverflow.com/questions/43890592/javax-crypto-illegalblocksizeexception-input-length-not-multiple-of-16-bytes
			// If you use NoPadding, then you must implement your own padding for encryption 
					
			//init symmetric cryptography (AES) with mode
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");			
					
					
			//  encrypt plaintext 
			cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivspec);
			byte[] text = "Hello World!!!".getBytes();
			byte[] ciphertext = cipher.doFinal(text); 
					
					
			System.out.println(Base64.getEncoder().encodeToString(ciphertext));
					
			// decrypt  ciphertext
			cipher.init(Cipher.DECRYPT_MODE, aesKey, ivspec);
			byte[] plaintext = cipher.doFinal(ciphertext);
			//System.out.println(plaintext.toString());
			// byte to String
			String plaintextStr = new String(plaintext, "UTF-8");
			System.out.println(plaintextStr);
					
					
			} 
			catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}

}
