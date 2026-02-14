package ricciliao.x.starter.aop;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import ricciliao.x.component.beans.PropsImportBeanDefinitionRegistrar;

import javax.annotation.Nonnull;

public class DynamicAspectDefinitionRegistrar extends PropsImportBeanDefinitionRegistrar<DynamicAspectAutoProperties> {

    protected DynamicAspectDefinitionRegistrar() {
        super(DynamicAspectAutoProperties.class);
    }

    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        for (DynamicAspectAutoProperties.ExpressionAspect aspect : this.getProps().getAspectList()) {
            if (StringUtils.isBlank(aspect.getBeanName())) {

                throw new BeanCreationException("can not define Dynamic Aspect without bean name!");
            }
            if (aspect.getAspect() == null) {

                throw new BeanCreationException("can not define Dynamic Aspect without aspect class!");
            }
            registry.registerBeanDefinition(
                    aspect.getBeanName(),
                    BeanDefinitionBuilder.genericBeanDefinition(aspect.getAspect()).getBeanDefinition()
            );
        }
    }

}
