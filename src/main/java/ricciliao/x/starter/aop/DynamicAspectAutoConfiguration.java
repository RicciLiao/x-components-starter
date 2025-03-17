package ricciliao.x.starter.aop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.lang.NonNull;
import ricciliao.x.component.context.PropsBeanDefinitionRegistryPostProcessor;
import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = DynamicAspectAutoProperties.class,
        conditionProperties = "ricciliao.x.dynamic-aspect.aspect-list[0].expression"
)
public class DynamicAspectAutoConfiguration extends PropsBeanDefinitionRegistryPostProcessor<DynamicAspectAutoProperties> {

    public DynamicAspectAutoConfiguration() {
        super(DynamicAspectAutoProperties.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        for (DynamicAspectAutoProperties.ExpressionAspect aspect : this.getProps().getAspectList()) {
            if (isBlank(aspect.getBeanName())) {

                throw new BeanCreationException("can not define Dynamic Aspect without bean name!");
            }
            if (aspect.getAspect() == null) {

                throw new BeanCreationException("can not define Dynamic Aspect without aspect class!");
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(aspect.getAspect());
            registry.registerBeanDefinition(aspect.getBeanName(), builder.getBeanDefinition());
        }
    }

    private static boolean isBlank(CharSequence cs) {
        int strLen = cs == null ? 0 : cs.length();
        if (strLen != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {

                    return false;
                }
            }
        }

        return true;
    }

}
