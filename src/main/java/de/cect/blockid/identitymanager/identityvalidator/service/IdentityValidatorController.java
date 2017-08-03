package de.cect.blockid.identitymanager.identityvalidator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationConstants;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.IdentityValidator;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.to.DecryptedIdentityAssertionTo;
import de.cect.blockid.identitymanager.identityvalidator.service.api.RequestForDecryptedSpecificIdentityAssertion;

/**
 * The REST Controller for the Identity Manager Application.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@RestController
@RequestMapping(path = ApplicationConstants.REST_ROOT_PATH
		+ ApplicationConstants.IDENTITYVALIDATOR_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Profile(ApplicationProfiles.IDVALIDATOR)
public class IdentityValidatorController {

	@Autowired
	private IdentityValidator identityValidator;

	@RequestMapping(path = "/version", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public String getVersion() {
		return "{\"version\": \"" + ApplicationConstants.APP_VERSION + "\"}";
	}

	// TODO ctinnes request validation here (should be simple annotations)
	@RequestMapping(path = "/decrypt", method = RequestMethod.POST)
	public DecryptedIdentityAssertionTo makeAssertion(
			@RequestBody RequestForDecryptedSpecificIdentityAssertion request) {
		return this.identityValidator.decryptIdentityAssertion(request.getIdentityAttributeName(),
				request.getReceiverAddressBase64(), request.getSenderAddressBase64(), request.getSecretKeyBase64());
	}

}
