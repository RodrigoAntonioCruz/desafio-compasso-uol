package com.compasso.uol.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Rodrigo da Cruz
 * @version 1.0
 * @since 2021-02-26
 * 
 */

@Configuration
@EnableSwagger2
@PropertySource("classpath:config-swagger.properties")
public class SwaggerConfig implements WebMvcConfigurer {

	@Value("${config.info.nome}")
	private String nome;
	@Value("${config.info.email}")
	private String email;
	@Value("${config.info.site}")
	private String site;
	@Value("${config.info.titulo}")
	private String titulo;
	@Value("${config.info.descricao}")
	private String descricao;
	@Value("${config.info.licenca}")
	private String licenca;
	@Value("${config.info.licencaUrl}")
	private String licencaUrl;
	@Value("${config.info.termoServicoUrl}")
	private String termoServicoUrl;
	@Value("${config.info.versao}")
	private String versao;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.compasso.uol")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title(titulo).description(descricao).license(licenca)
				.licenseUrl(licencaUrl).termsOfServiceUrl(termoServicoUrl).version(versao).build();
		return apiInfo;
	}
}
