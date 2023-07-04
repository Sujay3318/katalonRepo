package kazoo_operation_Utility;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import internal.GlobalVariable
import kazoo_operation_Utility.Decrypt_creds
/* 
 * This script generates md5 encrypted credentials for user set in profile global variables.
 */
public class EncryptCreds{

	@Keyword
	def Encrypt_Creds(String username,String password){

		//decrypt the encrypted 'username' and 'password' provided in profile
		def dycrypted_val = (new kazoo_operation_Utility.Decrypt_creds()).decrypt_username_password(username, password)

		//create a md5 object
		def md5 = ''
		//set input string to be encrypted
		String input = (dycrypted_val[0]  + ':' +dycrypted_val[1])
		//generated the encrypted code
		try {
			MessageDigest digest = MessageDigest.getInstance('MD5')
			digest.update(input.bytes, 0, input.length())
			md5 = new BigInteger(1, digest.digest()).toString(16)
		} catch (NoSuchAlgorithmException e) {
			println('Error in creating md5 hash :'+ e)
		}
		return md5

	}


	@Keyword
	def Encrypt_Creds_subaccounts(String username,String password){

		def md5 = ''
		//set input string to be encrypted
		def input = (username  + ':' +password)
		//generated the encrypted code
		try {
			MessageDigest digest = MessageDigest.getInstance('MD5')
			digest.update(input.bytes, 0, input.length())
			md5 = new BigInteger(1, digest.digest()).toString(16)
		} catch (NoSuchAlgorithmException e) {
			println('Error in creating md5 hash :'+ e)
		}
		return md5
	}

}
