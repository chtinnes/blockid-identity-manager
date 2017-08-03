package de.cect.blockid.identitymanager.identitystorageadapter.logic.impl.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.RequestForSpecificIdentityAssertion;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcFetchSpecificAssertion;

/**
 * Implementation of {@link UcFetchSpecificAssertion}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
public class UcFetchSpecificAssertionImpl implements UcFetchSpecificAssertion {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${blockid.transactionmanager.server.address}")
	private String serverAddress;

	@Value("${blockid.transactionmanager.server.rest.rootpath}")
	private String rootPath;

	@Value("${blockid.transactionmanager.server.rest.path.getassertion}")
	private String endpoint;

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
		RequestForSpecificIdentityAssertion request = new RequestForSpecificIdentityAssertion();
		request.setIdentityAttributeName(attributeName);
		request.setReceiverAddressBase64(receiverAddressBase64);
		request.setSenderAddressBase64(senderAddressBase64);
		return this.restTemplate.postForEntity(serverAddress + rootPath + endpoint, request, IdAssertionMessageTo.class)
				.getBody();
	}

}
