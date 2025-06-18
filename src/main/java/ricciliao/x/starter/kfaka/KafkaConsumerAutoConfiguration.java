package ricciliao.x.starter.kfaka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ricciliao.x.component.kafka.KafkaConsumer;
import ricciliao.x.component.kafka.KafkaHandler;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.starter.PropsAutoConfiguration;

import java.util.HashMap;
import java.util.Map;

@PropsAutoConfiguration(
        properties = KafkaConsumerAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.kafka.a.consumer-list[0].topic",
        after = KafkaAutoConfiguration.class
)
public class KafkaConsumerAutoConfiguration {

    public KafkaConsumerAutoConfiguration(@Autowired ConfigurableListableBeanFactory beanFactory,
                                          @Autowired ConsumerFactory<String, String> consumerFactory,
                                          @Autowired ObjectMapper objectMapper,
                                          @Autowired KafkaConsumerAutoProperties props) throws BeanCreationException {
        for (KafkaConsumerAutoProperties.Consumer consumer : props.getConsumerList()) {
            Map<String, Object> configProps = new HashMap<>(consumerFactory.getConfigurationProperties());
            configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
            configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, consumer.getMessageClass());
            ConsumerFactory<String, KafkaMessageDto> cfy =
                    new DefaultKafkaConsumerFactory<>(
                            configProps,
                            new StringDeserializer(),
                            new JsonDeserializer<>(objectMapper)
                    );
            KafkaHandler<KafkaMessageDto> kafkaHandler = beanFactory.createBean(consumer.getHandler());  //inorder to enable dependency injection
            beanFactory.registerSingleton(
                    consumer.getBeanName(),
                    new KafkaConsumer<>(cfy, consumer.getMessageClass()) {
                        @Override
                        public String getTopic() {
                            return consumer.getTopic();
                        }

                        @Override
                        public String getGroup() {
                            return consumer.getGroup();
                        }

                        @Override
                        public void handle(KafkaMessageDto message) {
                            kafkaHandler.handle(message);
                        }
                    }
            );
        }
    }

}
