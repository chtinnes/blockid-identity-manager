package de.cect.blockid.identitymanager.exampleapp.logic.impl.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.exampleapp.logic.api.BusinessSubject;
import de.cect.blockid.identitymanager.exampleapp.logic.api.usecase.UcValidateIdentityAssertion;
import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.Assertion;
import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.usecase.UcApplyRules;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.IdentityValidator;
import de.cect.blockid.identitymanager.identityvalidator.logic.api.to.DecryptedIdentityAssertionTo;

/**
 * Implementation of {@link UcValidateIdentityAssertion}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Profile(ApplicationProfiles.EXAMPLEAPP)
public class UcValidateIdentityAssertionImpl implements UcValidateIdentityAssertion {
	private static final Logger LOG = LoggerFactory.getLogger(UcValidateIdentityAssertionImpl.class);

	@Autowired
	IdentityValidator identityValidator;

	@Autowired
	UcApplyRules applyRules;

	@Autowired
	ConversionService conversionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.identityvalidator.logic.api.usecase.
	 * UcValidateIdentityAssertion#validateIdentityAssertion(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@Override
	public Boolean validateIdentityAssertion(String attributeName, String receiverAddressBase64,
			String senderAddressBase64, String secretKeyBase64) {
		DecryptedIdentityAssertionTo decyptedIdentityAssertion = this.identityValidator
				.decryptIdentityAssertion(attributeName, receiverAddressBase64, senderAddressBase64, secretKeyBase64);
		if (decyptedIdentityAssertion != null) {
			BusinessSubject subject = BusinessSubject.fromDescription(attributeName);
			Assertion<?> assertion = createTypedAssertion(subject.getType(), decyptedIdentityAssertion);
			if (assertion != null) {
				boolean isValid = this.applyRules.applyRules(assertion);
				if (isValid) {
					LOG.debug("Identity assertion validation for " + receiverAddressBase64
							+ " succeeded and assertion is valid.");
					return true;
				}
			}

		}
		LOG.debug("Identity assertion for " + receiverAddressBase64
				+ " could not be validated properly or is invalid. Returning false.");
		return false;

	}

	/**
	 * Creates an assertion of the given type.
	 * 
	 * @param <T>
	 *            the type parameter
	 * @param type
	 *            the type.
	 * @return assertion of the given type.
	 */
	private <T> Assertion<T> createTypedAssertion(Class<T> type, DecryptedIdentityAssertionTo identityAssertion) {
		Assertion<T> assertion = new Assertion<T>();
		T parsedValue = parseAttributeValue(identityAssertion.getIdentityAttributeValue(), type);
		if (parsedValue == null) {
			return null;
		}
		assertion.setSubject(identityAssertion.getIdentityAttributeName());
		assertion.setValue(parsedValue);
		return assertion;
	}

	/**
	 * Parses the given string into the given class.
	 * 
	 * @param identityAttributeValue
	 *            the attribute value as string.
	 * @param type
	 *            the type to convert the string into.
	 * @return the typed value.
	 */
	private <T> T parseAttributeValue(String identityAttributeValue, Class<T> type) {
		T value = null;
		value = this.conversionService.convert(identityAttributeValue, type);

		if (value == null) {
			LOG.error("The given type for the identity assertion is not known.");
			return null;
		}
		return value;
	}

}
