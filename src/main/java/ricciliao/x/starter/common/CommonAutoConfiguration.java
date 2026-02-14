package ricciliao.x.starter.common;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ricciliao.x.component.payload.response.ResponseExceptionResolver;
import ricciliao.x.component.payload.response.ResponseHttpMessageConverter;
import ricciliao.x.component.payload.response.ResponseModule;
import ricciliao.x.component.utils.SpringBeanUtils;
import ricciliao.x.starter.PropsAutoConfiguration;

import java.util.List;

@Configuration
@PropsAutoConfiguration(
        properties = CommonAutoProperties.class
)
public class CommonAutoConfiguration implements WebMvcConfigurer {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CommonAutoConfiguration(@Autowired ApplicationContext applicationContext) {
        SpringBeanUtils.setApplicationContext(applicationContext);
    }

    @Bean
    public ResponseHttpMessageConverter responseHttpMessageConverter() {

        return new ResponseHttpMessageConverter(objectMapper);
    }

    /**
     * @param responseHttpMessageConverter must be an {@link BeanPostProcessor}, lazy injection.
     */
    @Bean
    public static RequestMappingHandlerAdapterPostProcess requestMappingHandlerAdapterPostProcess(ObjectProvider<ResponseHttpMessageConverter> responseHttpMessageConverter) {

        return new RequestMappingHandlerAdapterPostProcess(responseHttpMessageConverter);
    }

    @Override
    public void extendMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
        converters.addFirst(responseHttpMessageConverter());
    }

    @Override
    public void extendHandlerExceptionResolvers(@Nonnull List<HandlerExceptionResolver> resolvers) {
        resolvers.addFirst(new ResponseExceptionResolver(responseHttpMessageConverter()));
    }

    @Configuration(proxyBeanMethods = false)
    static class JacksonObjectMapperAutoConfiguration {

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer customizer(@Autowired BuildProperties props) {

            return builder -> {
                builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                builder.modulesToInstall(
                        modules -> {
                            modules.add(new JavaTimeModule());
                            modules.add(new ResponseModule(props));
                        });
            };
        }

    }

}
