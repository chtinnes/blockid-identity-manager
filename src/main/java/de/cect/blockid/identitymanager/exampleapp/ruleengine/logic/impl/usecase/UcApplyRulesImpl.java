package de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.impl.usecase;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.Assertion;
import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.BusinessRule;
import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.usecase.UcApplyRules;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;

/**
 * Implementation of {@link UcApplyRules}.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Component
@Profile(ApplicationProfiles.EXAMPLEAPP)
public class UcApplyRulesImpl implements UcApplyRules {

	@Autowired(required = false)
	private List<BusinessRule<?>> ruleList;

	private Map<String, List<BusinessRule<?>>> groupedRules;

	/**
	 * Group rules by subject, so to speed up rule application.
	 */
	@PostConstruct
	public void onInit() {
		if (this.ruleList != null) {
			this.groupedRules = this.ruleList.stream().collect(groupingBy(BusinessRule::getSubject));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.usecase.
	 * UcApplyRules#applyRules(de.cect.blockid.identitymanager.exampleapp.ruleengine.
	 * logic.api.Assertion)
	 */
	@Override
	public Boolean applyRules(Assertion<?> assertion) {
		List<BusinessRule<?>> rulesForSubject = this.groupedRules.get(assertion.getSubject().toUpperCase());
		return rulesForSubject.stream().allMatch(rule -> !rule.isActive() || rule.applyRule(assertion));
	}

}
