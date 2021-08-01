package com.fenix.signupservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fenix.signupservice.controller"))
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Sign Up Service",
                "Sign Up Service for Fenix Coding Test",
                "1.0",
                "Terms of service",
                new Contact("Ali Majeed", "https://www.dummycontact.com/", "john@springfrmework.guru"),
                "Dummy License",
                "https://www.dummylicense.org/");
        return apiInfo;
    }

}
