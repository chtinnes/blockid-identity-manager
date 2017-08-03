package de.cect.blockid.identitymanager.identityreceiver.logic.api.usecase;

import java.util.List;

import de.cect.blockid.identitymanager.identityreceiver.logic.api.to.DecryptedIdentityAssertionWithSecretKeyTo;

/**
 * Use case for fetching and decrypting identity assertions that are made for
 * the application owner.
 *
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface UcFetchOwnIdentityAssertions {

	/**
	 * Fetches identity assertions for the application owner from the storage and
	 * decrypts them.
	 * 
	 * @return list of decrypted identity assertions.
	 */
	List<DecryptedIdentityAssertionWithSecretKeyTo> fetchOwnIdentityAssertions();

}
