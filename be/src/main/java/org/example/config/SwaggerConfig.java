package org.example.config;

import java.util.List;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .securitySchemes(securitySchemes())
        ;
  }

  private List<SecurityScheme> securitySchemes() {
    List<SecurityScheme> list = new ArrayList<>();
    list.add(new ApiKey("Authorization", "Authorization", "header"));
    return list;
  }

  private ApiInfo apiInfo() {
    Contact contact = new Contact("Genbesys", "localhost", "genbesys@gmail.com");
    return new ApiInfo("Genbesys",
        "Genbesys API Docs",
        "v1.0",
        "localhost",
        contact,
        "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0",
        new ArrayList<>()
    );
  }
}
