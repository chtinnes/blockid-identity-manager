package de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to;

/**
 * Object for outgoing REST request asking for all identity assertions for a
 * given receiver.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class RequestForIdentityReceiver {

	private String receiverAddressBase64;

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
