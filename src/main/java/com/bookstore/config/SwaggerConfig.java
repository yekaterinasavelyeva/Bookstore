package com.bookstore.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
					   .select()
					   .apis(RequestHandlerSelectors.basePackage("com.bookstore.api"))
					   .paths(Predicates.not(PathSelectors.regex("/error.*")))
					   .paths(Predicates.not(PathSelectors.regex("/actuator.*")))
					   .paths(PathSelectors.any())
					   .build();
	}

	@Bean
	@ConditionalOnMissingBean(ApiInfo.class)
	public ApiInfo apiInfo() {
		return ApiInfo.DEFAULT;
	}

	@Bean
	@ConditionalOnMissingBean(UiConfiguration.class)
	public UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
					   .validatorUrl(null)
					   .build();
	}
}
