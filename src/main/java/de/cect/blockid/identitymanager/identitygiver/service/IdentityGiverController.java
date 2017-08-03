package de.cect.blockid.identitymanager.identitygiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationConstants;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.identitygiver.logic.api.IdentityGiver;
import de.cect.blockid.identitymanager.identitygiver.logic.api.to.IdentityAssertionPlainTo;

/**
 * The REST Controller for the Identity Manager Application.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@RestController
@RequestMapping(path = ApplicationConstants.REST_ROOT_PATH
		+ ApplicationConstants.IDENTITYGIVER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Profile(ApplicationProfiles.IDGIVER)
public class IdentityGiverController {

	@Autowired
	private IdentityGiver identityGiver;

	@RequestMapping(path = "/version", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public String getVersion() {
		return "{\"version\": \"" + ApplicationConstants.APP_VERSION + "\"}";
	}

	// TODO ctinnes proper response type here
	@RequestMapping(path = "/makeAssertion", method = RequestMethod.POST)
	public String makeAssertion(@RequestBody IdentityAssertionPlainTo identityAssertion) {
		this.identityGiver.makeIdentityAssertion(identityAssertion);
		return "{\"response\": \"Dummy response\"}";
	}

}
