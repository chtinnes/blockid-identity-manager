package de.cect.blockid.identitymanager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.KeyPair;
import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.cect.blockid.identitymanager.gerneral.common.CryptoUtils;

/**
 * Test class for the IdentityManager application.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdentityManagerApplicationTests {

	@Autowired
	private CryptoUtils cryptoUtils;

	/**
	 * This test tests that the application context starts.
	 */
	@Test
	public void contextLoads() {
	}

	/**
	 * This test tests the {@link CryptoUtils} as used in the application. This is a
	 * positive test case.
	 */
	@Test
	public void testCryptoUtilsFullScenarioPositive() {
		String secret = "SECRET";
		// Generate keys
		String aesKeyBase64 = this.cryptoUtils.generateSecretAesKey();
		KeyPair keyPair = this.cryptoUtils.generateRsaKeyPair();

		// Encode them in base64 encoding
		String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
		String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
		// ALGORITHM
		// First use aesKey to encrypt secret
		String encryptedSecretBase64 = this.cryptoUtils.encrypt(secret, aesKeyBase64, CryptoUtils.AES);
		// Then RSA encrypt AES key
		String rsaEncryptedAesKey = this.cryptoUtils.encrypt(aesKeyBase64, publicKeyBase64, CryptoUtils.RSA);
		// Then receiver decrypts the aesKey
		String rsaDecryptedAesKey = this.cryptoUtils.decrypt(rsaEncryptedAesKey, privateKeyBase64, CryptoUtils.RSA);
		// Then receiver decrypts secret
		String decryptedSecret = this.cryptoUtils.decrypt(encryptedSecretBase64, rsaDecryptedAesKey, CryptoUtils.AES);

		// Assertions
		assertTrue(aesKeyBase64.equals(rsaDecryptedAesKey));
		assertTrue(decryptedSecret.equals(secret));
		assertFalse(encryptedSecretBase64.equals(secret));
	}

	@Test
	public void testSignatureAlgorithmPositive() {
		byte[] testMessage = new byte[] { 1, 2, 3, 4, 5 };

		KeyPair keyPair = this.cryptoUtils.generateRsaKeyPair();

		// Encode them in base64 encoding
		String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
		String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

		String signatureBase64 = this.cryptoUtils.getSignatureBase64(testMessage, privateKeyBase64);

		assertTrue(this.cryptoUtils.verifySignatureBase64(testMessage, signatureBase64, publicKeyBase64));

	}

}
