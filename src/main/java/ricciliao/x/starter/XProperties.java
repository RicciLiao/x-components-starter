package ricciliao.x.starter;

import org.springframework.boot.info.BuildProperties;
import ricciliao.x.starter.aop.DynamicAspectAutoProperties;
import ricciliao.x.starter.cache.ConsumerCacheAutoProperties;
import ricciliao.x.starter.common.CommonAutoProperties;

public record XProperties(BuildProperties buildProps,
                          CommonAutoProperties commonProps,
                          DynamicAspectAutoProperties dynamicAspectProps,
                          ConsumerCacheAutoProperties consumerCacheAutoProps) {
}
