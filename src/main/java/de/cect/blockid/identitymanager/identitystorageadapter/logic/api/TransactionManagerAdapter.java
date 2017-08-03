package de.cect.blockid.identitymanager.identitystorageadapter.logic.api;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcFetchAssertionsForReceiver;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcFetchSpecificAssertion;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcSendIdentityAssertion;

/**
 * Component facade for the transaction manager adapter used to communicate with
 * the transaction manager.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface TransactionManagerAdapter
		extends UcSendIdentityAssertion, UcFetchSpecificAssertion, UcFetchAssertionsForReceiver {

}
