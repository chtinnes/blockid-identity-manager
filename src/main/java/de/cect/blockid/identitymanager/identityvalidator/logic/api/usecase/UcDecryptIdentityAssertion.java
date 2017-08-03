package de.cect.blockid.identitymanager.identityvalidator.logic.api.usecase;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.to.DecryptedIdentityAssertionTo;

/**
 * Use case for fetching a referenced identity assertion and decrypt it with a
 * given key.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface UcDecryptIdentityAssertion {
	/**
	 * Fetch identity assertion defined by name, receiver and sender and decrypts
	 * its attribute value by use of the given key and validate the signature.
	 * 
	 * @param attributeName
	 *            the identity attribute name
	 * @param receiverAddressBase64
	 *            the receiver address
	 * @param senderAddressBase64
	 *            the sender address
	 * @param keyBase64
	 *            the key to decrypt the encrypted attribute value with.
	 * @return transport object for the decrypted identity assertion or null if
	 *         something goes wrong or validation failed.
	 */
	DecryptedIdentityAssertionTo decryptIdentityAssertion(String attributeName, String receiverAddressBase64,
			String senderAddressBase64, String keyBase64);

	/**
	 * Takes the givven assertion message and decrypts its attribute value by use of
	 * the given key and validate the signature.
	 * 
	 * @param assertionMessage
	 *            the identity assertion message
	 * @param keyBase64
	 *            the key to decrypt the encrypted attribute value with.
	 * @return transport object for the decrypted identity assertion or null if
	 *         something goes wrong or validation failed.
	 */
	DecryptedIdentityAssertionTo decryptIdentityAssertion(IdAssertionMessageTo assertionMessage, String keyBase64);

}
