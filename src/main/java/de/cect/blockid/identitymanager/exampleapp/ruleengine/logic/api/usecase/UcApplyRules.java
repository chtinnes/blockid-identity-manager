package de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.usecase;

import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.Assertion;

/**
 * Use case for applying all known rules to some assertion.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public interface UcApplyRules {

	/**
	 * Applies all known rules to the given assertion and returns true only if all
	 * applicable rules are obeyed.
	 * 
	 * @param assertion
	 *            the given assertion
	 * @return true only if all applicable rules are obeyed.
	 */
	Boolean applyRules(Assertion<?> assertion);

}
