package de.cect.blockid.identitymanager.exampleapp.service.api;

/**
 * Response object for incoming REST request asking for a validation of a
 * specific referenced assertion.
 *
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class ResponseForValidationRequest {

	private String receiverAddressBase64;

	private String senderAddressBase64;

	private String identityAttributeName;

	private Boolean isValid;

	/**
	 * @return the isValid
	 */
	public Boolean getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid
	 *            the isValid to set
	 */
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
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
	public void setSenderAddressBase64(String senderAddressBase64) {
		this.senderAddressBase64 = senderAddressBase64;
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
	 * @return the receiverAddressBase64
	 */
	public String getReceiverAddressBase64() {
		return receiverAddressBase64;
	}

	/**
	 * @param receiverAddressBase64
	 *            the receiverAddressBase64 to set
	 */
	public void setReceiverAddressBase64(String receiverAddressBase64) {
		this.receiverAddressBase64 = receiverAddressBase64;
	}

}
