package de.cect.blockid.identitymanager.identityreceiver.logic.impl.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.gerneral.common.CryptoUtils;
import de.cect.blockid.identitymanager.identityreceiver.logic.api.to.DecryptedIdentityAssertionWithSecretKeyTo;
import de.cect.blockid.identitymanager.identityreceiver.logic.api.usecase.UcFetchOwnIdentityAssertions;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.TransactionManagerAdapter;
import de.cect.blockid.identitymanager.identitystorageadapter.logic.api.to.IdAssertionMessageTo;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.IdentityValidator;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.to.DecryptedIdentityAssertionTo;

/**
 * Implementation of {@link UcFetchOwnIdentityAssertions} which stores the
 * decrypted assertions in a List.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Profile(ApplicationProfiles.IDRECEIVER)
// TODO ctinnes think of storing own assertions is database or some kind of
// cache.
public class UcFetchOwnIdentityAssertionsImpl implements UcFetchOwnIdentityAssertions {
	private static final Logger LOG = LoggerFactory.getLogger(UcFetchOwnIdentityAssertionsImpl.class);
	@Autowired
	private IdentityValidator identityValidator;

	@Autowired
	private TransactionManagerAdapter transactionManagerAdapter;

	@Autowired
	CryptoUtils cryptoUtils;

	@Value("${blockid.identitymanager.publickey.base64}")
	private String publicKey;

	@Value("${blockid.identitymanager.privatekey.base64}")
	private String privateKey;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identityreceiver.logic.api.usecase.
	 * UcFetchOwnIdentityAssertions#fetchOwnIdentityAssertions()
	 */
	@Override
	public List<DecryptedIdentityAssertionWithSecretKeyTo> fetchOwnIdentityAssertions() {
		List<IdAssertionMessageTo> identityAssertionMessages = transactionManagerAdapter
				.fetchAssertionsForReceiver(publicKey);
		LOG.debug("Fetched " + identityAssertionMessages.size() + " identity assertions for configured user.");
		return identityAssertionMessages.stream().map(message -> decryptMessage(message)).collect(Collectors.toList());
	}

	private DecryptedIdentityAssertionWithSecretKeyTo decryptMessage(IdAssertionMessageTo message) {
		String key = this.cryptoUtils.decrypt(message.getIdentityAssertion().getIdentityAssertionSecretEncBase64(),
				this.privateKey, CryptoUtils.RSA);
		DecryptedIdentityAssertionWithSecretKeyTo decryptedAssertionWithKey = new DecryptedIdentityAssertionWithSecretKeyTo();
		DecryptedIdentityAssertionTo decryptedAssertion = this.identityValidator.decryptIdentityAssertion(message, key);
		BeanUtils.copyProperties(decryptedAssertion, decryptedAssertionWithKey);
		decryptedAssertionWithKey.setSecretKeyBase64(key);
		return decryptedAssertionWithKey;
	}

}
