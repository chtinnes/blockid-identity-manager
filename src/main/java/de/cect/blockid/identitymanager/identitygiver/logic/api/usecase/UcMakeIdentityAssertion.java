package de.cect.blockid.identitymanager.identitygiver.logic.api.usecase;

import de.cect.blockid.identitymanager.identitygiver.logic.api.to.IdentityAssertionPlainTo;

/**
 * Use case for making identity assertions. Basically this is done, by taking
 * the plain assertion in the form <b>RECEIVER has ATTRIBUTE
 * ATTRIBUTE_VALUE</b>. Then, a secret (AES) key is computed and this key is
 * getting (RSA) encrypted with the receiver address which serves as a public
 * (RSA) key. The secret key is furthermore used to (AES) encrypt the the
 * ATTRIBUTE_VALUE.
 * 
 * This encrypted identity assertion is then combined with the sender address
 * and a signature to a signed message which is then broadcasted to some
 * identity store (e.g. Blockchain based).
 * 
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
/*
 * TODO ctinnes this is the "private use case" only, i.e. the application itself
 * is making identity assertions. Think of providing this as an external service
 * as well, i.e. others can send signed transactions. This could be done by
 * calling the service from the transaction manager directly, i.e. exposing it
 * to the public. Since this service should not be exposed to the public, there
 * should be some kind of IAM for this service. We should try to discuss this
 * issue from the business side, i.e. we should find out, when it makes sense to
 * expose such a service. Should this be a public blockchain? Then we definitely
 * don't need an exposed service...
 */
public interface UcMakeIdentityAssertion {
	/**
	 * Make an identity assertion, encrypt it and send it to a
	 * (blockchain)-transaction manager.
	 * 
	 * @param identityAssertionPlainTo
	 *            the plain identity assertion.
	 */
	void makeIdentityAssertion(IdentityAssertionPlainTo identityAssertionPlainTo);
}
