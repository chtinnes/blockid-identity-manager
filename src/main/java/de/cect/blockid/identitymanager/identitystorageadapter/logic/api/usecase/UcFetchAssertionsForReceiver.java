package de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase;

import java.util.List;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;

/**
 * Usecase for fetching identity assertions for a given receiver.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface UcFetchAssertionsForReceiver {

	/**
	 * Fetches identity assertions for a given receiver from the identity storage.
	 * 
	 * @param receiverAddressBase64
	 *            the receiver address
	 * @return transport object for the identity assertion message
	 */
	List<IdAssertionMessageTo> fetchAssertionsForReceiver(String receiverAddressBase64);

}
