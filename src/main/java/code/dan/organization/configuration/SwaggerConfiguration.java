package code.dan.organization.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private static final String BASE_PACKAGE = "code.dan.organization.controller";

    public Info getInfo(){
        return new Info()
                .title("Organization APIs")
                .description("Sandbox for Organization APIs")
                .version("v0.0.1")
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org"))
                .contact(new Contact()
                        .name("Adhe Wahyu Ardanto")
                        .url("")
                        .email("adhe.wahyu.ardanto@gmail.com"));
    }

    @Bean
    public OpenAPI baseOpenApi(){
        return new OpenAPI()
                .info(getInfo());
    }

    public OpenApiCustomiser getBaseCustomizer(){
        return openApi -> baseOpenApi();
    }

    @Bean
    public GroupedOpenApi allApis(){
        return GroupedOpenApi.builder()
                .addOpenApiCustomiser(getBaseCustomizer())
                .group("All APIs")
                .packagesToScan(BASE_PACKAGE)
                .build();
    }

}
