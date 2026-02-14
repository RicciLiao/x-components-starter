package ricciliao.x.starter.kafka;

import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = KafkaProducerAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.kafka.producer.producer-list[0].topic",
        after = KafkaAutoConfiguration.class,
        imports = {KafkaProducerDefinitionRegistrar.class}
)
public class KafkaProducerAutoConfiguration {

}
