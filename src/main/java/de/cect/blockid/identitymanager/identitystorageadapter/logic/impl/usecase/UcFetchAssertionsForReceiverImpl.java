package de.cect.blockid.identitymanager.identitystorageadapter.logic.impl.usecase;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.RequestForIdentityReceiver;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcFetchAssertionsForReceiver;

/**
 * Implementation of {@link UcFetchAssertionsForReceiver}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
public class UcFetchAssertionsForReceiverImpl implements UcFetchAssertionsForReceiver {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${blockid.transactionmanager.server.address}")
	private String serverAddress;

	@Value("${blockid.transactionmanager.server.rest.rootpath}")
	private String rootPath;

	@Value("${blockid.transactionmanager.server.rest.path.getallforreceiver}")
	private String endpoint;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.
	 * UcFetchAssertionsForReceiver#fetchSpecificIdentityAssertion(java.lang.String)
	 */
	@Override
	public List<IdAssertionMessageTo> fetchAssertionsForReceiver(String receiverAddressBase64) {
		RequestForIdentityReceiver request = new RequestForIdentityReceiver();
		request.setReceiverAddressBase64(receiverAddressBase64);
		return Arrays.asList(this.restTemplate
				.postForEntity(serverAddress + rootPath + endpoint, request, IdAssertionMessageTo[].class).getBody());
	}

}
