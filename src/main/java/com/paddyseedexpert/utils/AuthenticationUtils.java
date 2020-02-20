package com.paddyseedexpert.utils;

import javax.crypto.Cipher;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.google.common.io.BaseEncoding;

public class AuthenticationUtils {
	
	private static final String ENCRYPTION_DECRYPTION_ALGORITHM = "PBEWithMD5AndDES";
	private static final String SECRET_KEY = System.getenv("SECRET_KEY");
	private static int ITERATION_COUNT = 65536;
	
	public static String encrypt(String text) {
		return encryptDecrypt(text, Cipher.ENCRYPT_MODE);
	}
	
	public static String decrypt(String text) {
		return encryptDecrypt(text, Cipher.DECRYPT_MODE);
	}
	
	private static String encryptDecrypt(String text, int authMode) {

		StandardPBEStringEncryptor encryptor = getPBEEncryptor();
		if (authMode == Cipher.ENCRYPT_MODE){
	            return BaseEncoding.base64().encode(encryptor.encrypt(text).getBytes());
		}else{
				return encryptor.decrypt(new String(BaseEncoding.base64().decode(text)));
		}
	}

	private static StandardPBEStringEncryptor getPBEEncryptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(SECRET_KEY);
		encryptor.setKeyObtentionIterations(ITERATION_COUNT);
		encryptor.setAlgorithm(ENCRYPTION_DECRYPTION_ALGORITHM);
		return encryptor;
	}

}
