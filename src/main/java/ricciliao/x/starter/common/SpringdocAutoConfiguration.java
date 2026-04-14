package ricciliao.x.starter.common;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Encoding;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.collections4.CollectionUtils;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import ricciliao.x.starter.PropsAutoConfiguration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@PropsAutoConfiguration(
        properties = CommonAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.common.springdoc.enable",
        after = CommonAutoConfiguration.class
)
public class SpringdocAutoConfiguration {

    private final CommonAutoProperties commonProps;
    private final BuildProperties buildProps;

    public SpringdocAutoConfiguration(@Autowired CommonAutoProperties commonProps,
                                      @Autowired BuildProperties buildProps) {
        this.commonProps = commonProps;
        this.buildProps = buildProps;
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
                        .version(buildProps.getVersion());
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

    @Bean
    public OperationCustomizer requestPart2JsonCustomizer() {

        return (operation, handlerMethod) -> {
            RequestBody requestBody = operation.getRequestBody();
            if (Objects.isNull(requestBody)
                || Objects.isNull(requestBody.getContent())
                || Objects.isNull(requestBody.getContent().get(MediaType.MULTIPART_FORM_DATA_VALUE))
                || Objects.nonNull(requestBody.getContent().get(MediaType.MULTIPART_FORM_DATA_VALUE).getEncoding())) {

                return operation;
            }

            List<RequestPart> requestPartList =
                    Arrays.stream(handlerMethod.getMethod().getParameters())
                            .filter(parameter -> parameter.isAnnotationPresent(RequestPart.class)
                                                 && Serializable.class.isAssignableFrom(parameter.getType()))
                            .map(parameter -> parameter.getAnnotation(RequestPart.class))
                            .toList();
            if (CollectionUtils.isEmpty(requestPartList)) {

                return operation;
            }

            Map<String, Encoding> encodingMap = new LinkedHashMap<>();
            requestPartList.forEach(requestPart -> encodingMap.put(requestPart.name(), new Encoding().contentType(MediaType.APPLICATION_JSON_VALUE)));
            requestBody.getContent().get(MediaType.MULTIPART_FORM_DATA_VALUE).encoding(encodingMap);

            return operation;
        };
    }

}
