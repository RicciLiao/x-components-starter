package ricciliao.x.starter.common;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nonnull;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import ricciliao.x.component.context.TypedLifecycleBeanPostProcessor;
import ricciliao.x.component.response.ResponseExceptionResolver;
import ricciliao.x.component.response.ResponseHttpMessageConverter;
import ricciliao.x.component.response.ResponseModule;
import ricciliao.x.component.response.ResponseValueHandler;
import ricciliao.x.component.utils.SpringBeanUtils;
import ricciliao.x.starter.PropsAutoConfiguration;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@PropsAutoConfiguration(
        properties = CommonAutoProperties.class
)
public class CommonAutoConfiguration implements Serializable {
    @Serial
    private static final long serialVersionUID = -5872892465550813182L;

    public CommonAutoConfiguration(@Autowired ApplicationContext applicationContext,
                                   @Autowired CommonAutoProperties commonProps,
                                   @Autowired BuildProperties buildProperties) {
        SpringBeanUtils.setApplicationContext(applicationContext);
        commonProps.setVersion(buildProperties.getVersion());
        commonProps.setConsumer(buildProperties.getName());
        commonProps.setArtifact(buildProperties.getArtifact());
        commonProps.setGroup(buildProperties.getGroup());
    }

    @Configuration(proxyBeanMethods = false)
    static class JacksonObjectMapperConfiguration {
        @Bean
        public Jackson2ObjectMapperBuilderCustomizer customizer(@Autowired CommonAutoProperties prps) {

            return builder -> {
                builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                builder.timeZone(TimeZone.getDefault());
                builder.modulesToInstall(
                        modules -> {
                            modules.add(new JavaTimeModule());
                            modules.add(new ResponseModule(prps));
                        });
            };
        }
    }

    @ConditionalOnClass(WebMvcConfigurer.class)
    @Configuration
    static class CommonWebMvcConfiguration implements WebMvcConfigurer {

        private ObjectMapper objectMapper;

        @Autowired
        public void setObjectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Bean
        public ResponseHttpMessageConverter responseHttpMessageConverter() {

            return new ResponseHttpMessageConverter(objectMapper);
        }

        @Override
        public void extendMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
            WebMvcConfigurer.super.extendMessageConverters(converters);
            converters.addFirst(responseHttpMessageConverter());
        }

        @Override
        public void extendHandlerExceptionResolvers(@Nonnull List<HandlerExceptionResolver> resolvers) {
            WebMvcConfigurer.super.extendHandlerExceptionResolvers(resolvers);
            resolvers.addFirst(new ResponseExceptionResolver(responseHttpMessageConverter()));
        }

        @Bean
        public TypedLifecycleBeanPostProcessor<RequestMappingHandlerAdapter> adapterPostProcessor() {

            return TypedLifecycleBeanPostProcessor.processor(new TypedLifecycleBeanPostProcessor.LifecycleProcessor<>() {

                @Override
                public boolean supports() {

                    return true;
                }

                @Override
                public Class<RequestMappingHandlerAdapter> getBeanType() {

                    return RequestMappingHandlerAdapter.class;
                }

                @Override
                public RequestMappingHandlerAdapter afterInitialization(@Nonnull RequestMappingHandlerAdapter bean, @Nonnull String beanName) {
                    List<HandlerMethodReturnValueHandler> originalHandlers = bean.getReturnValueHandlers();
                    List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(originalHandlers)) {
                        newHandlers.addAll(originalHandlers);
                    }
                    newHandlers.addFirst(new ResponseValueHandler(responseHttpMessageConverter()));
                    bean.setReturnValueHandlers(newHandlers);

                    return bean;
                }
            });
        }

    }

}
