package ricciliao.x.starter.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import ricciliao.x.component.cache.consumer.ConsumerCacheProperties;
import ricciliao.x.component.cache.consumer.ConsumerCacheRestService;
import ricciliao.x.component.cache.pojo.ConsumerIdentifierDto;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = ConsumerCacheAutoProperties.class,
        conditionProperties = "ricciliao.x.cache-consumer.consumer"
)
public class ConsumerCacheAutoConfiguration extends PropsBeanDefinitionRegistryPostProcessor<ConsumerCacheAutoProperties> {

    protected ConsumerCacheAutoConfiguration() {
        super(ConsumerCacheAutoProperties.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
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

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
                if (bean instanceof RestTemplate restTemplate) {
                    System.out.println("I Am RestTemplate......");
                }

                return bean;
            }
        });
    }

}
