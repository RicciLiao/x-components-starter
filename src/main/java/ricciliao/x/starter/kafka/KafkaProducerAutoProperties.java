package ricciliao.x.starter.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.component.props.ApplicationProperties;

import java.util.List;
import java.util.Objects;

@ConfigurationProperties("ricciliao.x.kafka.producer")
public class KafkaProducerAutoProperties extends ApplicationProperties {

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
        private String topic;
        private Class<KafkaMessageDto> messageClass;
        private String beanName;

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Class<KafkaMessageDto> getMessageClass() {
            return messageClass;
        }

        public void setMessageClass(Class<KafkaMessageDto> messageClass) {
            this.messageClass = messageClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Producer provider)) return false;
            return Objects.equals(getTopic(), provider.getTopic()) && Objects.equals(getMessageClass(), provider.getMessageClass()) && Objects.equals(getBeanName(), provider.getBeanName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTopic(), getMessageClass(), getBeanName());
        }
    }

}
