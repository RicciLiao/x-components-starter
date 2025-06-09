package ricciliao.x.starter.log;


import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.log.MdcSupportTaskDecorator;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = AuditLogAutoProperties.class
)
public class AuditLogAutoConfiguration extends PropsBeanDefinitionRegistryPostProcessor<AuditLogAutoProperties> {

    protected AuditLogAutoConfiguration() {
        super(AuditLogAutoProperties.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@Nonnull BeanDefinitionRegistry registry) throws BeansException {
        // you will see~~
    }

    @Override
    public void postProcessBeanFactory(@Nonnull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        super.postProcessBeanFactory(beanFactory);

        if (Boolean.TRUE.equals(this.getProps().getExecutor().getEnable())) {
            ThreadPoolTaskExecutor mdcSupportExecutor = new ThreadPoolTaskExecutor();
            mdcSupportExecutor.setCorePoolSize(this.getProps().getExecutor().getCorePoolSize());
            mdcSupportExecutor.setMaxPoolSize(this.getProps().getExecutor().getMaxPoolSize());
            mdcSupportExecutor.setQueueCapacity(this.getProps().getExecutor().getQueueCapacity());
            mdcSupportExecutor.setThreadNamePrefix(this.getProps().getExecutor().getThreadNamePrefix());
            mdcSupportExecutor.setTaskDecorator(new MdcSupportTaskDecorator());
            mdcSupportExecutor.initialize();
            beanFactory.registerSingleton("mdcSupportExecutor", mdcSupportExecutor);
        }
    }
}
