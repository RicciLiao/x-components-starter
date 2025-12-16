package ricciliao.x.starter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Set;

public class XEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String MISSING_PROPERTY = "Missing required configuration property: %s";

    private final Set<String> properties;

    public XEnvironmentPostProcessor() {
        properties = Set.of(
                "ricciliao.x.common.consumer"
        );
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        for (String property : properties) {
            if (!environment.containsProperty(property)
                    || StringUtils.isBlank(environment.getProperty(property))) {

                throw new IllegalStateException(String.format(MISSING_PROPERTY, property));
            }
        }
    }

}
