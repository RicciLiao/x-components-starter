package ricciliao.x.starter.kfaka;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = KafkaProducerAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.kafka.b.provider-list[0].topic",
        after = KafkaAutoConfiguration.class
)
public class KafkaProducerAutoConfiguration extends PropsBeanDefinitionRegistryPostProcessor<KafkaProducerAutoProperties> {


    protected KafkaProducerAutoConfiguration() {
        super(KafkaProducerAutoProperties.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@Nonnull BeanDefinitionRegistry registry) throws BeansException {
        for (KafkaProducerAutoProperties.Provider provider : this.getProps().getProviderList()) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(KafkaProducerFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(provider.getTopic());
            registry.registerBeanDefinition(provider.getBeanName(), beanDefinition);
        }
    }

}
