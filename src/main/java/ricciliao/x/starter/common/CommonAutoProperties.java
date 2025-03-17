package ricciliao.x.starter.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.props.CommonProperties;

@SuppressWarnings("SpringBootApplicationProperties")
@ConfigurationProperties("ricciliao.x.common")
public class CommonAutoProperties extends CommonProperties {
}
