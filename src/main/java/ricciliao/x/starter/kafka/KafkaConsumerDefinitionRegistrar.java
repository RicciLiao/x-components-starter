package ricciliao.x.starter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.ResolvableType;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ricciliao.x.component.beans.PropsImportBeanDefinitionRegistrar;
import ricciliao.x.component.kafka.KafkaConsumer;
import ricciliao.x.component.kafka.KafkaConsumerHandler;
import ricciliao.x.component.kafka.KafkaMessageDto;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class KafkaConsumerDefinitionRegistrar extends PropsImportBeanDefinitionRegistrar<KafkaConsumerAutoProperties> implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public KafkaConsumerDefinitionRegistrar() {
        super(KafkaConsumerAutoProperties.class);
    }


    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        for (KafkaConsumerAutoProperties.Consumer consumer : this.getProps().getConsumerList()) {
            ResolvableType handlerType = ResolvableType.forClass(consumer.getHandler()).as(KafkaConsumerHandler.class);
            RootBeanDefinition handlerBeanDefinition = new RootBeanDefinition(consumer.getHandler());
            handlerBeanDefinition.setTargetType(handlerType);
            registry.registerBeanDefinition(consumer.getHandler().getSimpleName(), handlerBeanDefinition);

            ResolvableType consumerType = ResolvableType.forClassWithGenerics(KafkaConsumer.class, handlerType.getGeneric(0));
            RootBeanDefinition consumerBeanDefinition = new RootBeanDefinition(KafkaConsumer.class);
            consumerBeanDefinition.setTargetType(consumerType);
            consumerBeanDefinition.setInstanceSupplier(this.instanceSupplier(consumer));
            consumerBeanDefinition.setDependsOn(consumer.getHandler().getSimpleName());
            registry.registerBeanDefinition(
                    consumer.buildBeanNamePrefix() + KafkaConsumer.class.getSimpleName(),
                    consumerBeanDefinition
            );
        }
    }

    private <T extends KafkaMessageDto> Supplier<KafkaConsumer<T>> instanceSupplier(KafkaConsumerAutoProperties.Consumer consumer) {

        return () -> {
            ObjectMapper objectMapper = beanFactory.getBean("jacksonObjectMapper", ObjectMapper.class);
            DefaultKafkaConsumerFactory<?, ?> defaultKafkaConsumerFactory = beanFactory.getBean("kafkaConsumerFactory", DefaultKafkaConsumerFactory.class);
            KafkaConsumerHandler<?> handler = beanFactory.getBean(consumer.getHandler().getSimpleName(), KafkaConsumerHandler.class);

            Map<String, Object> configProps = new HashMap<>(defaultKafkaConsumerFactory.getConfigurationProperties());
            configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
            configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, this.getMessageClass(consumer.getHandler()));
            ConsumerFactory<String, T> cfy =
                    new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(objectMapper));

            return new KafkaConsumer<>(cfy) {

                @Override
                public String getTopic() {

                    return consumer.getTopic();
                }

                @Override
                public String getGroup() {

                    return consumer.getGroup();
                }

                @Override
                public KafkaConsumerHandler<T> getHandler() {

                    return (KafkaConsumerHandler<T>) handler;
                }
            };
        };
    }

    private Class<? extends KafkaMessageDto> getMessageClass(Class<? extends KafkaConsumerHandler<? extends KafkaMessageDto>> handlerClass) {
        Type[] types = handlerClass.getGenericInterfaces();
        if (types[0] instanceof ParameterizedType pt
                && pt.getRawType() instanceof Class<?> clazz
                && KafkaConsumerHandler.class.isAssignableFrom(clazz)
                && pt.getActualTypeArguments()[0] instanceof Class<?> mClazz
                && KafkaMessageDto.class.isAssignableFrom(mClazz)) {

            return mClazz.asSubclass(KafkaMessageDto.class);
        }

        throw new BeanCreationException(
                String.format("Cannot resolve %s parameterized type!", handlerClass)
        );
    }


}
