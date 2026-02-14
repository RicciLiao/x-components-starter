package ricciliao.x.starter.kafka;

import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Import;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = KafkaConsumerAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.kafka.consumer.consumer-list[0].topic",
        imports = {KafkaConsumerDefinitionRegistrar.class},
        after = KafkaAutoConfiguration.class
)
@Import({KafkaConsumerDefinitionRegistrar.class})
public class KafkaConsumerAutoConfiguration {

}
