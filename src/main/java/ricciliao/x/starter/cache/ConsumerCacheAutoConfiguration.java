package ricciliao.x.starter.cache;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = ConsumerCacheAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.cache-consumer.consumer",
        after = ConsumerCacheAutoProperties.class
)
public class ConsumerCacheAutoConfiguration extends PropsBeanDefinitionRegistryPostProcessor<ConsumerCacheAutoProperties> {

    protected ConsumerCacheAutoConfiguration() {
        super(ConsumerCacheAutoProperties.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@Nonnull BeanDefinitionRegistry registry) throws BeansException {
        for (ConsumerCacheProperties.OperationProperties operation : this.getProps().getOperationList()) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(ConsumerCacheRestServiceFactoryBean.class);
            ConstructorArgumentValues values = new ConstructorArgumentValues();
            values.addIndexedArgumentValue(0, this.getProps().getConsumer());
            values.addIndexedArgumentValue(1, operation);
            beanDefinition.setConstructorArgumentValues(values);
            registry.registerBeanDefinition(operation.getStore() + ConsumerCacheRestService.class.getSimpleName(), beanDefinition);
        }
    }

}
