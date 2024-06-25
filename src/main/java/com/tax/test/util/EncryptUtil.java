package com.tax.test.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptUtil {
	private static final String TRANSFORMATION = "AES/GCM/NoPadding";
	private static final int TAG_LENGTH_BIT = 128;
	private static final int IV_LENGTH_BYTE = 12;
	private static final SecretKey SECRET_KEY;


	static {
		byte[] keyBytes = Base64.getDecoder().decode("JTg33jxqggr3wSp5LdeRHlj6dAnVxxZdSs7qkCuB6RE=");
		SECRET_KEY = new SecretKeySpec(keyBytes, "AES");
	}


	public static String encrypt(String data) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			byte[] iv = new byte[IV_LENGTH_BYTE];
			SecureRandom random = new SecureRandom();
			random.nextBytes(iv);
			GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
			cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY, spec);
			byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			byte[] encryptedDataWithIv = new byte[IV_LENGTH_BYTE + encryptedData.length];
			System.arraycopy(iv, 0, encryptedDataWithIv, 0, IV_LENGTH_BYTE);
			System.arraycopy(encryptedData, 0, encryptedDataWithIv, IV_LENGTH_BYTE, encryptedData.length);
			return Base64.getEncoder().encodeToString(encryptedDataWithIv);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decrypt(String encryptedData)  {
		try {
			byte[] encryptedDataWithIv = Base64.getDecoder().decode(encryptedData);
			byte[] iv = new byte[IV_LENGTH_BYTE];
			System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
			GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY, spec);
			byte[] encryptedBytes = new byte[encryptedDataWithIv.length - IV_LENGTH_BYTE];
			System.arraycopy(encryptedDataWithIv, IV_LENGTH_BYTE, encryptedBytes, 0, encryptedBytes.length);
			byte[] decryptedData = cipher.doFinal(encryptedBytes);
			return new String(decryptedData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}