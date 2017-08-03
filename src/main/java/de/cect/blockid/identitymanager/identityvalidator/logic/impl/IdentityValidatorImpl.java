package de.cect.blockid.identitymanager.identityvalidator.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.IdentityValidator;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.to.DecryptedIdentityAssertionTo;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.usecase.UcDecryptIdentityAssertion;

/**
 * Implementation of {@link IdentityValidator}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Profile(ApplicationProfiles.IDVALIDATOR)
public class IdentityValidatorImpl implements IdentityValidator {

	@Autowired
	private UcDecryptIdentityAssertion decryptIdentityAssertion;

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
		return this.decryptIdentityAssertion.decryptIdentityAssertion(attributeName, receiverAddressBase64,
				senderAddressBase64, keyBase64);
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
		return this.decryptIdentityAssertion.decryptIdentityAssertion(assertionMessage, keyBase64);
	}

}
