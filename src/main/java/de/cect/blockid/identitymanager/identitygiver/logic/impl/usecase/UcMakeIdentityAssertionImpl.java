package de.cect.blockid.identitymanager.identitygiver.logic.impl.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.gerneral.common.CryptoUtils;
import de.cect.blockid.identitymanager.identitygiver.logic.api.to.IdentityAssertionPlainTo;
import de.cect.blockid.identitymanager.identitygiver.logic.api.usecase.UcMakeIdentityAssertion;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.TransactionManagerAdapter;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionTo;

/**
 * Implementation of {@link UcMakeIdentityAssertion}
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Profile(ApplicationProfiles.IDGIVER)
public class UcMakeIdentityAssertionImpl implements UcMakeIdentityAssertion {
	private static final Logger LOG = LoggerFactory.getLogger(UcMakeIdentityAssertionImpl.class);

	@Autowired
	private TransactionManagerAdapter transactionManagerAdapter;

	@Autowired
	private CryptoUtils cryptoUtils;

	@Value("${blockid.identitymanager.publickey.base64}")
	private String publicKey;

	@Value("${blockid.identitymanager.privatekey.base64}")
	private String privateKey;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identitygiver.logic.api.usecase.
	 * UcMakeIdentityAssertion#makeIdentityAssertion(de.cect.blockid.identitymanager.
	 * identitygiver.logic.api.to.IdentityAssertionPlainTo)
	 */
	public void makeIdentityAssertion(IdentityAssertionPlainTo identityAssertionPlainTo) {
		IdAssertionMessageTo idAssertionMessageTo = new IdAssertionMessageTo();
		idAssertionMessageTo.setIdentityAssertionSenderAddressBase64(this.publicKey);
		idAssertionMessageTo.setIdentityAssertion(computeIdentityAssertion(identityAssertionPlainTo));
		/*
		 * Compute and set signature TODO ctinnes I'am not really convinced about the
		 * way the signature is computed and also implemented. Advices are welcome =)
		 */
		idAssertionMessageTo.setIdentityAssertionSignatureBase64(this.computeSignatureBase64(idAssertionMessageTo));
		LOG.debug("Sending identity assertion for "
				+ identityAssertionPlainTo.getIdentityAssertionReceiverAddressBase64() + ".");
		this.transactionManagerAdapter.sendIdentityAssertion(idAssertionMessageTo);
	}

	/**
	 * This method computes the encrypted identity assertion from the given plain
	 * data.
	 * 
	 * @param identityAssertionPlainTo
	 *            the plain identity assertion data.
	 * @return the encrypted data in form of a transport object.
	 */
	private IdAssertionTo computeIdentityAssertion(IdentityAssertionPlainTo identityAssertionPlainTo) {
		IdAssertionTo idAssertion = new IdAssertionTo();
		idAssertion.setIdentityAssertionReceiverAddressBase64(
				identityAssertionPlainTo.getIdentityAssertionReceiverAddressBase64());

		String aesKeyBase64 = this.cryptoUtils.generateSecretAesKey();
		// TODO ctinnes remove this logging. it is for testing purposes only.
		LOG.info("THE SECRET KEY IS " + aesKeyBase64);
		String encryptedValueBase64 = this.cryptoUtils.encrypt(identityAssertionPlainTo.getIdentityAttributeValue(),
				aesKeyBase64, CryptoUtils.AES);
		String rsaEncryptedAesKey = this.cryptoUtils.encrypt(aesKeyBase64,
				identityAssertionPlainTo.getIdentityAssertionReceiverAddressBase64(), CryptoUtils.RSA);

		if (encryptedValueBase64 == null || rsaEncryptedAesKey == null) {
			LOG.warn("Identity Assertion can not be done with the given input data. Encryption failed.");
			throw new IllegalArgumentException(
					"Identity Assertion can not be done with the given input data. Encryption failed.");
		}

		idAssertion.setIdentityAssertionSecretEncBase64(rsaEncryptedAesKey);
		idAssertion.setIdentityAttributeName(identityAssertionPlainTo.getIdentityAttributeName());
		idAssertion.setIdentityAttributeValueEncBase64(encryptedValueBase64);
		return idAssertion;
	}

	/*
	 * TODO ctinnes this way of computing the signature of the message has to be
	 * discussed and has to be well documented. It is very strange indeed but it
	 * does its job.
	 */
	private String computeSignatureBase64(IdAssertionMessageTo message) {
		String messageString = message.toString();

		String signature = this.cryptoUtils.getSignatureBase64(messageString.getBytes(), this.privateKey);
		if (signature == null) {
			LOG.warn("Signature for the identity assertion could not be computed.");
			throw new IllegalArgumentException("Signature for the identity assertion could not be computed.");
		}
		return signature;
	}

}
