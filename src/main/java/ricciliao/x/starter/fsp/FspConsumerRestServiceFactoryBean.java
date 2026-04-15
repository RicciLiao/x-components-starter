package ricciliao.x.starter.fsp;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.client.RestClient;
import ricciliao.x.component.CoreConstants;
import ricciliao.x.component.payload.response.ResponseHttpMessageConverter;
import ricciliao.x.starter.common.CommonAutoProperties;

public class FspConsumerRestServiceFactoryBean implements FactoryBean<FspConsumerRestService>, ApplicationContextAware {

    private final FspConsumerProperties.OperationProperties props;
    private final RestClient.Builder builder;

    private ApplicationContext applicationContext;

    public FspConsumerRestServiceFactoryBean(@Nonnull FspConsumerProperties.OperationProperties props,
                                             @Nonnull RestClient.Builder builder) {
        this.props = props;
        this.builder = builder.clone();
    }

    @Override
    public FspConsumerRestService getObject() {
        CommonAutoProperties commonProps = applicationContext.getBean(CommonAutoProperties.class);
        ResponseHttpMessageConverter responseHttpMessageConverter = applicationContext.getBean(ResponseHttpMessageConverter.class);
        builder.defaultHeader(CoreConstants.CONSUMER_ID, commonProps.getConsumer())
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.addFirst(responseHttpMessageConverter));

        return new FspConsumerRestService(props, builder.build());
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {

        return FspConsumerRestService.class;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
