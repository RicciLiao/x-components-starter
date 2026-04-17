package ricciliao.x.starter.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.kafka.KafkaConsumerHandler;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.component.props.ApplicationProperties;

import java.util.List;
import java.util.Objects;

@ConfigurationProperties("ricciliao.x.kafka.consumer")
public class KafkaConsumerAutoProperties implements ApplicationProperties {

    /**
    * Kafka consumer list.
    */
    private List<Consumer> consumerList;

    public List<Consumer> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<Consumer> consumerList) {
        this.consumerList = consumerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KafkaConsumerAutoProperties that)) return false;
        return Objects.equals(getConsumerList(), that.getConsumerList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConsumerList());
    }

    public static class Consumer {
        /**
        * Kafka consumer topic.
        */
        private String topic;
        /**
         * Kafka consumer group.
         */
        private String group;
        /**
        * Kafka consumer message handler class, it should be implemented with {@link ricciliao.x.component.kafka.KafkaConsumerHandler}.
        */
        private Class<? extends KafkaConsumerHandler<? extends KafkaMessageDto>> handler;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public Class<? extends KafkaConsumerHandler<? extends KafkaMessageDto>> getHandler() {//NOSONAR
            return handler;
        }

        public void setHandler(Class<? extends KafkaConsumerHandler<? extends KafkaMessageDto>> handler) {
            this.handler = handler;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Consumer consumer)) return false;
            return Objects.equals(getTopic(), consumer.getTopic()) && Objects.equals(getGroup(), consumer.getGroup()) && Objects.equals(getHandler(), consumer.getHandler());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTopic(), getGroup(), getHandler());
        }

        public String buildBeanNamePrefix() {

            return this.getGroup() + this.getTopic().substring(0, 1).toUpperCase() + this.getTopic().substring(1);
        }

    }

}
