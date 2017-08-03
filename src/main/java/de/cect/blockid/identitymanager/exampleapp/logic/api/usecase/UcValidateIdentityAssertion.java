package de.cect.blockid.identitymanager.exampleapp.logic.api.usecase;

/**
 * Use case for validating identity assertion. This use case takes a reference
 * to a given identity assertion and a given secret(used to encrypt the
 * attribute value) and validates against given business rules.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface UcValidateIdentityAssertion {

	/**
	 * Validates the referenced identity assertion and applies defined business
	 * rules to it.
	 * 
	 * 
	 * @param attributeName
	 * @param receiverAddressBase64
	 * @param senderAddressBase64
	 * @param secretKey
	 *            the secret key which encrypts the attributes value.
	 * @return true if all applicable business rules a verified and the identity
	 *         assertion is valid.
	 */
	Boolean validateIdentityAssertion(String attributeName, String receiverAddressBase64, String senderAddressBase64,
			String secretKeyBase64);

}
