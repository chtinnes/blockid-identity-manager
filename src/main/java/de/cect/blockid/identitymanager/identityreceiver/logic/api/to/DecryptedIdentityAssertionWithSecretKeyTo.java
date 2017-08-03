package de.cect.blockid.identitymanager.identityreceiver.logic.api.to;

/**
 * A transport object for a decrypted identity assertion, i.e. an identity
 * assertion with plain text attribute value including the secret key that has
 * been used to encrypt the identity attribute value.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class DecryptedIdentityAssertionWithSecretKeyTo {

	private String receiverAddressBase64;
	private String senderAddressBase64;
	private String identityAttributeName;
	private String identityAttributeValue;
	private String secretKeyBase64;

	/**
	 * @return the secretKeyBase64
	 */
	public String getSecretKeyBase64() {
		return secretKeyBase64;
	}

	/**
	 * @param secretKeyBase64
	 *            the secretKeyBase64 to set
	 */
	public void setSecretKeyBase64(String secretKey) {
		this.secretKeyBase64 = secretKey;
	}

	/**
	 * @return the receiverAddressBase64
	 */
	public String getReceiverAddressBase64() {
		return receiverAddressBase64;
	}

	/**
	 * @param receiverAddressBase64
	 *            the receiverAddressBase64 to set
	 */
	public void setReceiverAddressBase64(String identityAssertionReceiverAddressBase64) {
		this.receiverAddressBase64 = identityAssertionReceiverAddressBase64;
	}

	/**
	 * @return the senderAddressBase64
	 */
	public String getSenderAddressBase64() {
		return senderAddressBase64;
	}

	/**
	 * @param senderAddressBase64
	 *            the senderAddressBase64 to set
	 */
	public void setSenderAddressBase64(String identityAssertionSenderAddressBase64) {
		this.senderAddressBase64 = identityAssertionSenderAddressBase64;
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
