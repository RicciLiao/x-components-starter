package ricciliao.x.starter.mcp;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.client.RestClient;
import ricciliao.x.component.beans.PropsImportBeanDefinitionRegistrar;

import javax.annotation.Nonnull;

public class ConsumerCacheDefinitionRegistrar extends PropsImportBeanDefinitionRegistrar<ConsumerCacheAutoProperties> {

    protected ConsumerCacheDefinitionRegistrar() {
        super(ConsumerCacheAutoProperties.class);
    }

    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        RestClient.Builder builder = RestClient.builder().baseUrl(this.getProps().getUrl());
        for (ConsumerCacheProperties.OperationProperties operation : this.getProps().getOperationList()) {
            String beanName = operation.getStore() + ConsumerCacheRestService.class.getSimpleName();
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(ConsumerCacheRestServiceFactoryBean.class);
            ConstructorArgumentValues values = new ConstructorArgumentValues();
            values.addIndexedArgumentValue(0, operation);
            values.addIndexedArgumentValue(1, builder);
            beanDefinition.setConstructorArgumentValues(values);
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
    }
}
