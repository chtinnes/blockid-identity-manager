package de.cect.blockid.identitymanager.exampleapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.cect.blockid.identitymanager.exampleapp.logic.impl.rules.AdultAgeBusinessRule;
import de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api.BusinessRule;
import de.cect.blockid.identitymanager.gerneral.api.constants.ApplicationProfiles;

/**
 * Configuration for the example app.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Configuration
@Profile(ApplicationProfiles.EXAMPLEAPP)
public class ExampleAppConfiguration {

	@Bean
	public BusinessRule<Integer> ageOverEighteen() {
		return new AdultAgeBusinessRule(18);
	}

}
