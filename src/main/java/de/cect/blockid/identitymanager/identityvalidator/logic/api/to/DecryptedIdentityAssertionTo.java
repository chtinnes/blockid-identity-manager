package de.cect.blockid.identitymanager.identityvalidator.logic.api.to;

/**
 * A transport object for a decrypted identity assertion, i.e. an identity
 * assertion with plain text attribute value.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class DecryptedIdentityAssertionTo {

	private String identityAssertionReceiverAddressBase64;
	private String identityAssertionSenderAddressBase64;
	private String identityAttributeName;
	private String identityAttributeValue;

	/**
	 * @return the identityAssertionReceiverAddressBase64
	 */
	public String getIdentityAssertionReceiverAddressBase64() {
		return identityAssertionReceiverAddressBase64;
	}

	/**
	 * @param identityAssertionReceiverAddressBase64
	 *            the identityAssertionReceiverAddressBase64 to set
	 */
	public void setIdentityAssertionReceiverAddressBase64(String identityAssertionReceiverAddressBase64) {
		this.identityAssertionReceiverAddressBase64 = identityAssertionReceiverAddressBase64;
	}

	/**
	 * @return the identityAssertionSenderAddressBase64
	 */
	public String getIdentityAssertionSenderAddressBase64() {
		return identityAssertionSenderAddressBase64;
	}

	/**
	 * @param identityAssertionSenderAddressBase64
	 *            the identityAssertionSenderAddressBase64 to set
	 */
	public void setIdentityAssertionSenderAddressBase64(String identityAssertionSenderAddressBase64) {
		this.identityAssertionSenderAddressBase64 = identityAssertionSenderAddressBase64;
	}

	/**
	 * @return the identityAttributeName
	 */
	public String getIdentityAttributeName() {
		return identityAttributeName;
	}

	/**
	 * @param identityAttributeName
	 *            the identityAttributeName to set
	 */
	public void setIdentityAttributeName(String identityAttributeName) {
		this.identityAttributeName = identityAttributeName;
	}

	/**
	 * @return the identityAttributeValue
	 */
	public String getIdentityAttributeValue() {
		return identityAttributeValue;
	}

	/**
	 * @param identityAttributeValue
	 *            the identityAttributeValue to set
	 */
	public void setIdentityAttributeValue(String identityAttributeValue) {
		this.identityAttributeValue = identityAttributeValue;
	}

}
