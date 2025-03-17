package ricciliao.x.starter.common;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ricciliao.x.component.utils.SpringBeanUtils;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(properties = CommonAutoProperties.class)
public class CommonAutoConfiguration {

    public CommonAutoConfiguration(@Autowired ApplicationContext applicationContext) {
        SpringBeanUtils.setApplicationContext(applicationContext);
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
