package ricciliao.x.starter.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;
import ricciliao.x.cache.ConsumerStore;
import ricciliao.x.cache.XCacheConstants;
import ricciliao.x.cache.pojo.StoreIdentifier;
import ricciliao.x.component.payload.response.ResponseHttpMessageConverter;
import ricciliao.x.starter.common.CommonAutoProperties;

public class ConsumerCacheRestServiceFactoryBean<T extends ConsumerStore> implements FactoryBean<ConsumerCacheRestService<T>>, ApplicationContextAware {

    private final ConsumerCacheProperties.OperationProperties props;
    private final  RestClient.Builder builder;

    private ApplicationContext applicationContext;

    public ConsumerCacheRestServiceFactoryBean(@Nonnull ConsumerCacheProperties.OperationProperties props,
                                               @Nonnull RestClient.Builder builder) {
        this.props = props;
        this.builder = builder.clone();
    }

    @Override
    public ConsumerCacheRestService<T> getObject() {
        CommonAutoProperties commonProps = applicationContext.getBean(CommonAutoProperties.class);
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);

        builder.defaultHeader(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, props.getStore())
                .defaultHeader(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, commonProps.getConsumer())
                .messageConverters(httpMessageConverters -> {
                    httpMessageConverters.clear();
                    httpMessageConverters.add(new ResponseHttpMessageConverter(objectMapper));
                    httpMessageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
                })
        ;

        return new ConsumerCacheRestService<>(
                props,
                new StoreIdentifier(commonProps.getConsumer(), props.getStore()),
                (Class<T>) props.getStoreClassName(),
                builder.build()
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
