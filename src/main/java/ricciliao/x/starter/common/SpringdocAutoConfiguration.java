package ricciliao.x.starter.common;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = CommonAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.common.springdoc.enable",
        after = CommonAutoConfiguration.class
)
public class SpringdocAutoConfiguration {

    private final CommonAutoProperties commonProps;

    public SpringdocAutoConfiguration(@Autowired CommonAutoProperties commonProps) {
        this.commonProps = commonProps;
    }


    @Bean
    public OpenAPI api() {
        Info info =
                new Info()
                        .title(commonProps.getSpringdoc().getTitle())
                        .description("A description")
                        .contact(new Contact().name("A contact"))
                        .license(new License().name("A License"))
                        .summary("A summary")
                        .version(commonProps.getVersion());
        Components components =
                new Components()
                        .addSecuritySchemes(
                                HttpHeaders.AUTHORIZATION,
                                new SecurityScheme()
                                        .name(HttpHeaders.AUTHORIZATION)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                        );

        return
                new OpenAPI()
                        .info(info)
                        .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                        .components(components);
    }

    @Bean
    public OpenApiCustomizer globalHeaderOpenApiCustomizer() {
        Parameter globalHeader1 =
                new HeaderParameter()
                        .name("Your-Header-Name-1")
                        .description("Description of your header 1")
                        .required(true);
        Parameter globalHeader2 =
                new HeaderParameter()
                        .name("Your-Header-Name-2")
                        .description("Description of your header 2")
                        .required(true);

        return
                openApi ->
                        openApi.getPaths()
                                .values()
                                .forEach(pathItem ->
                                        {
                                            for (Operation operation : pathItem.readOperations()) {
                                                operation
                                                        .addParametersItem(globalHeader1)
                                                        .addParametersItem(globalHeader2);
                                            }
                                        }
                                );
    }

}
