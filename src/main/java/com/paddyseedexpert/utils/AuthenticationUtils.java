package com.paddyseedexpert.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.getInstance;
import static com.google.common.io.BaseEncoding.base64;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ENCRYPTION_DECRYPTION_ALGORITHM;
import static com.paddyseedexpert.userprofile.constant.AppConstants.SECRET_KEY;

public class AuthenticationUtils {
	
	public static String encrypt(String unencryptedpassword) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        return encryptOrDecryptString(unencryptedpassword, ENCRYPT_MODE);
	}
	
	private static String encryptOrDecryptString(String text, int cipherMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Key aesKey = new SecretKeySpec(SECRET_KEY.getBytes(), ENCRYPTION_DECRYPTION_ALGORITHM);
		Cipher cipher = getInstance(ENCRYPTION_DECRYPTION_ALGORITHM);
		cipher.init(cipherMode, aesKey);
		if(cipherMode == ENCRYPT_MODE){
			return base64().encode(cipher.doFinal(text.getBytes()));
		} else {
			return new String(cipher.doFinal(base64().decode(text)));
		}
	}
	
	public static String decrypt(String encodedEncryptedPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encryptOrDecryptString(encodedEncryptedPassword, DECRYPT_MODE);
	}
	
}
