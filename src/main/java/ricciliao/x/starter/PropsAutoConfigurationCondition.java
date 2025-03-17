package ricciliao.x.starter;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import java.util.Map;

public class PropsAutoConfigurationCondition implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(PropsAutoConfiguration.class.getName());
        if (MapUtils.isEmpty(attributes)) {

            return false;
        }
        if (attributes.get("conditionProperties") instanceof String[] properties) {
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
}
