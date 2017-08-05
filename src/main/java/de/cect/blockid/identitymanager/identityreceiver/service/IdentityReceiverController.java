package de.cect.blockid.identitymanager.identityreceiver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationConstants;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.identityreceiver.logic.api.IdentityReceiver;
import de.cect.blockid.identitymanager.identityreceiver.logic.api.to.DecryptedIdentityAssertionWithSecretKeyTo;

/**
 * The REST Controller for the Identity Receiver Component.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@RestController
@RequestMapping(path = ApplicationConstants.REST_ROOT_PATH
		+ ApplicationConstants.IDENTITYRECEIVER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Profile(ApplicationProfiles.IDRECEIVER)
public class IdentityReceiverController {

	@Autowired
	private IdentityReceiver identityReceiver;

	@Value("${blockid.identitymanager.publickey.base64}")
	private String publicKeyBase64;

	@RequestMapping(path = "/version", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public String getVersion() {
		return "{\"version\": \"" + ApplicationConstants.APP_VERSION + "\"}";
	}

	public String getPublicKey() {
		return "{\"key\": \"" + this.publicKeyBase64 + "\"}";
	}

	// TODO ctinnes request validation here (should be simple annotations)
	@RequestMapping(path = "/fetchown", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public List<DecryptedIdentityAssertionWithSecretKeyTo> getOwnAssertions() {
		return this.identityReceiver.fetchOwnIdentityAssertions();
	}

}
