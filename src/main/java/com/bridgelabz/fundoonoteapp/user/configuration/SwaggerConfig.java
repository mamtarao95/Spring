package com.bridgelabz.fundoonoteapp.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
         // .paths(PathSelectors.regex("/.*"))                          
          .build();                                           
    }
	
	/* @Bean
	    SecurityConfiguration security() {
	    return new SecurityConfiguration(null, null, null, null, "token", ApiKeyVehicle.HEADER, "token", ",");
		 
	 }*/
	
	/*@Bean
	SecurityConfiguration security() {
	    return new SecurityConfiguration(
	            null,
	            null,
	            null, // realm Needed for authenticate button to work
	            null, // appName Needed for authenticate button to work
	            "BEARER"// apiKeyValue
	            ,ApiKeyVehicle.HEADER,
	           " AUTHORIZATION", //apiKeyName
	            null);
	}
	
	private ApiKey apiKey() {
	    return new ApiKey("apikey", "Authorization", "header");
	}
	*/
	   /* private SecurityContext securityContext() {
	        return SecurityContext.builder()
	              
	                .forPaths(PathSelectors.regex("/anyPath.*"))
	                .build();
	    }
*/
	   /* List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope
	                = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        return Lists.newArrayList(
	                new SecurityReference("mykey", authorizationScopes));
	    }*/

	    /*@Bean
	    SecurityConfiguration security() {
	        return new SecurityConfiguration(
	                null, null, null,
	                "project-name-v2",
	                "123",
	                ApiKeyVehicle.QUERY_PARAM,
	                "key",
	                ","scope separator);
	    }*/
	}
