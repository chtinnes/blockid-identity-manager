package de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;

/**
 * Usecase for sending identity assertions.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface UcSendIdentityAssertion {
	/**
	 * Sends the given object to the transaction manager.
	 * 
	 * @param idAssertionMessageTo
	 *            the transport object representing the identity assertion.
	 * @return answer from the transaction manager.
	 */
	String sendIdentityAssertion(IdAssertionMessageTo idAssertionMessageTo);

}
