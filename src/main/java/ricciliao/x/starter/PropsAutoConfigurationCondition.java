package ricciliao.x.starter;

import jakarta.annotation.Nonnull;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import java.util.Map;

public class PropsAutoConfigurationCondition implements Condition {

    @Override
    public boolean matches(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(PropsAutoConfiguration.class.getName(), true);
        if (MapUtils.isEmpty(attributes)
                || !filter((String[]) attributes.get("properties"), context.getClassLoader())) {

            return false;
        }
        if (attributes.get("conditionalOnProperties") instanceof String[] properties) {
            for (String property : properties) {
                if (context.getEnvironment().containsProperty(property)) {
                    String value = context.getEnvironment().getProperty(property);
                    if (Boolean.FALSE.toString().equalsIgnoreCase(value) || StringUtils.isBlank(value)) {

                        return false;
                    }
                } else {

                    return false;
                }
            }
        }

        return true;
    }

    protected final boolean filter(String[] propsClassNameList,
                                   ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = ClassUtils.getDefaultClassLoader();
        }
        try {
            for (String propsClassName : propsClassNameList) {
                if (classLoader != null) {
                    Class.forName(propsClassName, false, classLoader);
                } else {
                    Class.forName(propsClassName);
                }
            }
            return true;
        } catch (Throwable ex) { //NOSONAR

            return false;
        }
    }


}
