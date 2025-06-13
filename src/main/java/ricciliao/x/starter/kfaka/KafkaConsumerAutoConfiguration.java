package ricciliao.x.starter.kfaka;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import ricciliao.x.component.kafka.KafkaConsumer;
import ricciliao.x.component.kafka.KafkaHandler;
import ricciliao.x.starter.PropsAutoConfiguration;

import java.lang.reflect.InvocationTargetException;

@PropsAutoConfiguration(
        properties = KafkaConsumerAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.kafka.consumer-list[0].topic",
        after = KafkaAutoConfiguration.class
)
public class KafkaConsumerAutoConfiguration {

    public KafkaConsumerAutoConfiguration(@Autowired ConfigurableListableBeanFactory beanFactory,
                                          @Autowired ConsumerFactory<String, String> consumerFactory,
                                          @Autowired KafkaConsumerAutoProperties props) throws BeanCreationException {
        for (KafkaConsumerAutoProperties.Consumer consumer : props.getConsumerList()) {
            KafkaHandler kafkaHandler;
            try {
                kafkaHandler = consumer.getHandler().getDeclaredConstructor().newInstance();
            } catch (InstantiationException |
                     IllegalAccessException |
                     InvocationTargetException |
                     NoSuchMethodException e) {

                throw new BeanCreationException("Cannot create Kafka Consumer", e);
            }
            beanFactory.registerSingleton(
                    consumer.getBeanName(),
                    new KafkaConsumer(consumerFactory) {
                        @Override
                        public String getTopic() {
                            return consumer.getTopic();
                        }

                        @Override
                        public String getGroup() {
                            return consumer.getGroup();
                        }

                        @Override
                        public void handle(String message) {
                            kafkaHandler.handle(message);
                        }
                    }
            );
        }
    }

}
