package com.box20six.docker.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("business-api")
                .select()
                .paths(PathSelectors.any()) // and by paths
                .paths(paths())
                .build()
                .apiInfo(apiInfo());
    }

    public Predicate<String> paths() {
        return or(regex("/api.*"));
    }

    public ApiInfo apiInfo() {
        return new ApiInfo("Docker Example", "Docker Example", "0.1.0", "", "kenyatta.clark@gmail.com", "", "");
    }
}
