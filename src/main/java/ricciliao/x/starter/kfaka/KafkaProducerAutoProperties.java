package ricciliao.x.starter.kfaka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.kafka.KafkaMessageDto;
import ricciliao.x.component.props.ApplicationProperties;

import java.util.List;
import java.util.Objects;

@ConfigurationProperties("ricciliao.x.kafka.b")
public class KafkaProducerAutoProperties extends ApplicationProperties {

    private List<Provider> providerList;

    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KafkaProducerAutoProperties that)) return false;
        return Objects.equals(getProviderList(), that.getProviderList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProviderList());
    }

    public static class Provider {
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
            if (!(o instanceof Provider provider)) return false;
            return Objects.equals(getTopic(), provider.getTopic()) && Objects.equals(getMessageClass(), provider.getMessageClass()) && Objects.equals(getBeanName(), provider.getBeanName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTopic(), getMessageClass(), getBeanName());
        }
    }

}
