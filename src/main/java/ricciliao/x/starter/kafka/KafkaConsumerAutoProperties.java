package ricciliao.x.starter.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.kafka.KafkaConsumerHandler;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.component.props.ApplicationProperties;

import java.util.List;
import java.util.Objects;

@ConfigurationProperties("ricciliao.x.kafka.consumer")
public class KafkaConsumerAutoProperties implements ApplicationProperties {

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
        private String topic;
        private String group;
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

        public String buildBeanNamePrefix() {

            return this.getGroup() + this.getTopic().substring(0, 1).toUpperCase() + this.getTopic().substring(1);
        }

    }

}
