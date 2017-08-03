package de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api;

import java.util.List;

/**
 * The interface for a business rule, which should serve as a restrictive rule
 * for some business service.
 * 
 * @param <V>
 *            The values for the subject allowed.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface BusinessRule<V> {

	/**
	 * Getter for the subject of the rule, e.g. the age.
	 * 
	 * @return the Subject of the rule.
	 */
	String getSubject();

	/**
	 * The values which are valid for the subject, e.g. 18.
	 * 
	 * @return the value(s).
	 */
	List<V> getApplicableValues();

	/**
	 * Should the rule be applied.
	 * 
	 * @return true if the rule should be applied.
	 */
	Boolean isActive();

	/**
	 * Applies the rule to the given value.
	 * 
	 * @param givenValue
	 * @return true if the value is valid for that rule.
	 */
	Boolean applyRule(Assertion<?> assertion);

}
