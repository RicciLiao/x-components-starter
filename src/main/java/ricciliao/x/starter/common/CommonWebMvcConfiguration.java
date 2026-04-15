package ricciliao.x.starter.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ricciliao.x.component.CoreConstants;
import ricciliao.x.component.payload.response.ResponseExceptionResolver;
import ricciliao.x.component.payload.response.ResponseHttpMessageConverter;

import java.util.List;

@ConditionalOnClass(WebMvcConfigurer.class)
@Configuration
public class CommonWebMvcConfiguration implements WebMvcConfigurer {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public ResponseHttpMessageConverter responseHttpMessageConverter() {

        return new ResponseHttpMessageConverter(objectMapper);
    }

    @Bean
    public RestClient consumerRestClient(@Autowired CommonAutoProperties props) {

        return RestClient
                .builder()
                .defaultHeader(CoreConstants.CONSUMER_ID, props.getConsumer())
                .build();
    }

    @Override
    public void extendMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
        converters.removeIf(ResponseHttpMessageConverter.class::isInstance);
    }

    @Override
    public void extendHandlerExceptionResolvers(@Nonnull List<HandlerExceptionResolver> resolvers) {
        resolvers.addFirst(new ResponseExceptionResolver(responseHttpMessageConverter()));
    }

    /**
     * @param responseHttpMessageConverter must be an {@link org.springframework.beans.factory.config.BeanPostProcessor}, lazy injection.
     */
    @Bean
    public static RequestMappingHandlerAdapterPostProcess requestMappingHandlerAdapterPostProcess(ObjectProvider<ResponseHttpMessageConverter> responseHttpMessageConverter) {

        return new RequestMappingHandlerAdapterPostProcess(responseHttpMessageConverter);
    }

}
