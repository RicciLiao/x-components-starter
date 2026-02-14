package ricciliao.x.starter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.component.kafka.KafkaProducer;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class KafkaProducerFactoryBean<T extends KafkaMessageDto> implements FactoryBean<KafkaProducer<T>>, BeanFactoryAware {

    private final String topic;
    private BeanFactory beanFactory;

    public KafkaProducerFactoryBean(String topic) {
        this.topic = topic;
    }

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public KafkaProducer<T> getObject() {
        ProducerFactory<?, ?> producerFactory = beanFactory.getBean(ProducerFactory.class);
        ObjectMapper objectMapper = beanFactory.getBean(ObjectMapper.class);

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
