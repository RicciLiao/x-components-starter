package ricciliao.x.starter.common;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ricciliao.x.component.utils.SpringBeanUtils;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = CommonAutoProperties.class,
        before = JacksonAutoConfiguration.class
)
public class CommonAutoConfiguration {

    public CommonAutoConfiguration(@Autowired ApplicationContext applicationContext,
                                   @Autowired CommonAutoProperties props,
                                   @Value("${spring.application.version}") String version,
                                   @Value("${spring.application.name}") String name) {
        SpringBeanUtils.setApplicationContext(applicationContext);
        props.setVersion(version);
        props.setConsumer(name);
    }

    @Bean
    public ObjectMapper objectMapper(@Autowired CommonAutoProperties props) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setTimeZone(props.getTimeZone());
        // objectMapper java.time.LocalDate/LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

}
