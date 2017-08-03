package de.cect.blockid.identitymanager.gerneral.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for cryptographic requirements. This class basically contains
 * convenience methods for the Java Security API.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
public class CryptoUtils {
	private static final Logger LOG = LoggerFactory.getLogger(CryptoUtils.class);

	public static final String RSA = "RSA";

	public static final String AES = "AES";

	private static final String AES_ALGO_SPEC = "AES/ECB/PKCS5Padding";

	/**
	 * Computes a signature of the given message by first computing the SHA-1 of the
	 * message and then encrypting this hash with the private key.
	 * 
	 * @param binaryMessage
	 *            the message.
	 * @param privateKeyBase64
	 *            the base 64 encoded private key
	 * @return the base64 encoded signature.
	 */
	public String getSignatureBase64(byte[] binaryMessage, String privateKeyBase64) {
		try {
			byte[] messageHash = MessageDigest.getInstance("SHA-1").digest(binaryMessage);
			Key privateKey = rsaPrivateKeyFromBase64(privateKeyBase64);
			Cipher cipher = getCipher(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(messageHash));
		} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
			LOG.error("Could not generate signature for given message and key.", e);
		}
		return null;
	}

	/**
	 * Computes the SHA-1 hash of the given message and RSA decrypts the given
	 * signature with the given public key. Then the result of the comparison is
	 * returned.
	 * 
	 * @param binaryMessage
	 *            the original message.
	 * @param signatureBase64
	 *            the base64 encoded signature.
	 * @param publicKeyBase64
	 *            the base64 encoded public Key.
	 * @return true if signature is valid, false is it is not valid and null if
	 *         verification failed for technical reasons.
	 */
	public Boolean verifySignatureBase64(byte[] binaryMessage, String signatureBase64, String publicKeyBase64) {
		try {
			byte[] messageHash = MessageDigest.getInstance("SHA-1").digest(binaryMessage);
			byte[] binarySignature = Base64.getDecoder().decode(signatureBase64);
			Key publicKey = rsaPublicKeyFromBase64(publicKeyBase64);
			Cipher cipher = getCipher(RSA);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] decryptedSignature = cipher.doFinal(binarySignature);
			return Arrays.equals(messageHash, decryptedSignature);
		} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
			LOG.error("Could not verify signature for given message and key.", e);
		}
		return null;
	}

	/**
	 * Encrypts the given string with the given algorithm(RSA or AES) and returns
	 * the base64 encoding of the cipher. In case of asymmetric encryption, it is
	 * assumed, that the given key is a public key.
	 * 
	 * @param strToEncrypt
	 *            the text to be encrypted
	 * @param keyBase64
	 *            a key in base64 encoding.
	 * @param algorithm
	 *            the algorithm, i.e. RSA or AES
	 * @return the base64 encoded encrypted text or null if something fails.
	 */
	public String encrypt(String strToEncrypt, String keyBase64, String algorithm) {
		String encyptedString = null;
		Key key = getSecretKey(keyBase64, algorithm, Cipher.PUBLIC_KEY);
		Cipher cipher = getCipher(algorithm);
		if (key != null && cipher != null) {
			try {
				cipher.init(Cipher.ENCRYPT_MODE, key);
				encyptedString = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
					| UnsupportedEncodingException e) {
				LOG.error("Could not cipher with the given inputq.", e);
			}
		}
		return encyptedString;
	}

	/**
	 * Decrypts the given base64 encoded string with the given algorithm(RSA or AES)
	 * and returns the base64 encoding of the cipher. In case of asymmetric
	 * encryption, it is assumed, that the given key is a private key.
	 * 
	 * @param strToEncrypt
	 *            the text to be encrypted
	 * @param keyBase64
	 *            a key in base64 encoding.
	 * @param algorithm
	 *            the algorithm, i.e. RSA or AES
	 * @return the decrypted text (UTF-8) or null if something fails.
	 */
	public String decrypt(String strToDecrypt, String keyBase64, String algorithm) {
		String decyptedString = null;
		Key key = getSecretKey(keyBase64, algorithm, Cipher.PRIVATE_KEY);
		Cipher cipher = getCipher(algorithm);
		if (key != null && cipher != null) {
			try {
				cipher.init(Cipher.DECRYPT_MODE, key);
				decyptedString = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), "UTF-8");
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
					| UnsupportedEncodingException e) {
				LOG.error("Could not cipher with the given input.", e);
			}
		}
		return decyptedString;
	}

	/**
	 * Generate a symmetric AES key.
	 * 
	 * @return the base64 encoded string representing the AES key or null if not
	 *         possible.
	 */
	public String generateSecretAesKey() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance(AES);
			SecureRandom random = new SecureRandom();
			keyGen.init(random);
			return Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Symmetric key could not be generated", e);
		}
		return null;
	}

	/**
	 * Generate a asymmetric RSA key pair.
	 * 
	 * @return the {@link KeyPair} representing the RSA keys or null if not
	 *         possible.
	 */
	public KeyPair generateRsaKeyPair() {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance(RSA);
			SecureRandom random = new SecureRandom();
			keyGen.initialize(1024, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Keypair could not be generated", e);
		}
		return null;
	}

	/**
	 * Creates the cipher object for the encryption/decryption.
	 * 
	 * @param algorithm
	 *            the algorithmn to create the cipher object for.
	 * @return the {@link Cipher} object.
	 */
	private Cipher getCipher(String algorithm) {
		Cipher cipher = null;
		try {
			switch (algorithm) {
			case RSA:
				cipher = Cipher.getInstance(RSA);
				break;
			case AES:
				cipher = Cipher.getInstance(AES_ALGO_SPEC);
				break;
			default:
				LOG.error("Cipher algorithm not known. Only RSA and AES are currently supportet.");
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			LOG.error("Cipher algorithm not known. Only RSA and AES are currently supportet.", e);
		}
		return cipher;
	}

	/**
	 * 
	 * Generates the {@link Key} out of a base64 encoded key.
	 * 
	 * @param keyBase64
	 *            the encoded key.
	 * @param algorithm
	 *            the algorithm to be used with the key.
	 * @param mode
	 *            specifies if private or public key should be generated.
	 * @return the {@link Key} for the given base64 encoded key and given algorithm
	 *         and mode.
	 */
	private Key getSecretKey(String keyBase64, String algorithm, int mode) {
		Key key = null;
		switch (algorithm) {
		case RSA:
			switch (mode) {
			case Cipher.PRIVATE_KEY:
				key = rsaPrivateKeyFromBase64(keyBase64);
				break;
			case Cipher.PUBLIC_KEY:
				key = rsaPublicKeyFromBase64(keyBase64);
				break;
			default:
				LOG.error("Cipher algorithm not known. Only RSA and AES are currently supportet.");
			}
			break;
		case AES:
			key = aesKeyFromBase64(keyBase64);
			break;
		default:
			LOG.error("Cipher algorithm not known. Only RSA and AES are currently supportet.");
		}
		return key;

	}

	/**
	 * Generate a asymmetric RSA private key from the given Base64 encoded binary.
	 * 
	 * @param privateKeyBase64
	 *            the binary.
	 * @return the {@link Key} representing the RSA private key or null if not
	 *         possible.
	 */
	private Key rsaPrivateKeyFromBase64(String privateKeyBase64) {
		try {
			KeyFactory kf = KeyFactory.getInstance(RSA);
			return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64)));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOG.error("Private Key could not be reconstructed", e);
		}
		return null;
	}

	/**
	 * Generate a asymmetric RSA public key from the given Base64 encoded binary.
	 * 
	 * @param publicKeyBase64
	 *            the binary.
	 * @return the {@link Key} representing the RSA public key or null if not
	 *         possible.
	 */
	private Key rsaPublicKeyFromBase64(String publicKeyBase64) {
		try {
			KeyFactory kf = KeyFactory.getInstance(RSA);
			return kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64)));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOG.error("Public Key could not be reconstructed", e);
		}
		return null;
	}

	/**
	 * Generate a symmertic AES key from the given Base64 encoded binary.
	 * 
	 * @param keyBase64
	 *            the binary.
	 * @return the {@link Key} representing the AES key or null if not possible.
	 */
	/*
	 * TODO ctinnes what is the purpose of applying SHA-1 to the key first. I think
	 * this comes from the idea, that a simple, readable password is to be used. In
	 * our case, we generate a secure key and work with that. So applying SHA-1
	 * first might not be necessary.
	 */
	private Key aesKeyFromBase64(String keyBase64) {
		MessageDigest sha = null;
		try {
			byte[] keyBytes = Base64.getDecoder().decode(keyBase64);
			sha = MessageDigest.getInstance("SHA-1");
			return new SecretKeySpec(Arrays.copyOf(sha.digest(keyBytes), 16), AES);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Symmetric key could not be reconstructed", e);
		}
		return null;
	}
}