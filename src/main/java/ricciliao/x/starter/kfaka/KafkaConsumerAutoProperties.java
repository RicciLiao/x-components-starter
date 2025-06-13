package ricciliao.x.starter.kfaka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.kafka.KafkaHandler;
import ricciliao.x.component.props.ApplicationProperties;

import java.util.List;
import java.util.Objects;

@ConfigurationProperties("ricciliao.x.kafka")
public class KafkaConsumerAutoProperties extends ApplicationProperties {

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
        private Class<? extends KafkaHandler> handler;
        private String beanName;

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

        public Class<? extends KafkaHandler> getHandler() {
            return handler;
        }

        public void setHandler(Class<? extends KafkaHandler> handler) {
            this.handler = handler;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Consumer consumer)) return false;
            return Objects.equals(getTopic(), consumer.getTopic()) && Objects.equals(getGroup(), consumer.getGroup()) && Objects.equals(getHandler(), consumer.getHandler()) && Objects.equals(getBeanName(), consumer.getBeanName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTopic(), getGroup(), getHandler(), getBeanName());
        }
    }

}
