package de.cect.blockid.identitymanager.identitygiver.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.identitygiver.logic.api.IdentityGiver;
import de.cect.blockid.identitymanager.identitygiver.logic.api.to.IdentityAssertionPlainTo;
import de.cect.blockid.identitymanager.identitygiver.logic.api.usecase.UcMakeIdentityAssertion;

/**
 * Implementation of {@link IdentityGiver}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Profile(ApplicationProfiles.IDGIVER)
public class IdentityGiverImpl implements IdentityGiver {

	@Autowired
	private UcMakeIdentityAssertion makeIdentityAssertion;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identitygiver.logic.api.usecase.
	 * UcMakeIdentityAssertion#makeIdentityAssertion(de.cect.blockid.identitymanager.
	 * identitygiver.logic.api.to.IdentityAssertionPlainTo)
	 */
	@Override
	public void makeIdentityAssertion(IdentityAssertionPlainTo identityAssertionPlainTo) {
		this.makeIdentityAssertion.makeIdentityAssertion(identityAssertionPlainTo);

	}

}
