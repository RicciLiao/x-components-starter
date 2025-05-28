package ricciliao.x.starter.log;


import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.log.MdcSupportFilter;
import ricciliao.x.log.MdcSupportTaskDecorator;

@AutoConfiguration
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

        FilterRegistrationBean<MdcSupportFilter> auditLogFilterBean = new FilterRegistrationBean<>();
        auditLogFilterBean.setFilter(new MdcSupportFilter());
        auditLogFilterBean.addUrlPatterns("/*");
        auditLogFilterBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        auditLogFilterBean.setName("auditLogFilter");
        beanFactory.registerSingleton("auditLogFilter", auditLogFilterBean);

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
