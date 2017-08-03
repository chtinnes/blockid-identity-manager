package de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;

/**
 * Usecase for fetching a specific identity assertion.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface UcFetchSpecificAssertion {

	/**
	 * Fetches a specific identity assertion defined by attribute name, receiver and
	 * sender from the identity storage.
	 * 
	 * @param attributeName
	 *            the identity attribute name
	 * @param receiverAddressBase64
	 *            the receiver address
	 * @param senderAddressBase64
	 *            the sender address
	 * @return transport object for the identity assertion message
	 */
	IdAssertionMessageTo fetchSpecificIdentityAssertion(String attributeName, String receiverAddressBase64,
			String senderAddressBase64);

}
