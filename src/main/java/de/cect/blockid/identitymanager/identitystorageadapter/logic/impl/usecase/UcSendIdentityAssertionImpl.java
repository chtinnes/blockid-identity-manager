package de.cect.blockid.identitymanager.identitystorageadapter.logic.impl.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.UcSendIdentityAssertion;

/**
 * Implementation of {@link UcSendIdentityAssertion}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
public class UcSendIdentityAssertionImpl implements UcSendIdentityAssertion {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${blockid.transactionmanager.server.address}")
	private String serverAddress;

	@Value("${blockid.transactionmanager.server.rest.rootpath}")
	private String rootPath;

	@Value("${blockid.transactionmanager.server.rest.path.sendidentityassertion}")
	private String endpoint;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identitystorageadapter.logic.api.usecase.
	 * UcSendIdentityAssertion#sendIdentityAssertion(de.cect.blockid.identitymanager.
	 * transactionmanageradapter.logic.api.to.IdAssertionMessageTo)
	 */
	public String sendIdentityAssertion(IdAssertionMessageTo idAssertionMessageTo) {
		return this.restTemplate.postForEntity(serverAddress + rootPath + endpoint, idAssertionMessageTo, String.class)
				.getBody();
	}

}
