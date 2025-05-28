package ricciliao.x.starter.aop;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = DynamicAspectAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.dynamic-aspect.aspect-list[0].expression"
)
public class DynamicAspectAutoConfiguration extends PropsBeanDefinitionRegistryPostProcessor<DynamicAspectAutoProperties> {

    public DynamicAspectAutoConfiguration() {
        super(DynamicAspectAutoProperties.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@Nonnull BeanDefinitionRegistry registry) throws BeansException {
        for (DynamicAspectAutoProperties.ExpressionAspect aspect : this.getProps().getAspectList()) {
            if (StringUtils.isBlank(aspect.getBeanName())) {

                throw new BeanCreationException("can not define Dynamic Aspect without bean name!");
            }
            if (aspect.getAspect() == null) {

                throw new BeanCreationException("can not define Dynamic Aspect without aspect class!");
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(aspect.getAspect());
            registry.registerBeanDefinition(aspect.getBeanName(), builder.getBeanDefinition());
        }
    }

}
