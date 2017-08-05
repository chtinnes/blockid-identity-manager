package de.cect.blockid.identitymanager.exampleapp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.cect.blockid.identitymanager.exampleapp.logic.api.usecase.UcValidateIdentityAssertion;
import de.cect.blockid.identitymanager.exampleapp.service.api.RequestForValidation;
import de.cect.blockid.identitymanager.exampleapp.service.api.ResponseForValidationRequest;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationConstants;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;

/**
 * The REST Controller for the example application.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@RestController
@RequestMapping(path = ApplicationConstants.REST_ROOT_PATH
		+ ApplicationConstants.EXAMPLEAPP_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Profile(ApplicationProfiles.EXAMPLEAPP)
public class ExampleAppController {

	@Autowired
	private UcValidateIdentityAssertion validateIdentityAssertion;

	@RequestMapping(path = "/version", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public String getVersion() {
		return "{\"version\": \"" + ApplicationConstants.APP_VERSION + "\"}";
	}

	// TODO ctinnes request validation here (should be simple annotations)
	@RequestMapping(path = "/validate", method = RequestMethod.POST)
	public ResponseForValidationRequest validate(@RequestBody RequestForValidation request) {
		ResponseForValidationRequest response = new ResponseForValidationRequest();
		BeanUtils.copyProperties(request, response);
		response.setIsValid(this.validateIdentityAssertion.validateIdentityAssertion(request.getIdentityAttributeName(),
				request.getReceiverAddressBase64(), request.getSenderAddressBase64(), request.getSecretKeyBase64()));
		return response;
	}

}
