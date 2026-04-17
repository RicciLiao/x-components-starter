package ricciliao.x.starter.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.component.props.ApplicationProperties;

import java.util.List;
import java.util.Objects;

@ConfigurationProperties("ricciliao.x.kafka.producer")
public class KafkaProducerAutoProperties implements ApplicationProperties {

    /**
     * Kafka producer list.
     */
    private List<Producer> producerList;

    public List<Producer> getProducerList() {
        return producerList;
    }

    public void setProducerList(List<Producer> producerList) {
        this.producerList = producerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KafkaProducerAutoProperties that)) return false;
        return Objects.equals(getProducerList(), that.getProducerList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProducerList());
    }

    public static class Producer {
        /**
         * Kafka producer topic.
         */
        private String topic;
        /**
         * Kafka producer message POJO class, it should be implemented with {@link ricciliao.x.component.kafka.KafkaMessageDto}.
         */
        private Class<? extends KafkaMessageDto> messageClass;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Class<? extends KafkaMessageDto> getMessageClass() {
            return messageClass;
        }

        public void setMessageClass(Class<? extends KafkaMessageDto> messageClass) {
            this.messageClass = messageClass;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Producer producer)) return false;
            return Objects.equals(getTopic(), producer.getTopic()) && Objects.equals(getMessageClass(), producer.getMessageClass());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTopic(), getMessageClass());
        }

        public String buildBeanNamePrefix() {

            return this.getTopic().substring(0, 1).toUpperCase() + this.getTopic().substring(1);
        }
    }

}
