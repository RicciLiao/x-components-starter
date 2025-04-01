package ricciliao.x.starter.cache;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.web.client.RestTemplate;
import ricciliao.x.cache.ConsumerCacheProperties;
import ricciliao.x.cache.ConsumerCacheRestService;
import ricciliao.x.cache.pojo.ConsumerIdentifierDto;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = ConsumerCacheAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.cache-consumer.consumer"
)
public class ConsumerCacheAutoConfiguration extends PropsBeanDefinitionRegistryPostProcessor<ConsumerCacheAutoProperties> {

    protected ConsumerCacheAutoConfiguration() {
        super(ConsumerCacheAutoProperties.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@Nonnull BeanDefinitionRegistry registry) throws BeansException {
        RestTemplate restTemplate = new RestTemplate();
        for (ConsumerCacheProperties.OperationProperties operation : this.getProps().getOperationList()) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(ConsumerCacheRestService.class);
            ConstructorArgumentValues values = new ConstructorArgumentValues();
            values.addIndexedArgumentValue(0, operation);
            values.addIndexedArgumentValue(1, new ConsumerIdentifierDto(this.getProps().getConsumer(), operation.getStore()));
            values.addIndexedArgumentValue(2, restTemplate);
            values.addIndexedArgumentValue(3, operation.getStoreClassName());
            beanDefinition.setConstructorArgumentValues(values);
            registry.registerBeanDefinition(operation.getStore() + ConsumerCacheRestService.class.getSimpleName(), beanDefinition);
        }
    }

}
