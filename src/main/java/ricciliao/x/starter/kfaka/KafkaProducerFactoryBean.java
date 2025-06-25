package ricciliao.x.starter.kfaka;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.component.kafka.KafkaProducer;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerFactoryBean<T extends KafkaMessageDto> implements FactoryBean<KafkaProducer<T>>, ApplicationContextAware {

    private final String topic;
    private ApplicationContext applicationContext;

    public KafkaProducerFactoryBean(String topic) {
        this.topic = topic;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public KafkaProducer<T> getObject() {
        ProducerFactory<?, ?> producerFactory = applicationContext.getBean(ProducerFactory.class);
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);

        Map<String, Object> configProps = new HashMap<>(producerFactory.getConfigurationProperties());
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        ProducerFactory<String, T> pfy =
                new DefaultKafkaProducerFactory<>(
                        configProps,
                        new StringSerializer(),
                        new JsonSerializer<>(objectMapper)
                );

        return new KafkaProducer<>(pfy) {
            @Override
            public String getTopic() {

                return topic;
            }
        };
    }

    @Override
    public Class<?> getObjectType() {

        return KafkaProducer.class;
    }


}
