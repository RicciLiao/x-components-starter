package ricciliao.x.starter.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.cache.consumer.ConsumerCacheProperties;

@ConfigurationProperties("ricciliao.x.cache-consumer")
public class ConsumerCacheAutoProperties extends ConsumerCacheProperties {
}
