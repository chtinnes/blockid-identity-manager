package de.cect.blockid.identitymanager.identitygiver.logic.api.to;

/**
 * Transport object for an identity assertion in the identity manager.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class IdentityAssertionPlainTo {

	private String identityAssertionReceiverAddressBase64;
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
