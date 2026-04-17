package ricciliao.x.starter.kafka;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import ricciliao.x.component.beans.PropsImportBeanDefinitionRegistrar;
import ricciliao.x.component.kafka.KafkaProducer;

public class KafkaProducerDefinitionRegistrar extends PropsImportBeanDefinitionRegistrar<KafkaProducerAutoProperties> {

    public KafkaProducerDefinitionRegistrar() {
        super(KafkaProducerAutoProperties.class);
    }

    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        for (KafkaProducerAutoProperties.Producer provider : this.getProps().getProducerList()) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(KafkaProducerFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(provider.getTopic());
            registry.registerBeanDefinition(
                    provider.buildBeanNamePrefix() + KafkaProducer.class.getSimpleName(),
                    beanDefinition
            );
        }
    }

}
