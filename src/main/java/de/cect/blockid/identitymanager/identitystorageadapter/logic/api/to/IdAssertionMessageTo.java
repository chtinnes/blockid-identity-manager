package de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to;

/**
 * 
 * Transport object for Identity Assertion Messages which are received from the
 * transaction manager.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class IdAssertionMessageTo {

	private String identityAssertionSenderAddressBase64;

	private String identityAssertionSignatureBase64;

	private IdAssertionTo identityAssertion;

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
	 * @return the identityAssertionSignatureBase64
	 */
	public String getIdentityAssertionSignatureBase64() {
		return identityAssertionSignatureBase64;
	}

	/**
	 * @param identityAssertionSignatureBase64
	 *            the identityAssertionSignatureBase64 to set
	 */
	public void setIdentityAssertionSignatureBase64(String identityAssertionSignatureBase64) {
		this.identityAssertionSignatureBase64 = identityAssertionSignatureBase64;
	}

	/**
	 * @return the identityAssertion
	 */
	public IdAssertionTo getIdentityAssertion() {
		return identityAssertion;
	}

	/**
	 * @param identityAssertion
	 *            the identityAssertion to set
	 */
	public void setIdentityAssertion(IdAssertionTo identityAssertion) {
		this.identityAssertion = identityAssertion;
	}

	@Override
	public String toString() {
		return this.identityAssertionSenderAddressBase64 + this.getIdentityAssertion().toString();
	}

}
