package com.webservices.wsdlutilidad;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Clase encargada de la documentacion de la aplicacion
 * 
 * @author Fabian Cata�o Sanchez 
 *
 */
@EnableWebMvc
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .select()
				.apis(RequestHandlerSelectors.basePackage("com.webservices.wsdlutilidad.rest"))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}

	/**
	 * Metodo que permite definir la informacion general que aplica a todas las Apis
	 * expuestas en este proyecto
	 * 
	 * @return Objeto con la informacion de las Apis
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo("Core Rest service",
				"Microservicio que define la arquitetura de los microservicios Java con Wsdl", "1.0",
				"https://www.jfacplay.com",
				new Contact("JFacplay", "https://www.jfacplay.com", ""),
				"License", "https://www.jfacplay.com", Collections.emptyList());
	}

}
