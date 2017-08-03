package de.cect.blockid.identitymanager.gerneral.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring configuration for a REST Client.
 * 
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
// more information on
// https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("de.cect.blockid.identitymanager"))
				.paths(PathSelectors.regex("/blockid/v1/.*")).build();
	}
}
