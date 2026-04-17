package ricciliao.x.starter.fsp;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.client.RestClient;
import ricciliao.x.component.beans.PropsImportBeanDefinitionRegistrar;

public class FspConsumerDefinitionRegistrar extends PropsImportBeanDefinitionRegistrar<FspConsumerAutoProperties> {

    protected FspConsumerDefinitionRegistrar() {
        super(FspConsumerAutoProperties.class);
    }

    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        RestClient.Builder builder = RestClient.builder().baseUrl(this.getProps().getUrl());
        String className = FspConsumerRestService.class.getSimpleName();
        String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(FspConsumerRestServiceFactoryBean.class);
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        ConstructorArgumentValues values = new ConstructorArgumentValues();
        values.addIndexedArgumentValue(0, this.getProps().getOperation());
        values.addIndexedArgumentValue(1, builder);
        beanDefinition.setConstructorArgumentValues(values);
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
