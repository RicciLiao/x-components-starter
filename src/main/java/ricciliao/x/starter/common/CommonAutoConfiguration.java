package ricciliao.x.starter.common;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ricciliao.x.component.payload.response.ResponseModule;
import ricciliao.x.component.utils.SpringBeanUtils;
import ricciliao.x.starter.PropsAutoConfiguration;

@Configuration
@PropsAutoConfiguration(
        properties = CommonAutoProperties.class
)
public class CommonAutoConfiguration {

    public CommonAutoConfiguration(@Autowired ApplicationContext applicationContext) {
        SpringBeanUtils.setApplicationContext(applicationContext);
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
