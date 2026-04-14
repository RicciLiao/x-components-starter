package ricciliao.x.starter.log;


import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ricciliao.x.component.beans.PropsImportBeanDefinitionRegistrar;
import ricciliao.x.log.MdcSupportTaskDecorator;

public class AuditLogDefinitionRegistrar extends PropsImportBeanDefinitionRegistrar<AuditLogAutoProperties> {

    protected AuditLogDefinitionRegistrar() {
        super(AuditLogAutoProperties.class);
    }

    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(
                "mdcSupportExecutor",
                BeanDefinitionBuilder.genericBeanDefinition(
                        ThreadPoolTaskExecutor.class,
                        () -> {
                            ThreadPoolTaskExecutor mdcSupportExecutor = new ThreadPoolTaskExecutor();
                            mdcSupportExecutor.setCorePoolSize(this.getProps().getExecutor().getCorePoolSize());
                            mdcSupportExecutor.setMaxPoolSize(this.getProps().getExecutor().getMaxPoolSize());
                            mdcSupportExecutor.setQueueCapacity(this.getProps().getExecutor().getQueueCapacity());
                            mdcSupportExecutor.setThreadNamePrefix(this.getProps().getExecutor().getThreadNamePrefix());
                            mdcSupportExecutor.setTaskDecorator(new MdcSupportTaskDecorator());
                            mdcSupportExecutor.initialize();

                            return mdcSupportExecutor;
                        }
                ).getBeanDefinition()
        );
    }

}
