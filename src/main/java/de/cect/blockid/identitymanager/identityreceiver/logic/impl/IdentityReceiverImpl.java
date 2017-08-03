package de.cect.blockid.identitymanager.identityreceiver.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.identityreceiver.logic.api.IdentityReceiver;
import de.cect.blockid.identitymanager.identityreceiver.logic.api.to.DecryptedIdentityAssertionWithSecretKeyTo;
import de.cect.blockid.identitymanager.identityreceiver.logic.api.usecase.UcFetchOwnIdentityAssertions;

/**
 * Implementation of {@link IdentityReceiver}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Profile(ApplicationProfiles.IDRECEIVER)
public class IdentityReceiverImpl implements IdentityReceiver {

	@Autowired
	private UcFetchOwnIdentityAssertions fetchOwnIdentityAssertions;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identityreceiver.logic.api.usecase.
	 * UcFetchOwnIdentityAssertions#fetchOwnIdentityAssertions()
	 */
	@Override
	public List<DecryptedIdentityAssertionWithSecretKeyTo> fetchOwnIdentityAssertions() {
		return this.fetchOwnIdentityAssertions.fetchOwnIdentityAssertions();
	}

}
