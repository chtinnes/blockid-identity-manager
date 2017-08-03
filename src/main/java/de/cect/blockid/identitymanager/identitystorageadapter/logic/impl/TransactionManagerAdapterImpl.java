package de.cect.blockid.identitymanager.identitystorageadapter.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.TransactionManagerAdapter;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcFetchAssertionsForReceiver;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcFetchSpecificAssertion;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcSendIdentityAssertion;

/**
 * Implementation of {@link TransactionManagerAdapter}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
public class TransactionManagerAdapterImpl implements TransactionManagerAdapter {

	@Autowired
	private UcSendIdentityAssertion sendIdentityAssertion;

	@Autowired
	private UcFetchSpecificAssertion fetchSpecificAssertion;

	@Autowired
	private UcFetchAssertionsForReceiver fetchAssertionsForReceiver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.
	 * UcSendIdentityAssertion#sendIdentityAssertion(de.cect.blockid.identitymanager.
	 * transactionmanageradapter.logic.api.to.IdAssertionMessageTo)
	 */
	public String sendIdentityAssertion(IdAssertionMessageTo idAssertionMessageTo) {
		return this.sendIdentityAssertion.sendIdentityAssertion(idAssertionMessageTo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.
	 * UcFetchSpecificAssertion#fetchSpecificIdentityAssertion(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public IdAssertionMessageTo fetchSpecificIdentityAssertion(String attributeName, String receiverAddressBase64,
			String senderAddressBase64) {

		return this.fetchSpecificAssertion.fetchSpecificIdentityAssertion(attributeName, receiverAddressBase64,
				senderAddressBase64);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.
	 * UcFetchAssertionsForReceiver#fetchSpecificIdentityAssertion(java.lang.String)
	 */
	@Override
	public List<IdAssertionMessageTo> fetchAssertionsForReceiver(String receiverAddressBase64) {
		return fetchAssertionsForReceiver.fetchAssertionsForReceiver(receiverAddressBase64);
	}

}
