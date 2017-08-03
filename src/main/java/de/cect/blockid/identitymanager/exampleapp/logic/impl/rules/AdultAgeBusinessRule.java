package de.cect.blockid.identitymanager.exampleapp.logic.impl.rules;

import java.util.Arrays;
import java.util.List;

import de.cect.blockid.identitymanager.exampleapp.logic.api.BusinessSubject;
import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.Assertion;
import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.BusinessRule;

/**
 * Implementation of {@link BusinessRule} which checks that a given assertion
 * states that the age exceeds a configurable age.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class AdultAgeBusinessRule implements BusinessRule<Integer> {
	private final List<Integer> applicableValues;

	public AdultAgeBusinessRule(Integer minAge) {
		this.applicableValues = Arrays.asList(minAge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.BusinessRule#
	 * getSubject()
	 */
	@Override
	public String getSubject() {
		return BusinessSubject.AGE.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.BusinessRule#
	 * getApplicableValues()
	 */
	@Override
	public List<Integer> getApplicableValues() {
		return applicableValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.BusinessRule#
	 * isActive()
	 */
	@Override
	public Boolean isActive() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.BusinessRule#
	 * applyRule(de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.
	 * Assertion)
	 */
	@Override
	public Boolean applyRule(Assertion<?> assertion) {
		if (assertion.getValue().getClass() != Integer.class) {
			throw new ClassCastException();
		}
		return assertion.getSubject().equalsIgnoreCase(this.getSubject())
				&& (Integer) assertion.getValue() > this.applicableValues.get(0);
	}

}
