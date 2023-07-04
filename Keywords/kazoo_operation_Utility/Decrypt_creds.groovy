package kazoo_operation_Utility
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher
import java.security.Key;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Hex

import internal.GlobalVariable

//standard AES encryption classes using AES library
public class Decrypt_creds {

	//Set alogorithm for encryption
	private static final String ALGO = "AES";

	//set the secret pass key
	static final def keyValue =" passthewordnametheuser ".getBytes()

	//Function to encrypt input text
	public static String encrypt(String Data) throws Exception {
		def key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes("UTF-8"));
		return new String(Hex.encodeHex(encVal));
	}

	//Function to decrypt the input text
	public static String decrypt(String encryptedData) throws Exception {
		def key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		// encryptedData=new String()//unhex
		byte[] decValue = c.doFinal(Hex.decodeHex(encryptedData.toCharArray()));
		return new String(decValue);
	}

	//Function to generate AES key used for encryption or decryption
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}


	//This KW provide encryption, decryption operation for username and password
	@Keyword
	def decrypt_username_password (String username,String password){

		try {

			//Encrypt the username and password and print it, this code is kept commented because we are using encrypted username and password, will be used to generated encrypted values for future profiles.

			/*ArrayList  encrypted_val = [Decrypt_creds.encrypt(username), Decrypt_creds.encrypt(password)]
			 // System.out.println("encrypted user name="+ encryptedUsername);
			 println ("Encrypted creds" + encrypted_val)
			 return encrypted_val*/


			//List of decrypted text to be used for generating encrypted credentials

			ArrayList  decrypted_val = [Decrypt_creds.decrypt(username), Decrypt_creds.decrypt(password)]
			//println ("decrypted creds" +decrypted_val)
			return decrypted_val

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}