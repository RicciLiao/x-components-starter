package ricciliao.x.starter.cache;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.client.RestTemplate;
import ricciliao.x.cache.ConsumerStore;
import ricciliao.x.cache.pojo.ConsumerIdentifier;

public class ConsumerCacheRestServiceFactoryBean<T extends ConsumerStore> implements FactoryBean<ConsumerCacheRestService<T>>, ApplicationContextAware {

    private final String consumer;
    private final ConsumerCacheProperties.OperationProperties props;

    private ApplicationContext applicationContext;

    public ConsumerCacheRestServiceFactoryBean(@Nonnull String consumer,
                                               @Nonnull ConsumerCacheProperties.OperationProperties props) {
        this.consumer = consumer;
        this.props = props;
    }

    @Override
    public ConsumerCacheRestService<T> getObject() {
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);

        return new ConsumerCacheRestService<>(
                props,
                new ConsumerIdentifier(consumer, props.getStore()),
                restTemplate,
                (Class<T>) props.getStoreClassName()
        );
    }

    @Override
    public Class<?> getObjectType() {

        return ConsumerCacheRestService.class;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
