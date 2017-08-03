package de.cect.blockid.identitymanager.identityvalidator.logic.impl.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.gerneral.common.CryptoUtils;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.TransactionManagerAdapter;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.to.DecryptedIdentityAssertionTo;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.usecase.UcDecryptIdentityAssertion;

/**
 * Implementation of {@link UcDecryptIdentityAssertion}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Primary
@Profile(ApplicationProfiles.IDVALIDATOR)
public class UcDecryptIdentityAssertionImpl implements UcDecryptIdentityAssertion {
	private static final Logger LOG = LoggerFactory.getLogger(UcDecryptIdentityAssertionImpl.class);

	@Autowired
	private CryptoUtils cryptoUtils;

	@Autowired
	private TransactionManagerAdapter transactionManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identityvalidator.logic.api.usecase.
	 * UcDecryptIdentityAssertion#decryptIdentityAssertion(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DecryptedIdentityAssertionTo decryptIdentityAssertion(String attributeName, String receiverAddressBase64,
			String senderAddressBase64, String keyBase64) {
		DecryptedIdentityAssertionTo decryptedIdentityAssertion = new DecryptedIdentityAssertionTo();
		IdAssertionMessageTo assertionMessage = this.transactionManager.fetchSpecificIdentityAssertion(attributeName,
				receiverAddressBase64, senderAddressBase64);

		if (assertionMessage == null) {
			LOG.warn("No idenity assertion could be fetched for the given parameters.");
			decryptedIdentityAssertion = null;
		} else {
			decryptedIdentityAssertion = decryptIdentityAssertion(assertionMessage, keyBase64);
		}
		LOG.debug("Decrypted identity assertion for " + receiverAddressBase64 + ".");
		return decryptedIdentityAssertion;
	}

	private DecryptedIdentityAssertionTo decryptIdentityAttributeValue(String keyBase64,
			DecryptedIdentityAssertionTo decryptedIdentityAssertion, IdAssertionMessageTo assertionMessage) {
		String decryptedAttributeValue = this.cryptoUtils.decrypt(
				assertionMessage.getIdentityAssertion().getIdentityAttributeValueEncBase64(), keyBase64,
				CryptoUtils.AES);
		if (decryptedAttributeValue == null) {
			LOG.warn("Encrypted attribute value could not be decrypted with the given key.");
			decryptedIdentityAssertion = null;
		} else {
			decryptedIdentityAssertion.setIdentityAttributeValue(decryptedAttributeValue);
		}
		return decryptedIdentityAssertion;
	}

	private DecryptedIdentityAssertionTo verifySignature(DecryptedIdentityAssertionTo decryptedIdentityAssertion,
			IdAssertionMessageTo assertionMessage) {
		if (!this.cryptoUtils.verifySignatureBase64(assertionMessage.toString().getBytes(),
				assertionMessage.getIdentityAssertionSignatureBase64(),
				assertionMessage.getIdentityAssertionSenderAddressBase64())) {
			LOG.warn("Signature validation failed for message " + assertionMessage.toString());
			decryptedIdentityAssertion = null;
		}
		return decryptedIdentityAssertion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identityvalidator.logic.api.usecase.
	 * UcDecryptIdentityAssertion#decryptIdentityAssertion(de.igovlab.
	 * identitymanager.transactionmanageradapter.logic.api.to.IdAssertionMessageTo,
	 * java.lang.String)
	 */
	@Override
	public DecryptedIdentityAssertionTo decryptIdentityAssertion(IdAssertionMessageTo assertionMessage,
			String keyBase64) {
		DecryptedIdentityAssertionTo decryptedIdentityAssertion = new DecryptedIdentityAssertionTo();

		decryptedIdentityAssertion = decryptIdentityAttributeValue(keyBase64, decryptedIdentityAssertion,
				assertionMessage);
		decryptedIdentityAssertion.setIdentityAssertionReceiverAddressBase64(
				assertionMessage.getIdentityAssertion().getIdentityAssertionReceiverAddressBase64());
		decryptedIdentityAssertion
				.setIdentityAssertionSenderAddressBase64(assertionMessage.getIdentityAssertionSenderAddressBase64());
		decryptedIdentityAssertion
				.setIdentityAttributeName(assertionMessage.getIdentityAssertion().getIdentityAttributeName());
		if (decryptedIdentityAssertion != null) {
			decryptedIdentityAssertion = verifySignature(decryptedIdentityAssertion, assertionMessage);
		}

		LOG.debug("Decrypted identity assertion for "
				+ assertionMessage.getIdentityAssertion().getIdentityAssertionReceiverAddressBase64() + ".");
		return decryptedIdentityAssertion;
	}

}
