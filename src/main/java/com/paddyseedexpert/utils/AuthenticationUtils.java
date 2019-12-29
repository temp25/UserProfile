package com.paddyseedexpert.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class AuthenticationUtils {
	
	private static final String ENCRYPTION_DECRYPTION_ALGORITHM = "PBEWithMD5AndDES";
	private static final String SECRET_KEY = System.getenv("SECRET_KEY");
	private static byte[] RANDOM_SALT = { (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99 };
	private static int ITERATION_COUNT = 65536;
	
	public static String encrypt(String text) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return encryptDecrypt(text, Cipher.ENCRYPT_MODE);
	}
	
	private static String encryptDecrypt(String text, int authMode) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		PBEKeySpec keySpec = new PBEKeySpec(SECRET_KEY.toCharArray(), RANDOM_SALT, ITERATION_COUNT);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ENCRYPTION_DECRYPTION_ALGORITHM);
		SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
		
		AlgorithmParameterSpec algorithmParameterSpec = (AlgorithmParameterSpec) new PBEParameterSpec(RANDOM_SALT, ITERATION_COUNT);
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(authMode, secretKey, algorithmParameterSpec);
		
		if (authMode == Cipher.ENCRYPT_MODE){
            return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        }else{
            return new String(cipher.doFinal(Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            
        }
	}
	
	public static String decrypt(String text) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return encryptDecrypt(text, Cipher.DECRYPT_MODE);
	}
	
}
